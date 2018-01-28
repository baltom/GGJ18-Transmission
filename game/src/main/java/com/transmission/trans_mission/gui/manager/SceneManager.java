package com.transmission.trans_mission.gui.manager;

import com.google.gson.Gson;
import com.transmission.trans_mission.container.BoundsMap;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class SceneManager {

    public static final int TRAIN_SCENE = 0;
    public static final int TOILET_SCENE = 1;
    public static final int MURDER_SCENE = 2;
    public static final int PUBE_SCENE = 3;

    private int currentScene;
    private HashMap<Integer, BoundsMap> boundsHashMap;
    private HashMap<Integer, List<TileSet>> backgroundTiles;

    public SceneManager(int currentScene) {
        this.currentScene = currentScene;
        this.backgroundTiles = new HashMap<>();
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
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public TileDrawable getBackgroundLayer() {
        TileDrawable returnTile = null;
        try {
            switch (currentScene) {
                case TRAIN_SCENE:
                    returnTile = new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(0).getImage());
                    break;
                case TOILET_SCENE:
                    returnTile = new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(0).getImage());
                    break;
                case MURDER_SCENE:
                    returnTile = new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(0).getImage());
                case PUBE_SCENE:
                    returnTile = new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(0).getImage());
            }
        } catch (NullPointerException e) {
            System.out.println("Failed background");
        }
        return returnTile;
    }

    private Tile getBackgroundTile(int i) {
        List<TileSet> currentSet = backgroundTiles.get(currentScene);
        if (currentSet != null) {
            return currentSet.get(i).getTile(0);
        } else {
            return null;
        }
    }

    public TileDrawable getForegroundLayer() {
        TileDrawable returnTile = null;
        try {
            switch (currentScene) {
                case TRAIN_SCENE:
                    returnTile = new TileDrawable(null, new Point2D(0, 0), getBackgroundTile(1).getImage());
                    break;
            }
        } catch (NullPointerException e) {
            System.out.println("Failed foreground");
        }
        return returnTile;
    }

    public int getCurrentScene() {
        return currentScene;
    }

    public void updateScene(int scene) {
        this.currentScene = scene;
    }

    public BoundsMap getBoundsMap() {
        return boundsHashMap.get(currentScene);
    }

    public void addBackgroundScene(int scene, TileSet[] tileSet) {
        List<TileSet> tileset = backgroundTiles.getOrDefault(scene, new ArrayList<>());
        tileset.addAll(Arrays.stream(tileSet).collect(Collectors.toList()));
        backgroundTiles.put(scene, tileset);
    }

    public Double getCharacterScale() {
        switch (currentScene) {
            case TRAIN_SCENE:
                return 3.;
            case TOILET_SCENE:
                return 4.;
        }
        return 3.;
    }

    public Point2D getCharacterPos() {
        switch (currentScene) {
            case TRAIN_SCENE:
                return new Point2D(0, 0);
            case TOILET_SCENE:
                return new Point2D(3., 485.);
        }
        return new Point2D(0, 0);
    }

    public int getCurrentMaxRange() {
        switch (currentScene) {
            case TRAIN_SCENE:
                return 100;
            case TOILET_SCENE:
                return 650;
            case MURDER_SCENE:
            case PUBE_SCENE:
                return 1300;
        }
        return 100;
    }
}
