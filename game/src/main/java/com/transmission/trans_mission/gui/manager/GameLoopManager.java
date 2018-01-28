package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.container.BoundsMap;
import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.contract.GameLogicCallback;
import com.transmission.trans_mission.contract.RenderCallback;
import com.transmission.trans_mission.gui.containers.Square;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.List;

import static com.transmission.trans_mission.gui.manager.SceneManager.*;

public class GameLoopManager {

    private final DialogManager dialogManager;
    private final SceneManager sceneManager;
    private final InteractionManager interactionManger;
    private List<RenderCallback> renderItems;
    private List<GameLogicCallback> gameLogic;
    private boolean gameRunning;
    private long lastFpsTime;
    private int fps;
    private boolean doARender;
    private boolean hasDoneInitialRender = false;
    private List<TileSet> backgroundTiles;
    private boolean drawBoundsMap = false;
    private CharacterContainer character;
    private CutsceneManager cutscene;
    private boolean playCutscene;
    private int cutSceneNum;

    public GameLoopManager(DialogManager dialogManager, InteractionManager interactionManager) {
        renderItems = new ArrayList<>();
        gameLogic = new ArrayList<>();
        this.dialogManager = dialogManager;
        this.interactionManger = interactionManager;
        this.sceneManager = new SceneManager(TRAIN_SCENE);
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
            if (playCutscene && cutscene != null) {
                cutscene.playCutscene(cutSceneNum, 200, callback);
                playCutscene = false;
                changeScene(TOILET_SCENE);
            } else {
                long now = System.nanoTime();
                long updateLength = now - lastLoopTime;
                lastLoopTime = now;
                double delta = updateLength / ((double) OPTIMAL_TIME);
                lastFpsTime += updateLength;
                fps++;

                if (lastFpsTime >= 1_000_000_000) {
                    //System.out.println("(FPS: " + fps + ")");
                    lastFpsTime = 0;
                    fps = 0;
                }
                doGameUpdates(delta);
                render(callback);
                Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
            }
        }
    }

    private void render(DrawTileCallback callback) {
        doARender = true;
        if (doARender || !hasDoneInitialRender) {
            hasDoneInitialRender = true;
            doARender = false;
            callback.draw(null);

            TileDrawable backgroundLayer = sceneManager.getBackgroundLayer();
            if (backgroundLayer != null) {
                callback.draw(backgroundLayer);
            }

            renderItems.forEach(render -> render.render(callback));

            TileDrawable foregroundLayer = sceneManager.getForegroundLayer();
            if (foregroundLayer != null) {
                callback.draw(foregroundLayer);
            }

            if (dialogManager.shouldDrawDialog()) {
                callback.draw(dialogManager.getDialog());
            }
            if (drawBoundsMap) {
                BoundsMap boundsMap = getBoundsMap();
                callback.draw(new Square(boundsMap));
                interactionManger.drawInteractions(callback, sceneManager.getCurrentScene());
            }
        }
    }

    private void doGameUpdates(double delta) {
        gameLogic.forEach(logic -> logic.gameLogic(delta));
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void addBackgroundTileSet(int scene, TileSet... tileSet) {
        this.sceneManager.addBackgroundScene(scene, tileSet);
    }

    public BoundsMap getBoundsMap() {
        return sceneManager.getBoundsMap();
    }

    public void setCharacter(CharacterContainer character) {
        this.character = character;
        this.character.setBoundsMap(sceneManager.getBoundsMap());
    }

    public int getCurrentScene() {
        return sceneManager.getCurrentScene();
    }

    public void changeScene(int scene) {
        sceneManager.updateScene(scene);
        if (scene == TRAIN_SCENE || scene == TOILET_SCENE) {
            character.setShouldRender(true);
            Double characterScale = sceneManager.getCharacterScale();
            Point2D characterPos = sceneManager.getCharacterPos();
            character.setBoundsMap(sceneManager.getBoundsMap());
            character.setScale(characterScale);
            character.setPos(characterPos);
        } else {
            character.setShouldRender(false);
        }

        if (scene == MURDER_SCENE) {
            dialogManager.displayDialog(scene);
        } else {
            dialogManager.removeDialog();
        }
    }

    public int getCurrentMaxRange() {
        return sceneManager.getCurrentMaxRange();
    }

    public void playCutscene(int murderCutscene, CutsceneManager cutsceneManager) {
        this.cutscene = cutsceneManager;
        this.playCutscene = true;
        this.cutSceneNum = murderCutscene;
    }
}
