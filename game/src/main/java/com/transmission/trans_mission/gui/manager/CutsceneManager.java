package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;

import java.util.HashMap;

public class CutsceneManager {

    public static final int START_SCENE = 0;
    public static final int INTRO_SCENE = 1;

    private HashMap<Integer, TileSet> cutscenes;

    public CutsceneManager() {
        cutscenes = new HashMap<>();
    }

    public void playCutscene(int i, long speed, DrawTileCallback callback) throws InterruptedException {
        if (cutscenes.get(i) != null) {
            TileSet tileSet = cutscenes.get(i);
            for (int step = 0; step < tileSet.getTileCount(); step++) {
                callback.draw(new TileDrawable(null, new Point2D(0, 0), tileSet.getTile(step).getImage()));
                Thread.sleep(speed);
            }
        } else {
            System.out.println("Incorrect");
        }
    }

    public void addCutscene(int introScene, TileSet tileSet) {
        cutscenes.put(introScene, tileSet);
    }
}
