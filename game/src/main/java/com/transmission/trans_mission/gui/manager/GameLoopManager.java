package com.transmission.trans_mission.gui.manager;

import com.google.gson.Gson;
import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.container.BoundsMap;
import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.contract.GameLogicCallback;
import com.transmission.trans_mission.contract.RenderCallback;
import com.transmission.trans_mission.gui.containers.Square;
import com.transmission.trans_mission.gui.containers.Tile;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GameLoopManager {

    private final DialogManager dialogManager;
    private List<RenderCallback> renderItems;
    private List<GameLogicCallback> gameLogic;
    private boolean gameRunning;
    private long lastFpsTime;
    private int fps;
    private boolean doARender;
    private boolean hasDoneInitialRender = false;
    private List<TileSet> backgroundTiles;
    private HashMap<Integer, BoundsMap> boundsHashMap;
    private boolean drawBoundsMap = false;
    private CharacterContainer character;
    private int currentScene = 0;

    public GameLoopManager(DialogManager dialogManager) {
        renderItems = new ArrayList<>();
        gameLogic = new ArrayList<>();
        this.dialogManager = dialogManager;
        loadBoundsMap("bounds.json");
    }

    private void loadBoundsMap(String s) {
        boundsHashMap = new HashMap<>();

        try {
            String file = FileUtils.readFileToString(new File(getClass().getResource("/" + s).toURI()), Charset.forName("UTF-8"));
            Gson gson = new Gson();
            BoundsMap[] boundsMaps = gson.fromJson(file, BoundsMap[].class);
            for (BoundsMap map :
                    boundsMaps) {
                boundsHashMap.put(map.getId(), map);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void addRenderItem(RenderCallback item) {
        renderItems.add(item);
    }

    public void addGameLogicItem(GameLogicCallback item) {
        gameLogic.add(item);
    }

    public void endGame() {
        gameRunning = false;
    }

    public void startGameLoop(DrawTileCallback callback) throws InterruptedException {
        gameRunning = true;
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;

        while (gameRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1_000_000_000) {
                System.out.println("(FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            doGameUpdates(delta);
            render(callback);
            Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
        }
    }

    private void render(DrawTileCallback callback) {
        doARender = true;
        if (doARender || !hasDoneInitialRender) {
            hasDoneInitialRender = true;
            doARender = false;
            callback.draw(null);
            callback.draw(new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(0).getImage()));
            renderItems.forEach(render -> render.render(callback));
            callback.draw(new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(1).getImage()));
            if (dialogManager.shouldDrawDialog()) {
                callback.draw(dialogManager.getDialog());
            }
            if (drawBoundsMap) {
                BoundsMap boundsMap = boundsHashMap.get(0);
                callback.draw(new Square(boundsMap));
            }
        }
    }

    private Tile getBackgroundTile(int num) {
        return backgroundTiles.get(num).getTile(0);
    }

    public void setDoARender(boolean doARender) {
        if (doARender) {
            this.doARender = true;
        }
    }

    private void doGameUpdates(double delta) {
        gameLogic.forEach(logic -> setDoARender(logic.gameLogic(delta)));
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void addBackgroundTileSet(TileSet tileSet) {
        if (this.backgroundTiles == null) {
            this.backgroundTiles = new ArrayList<>();
        }
        this.backgroundTiles.add(tileSet);
    }

    public BoundsMap getBoundsMap() {
        return boundsHashMap.get(0);
    }

    public void setCharacter(CharacterContainer character) {
        this.character = character;
        character.setBoundsMap(boundsHashMap.get(0));
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void setCurrentScene(int currentScene) {
        this.currentScene = currentScene;
    }
}
