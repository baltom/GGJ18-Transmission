package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;

import java.util.Arrays;
import java.util.List;

public class CutsceneManager {

    private List<TileSet> cutscenes;

    public CutsceneManager(TileSet... tileSet) {
        cutscenes = Arrays.asList(tileSet);
    }

    public void playCutscene(int i, long speed, DrawTileCallback callback) throws InterruptedException {
        if (cutscenes.size() > i) {
            TileSet tileSet = cutscenes.get(i);
            for (int step = 0; step < tileSet.getTileCount(); step++) {
                callback.draw(new TileDrawable(null, new Point2D(0, 0), tileSet.getTile(step).getImage()));
                Thread.sleep(speed);
            }
        } else {
            System.out.println("Incorrect");
        }
    }
}
