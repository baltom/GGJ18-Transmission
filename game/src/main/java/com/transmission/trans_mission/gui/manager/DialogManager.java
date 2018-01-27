package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.gui.containers.Dialog;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

public class DialogManager {

    private Canvas parent;
    private Point2D dialogPos;
    private Point2D dialogSize;
    private TileSet tileSet;

    public DialogManager(Canvas parent, TileSet tileSet) {
        initDialogSize(parent);
        this.tileSet = tileSet;
    }

    public void initDialogSize(Canvas parent) {
        this.parent = parent;
        dialogSize = new Point2D(parent.getWidth(), 250);
        dialogPos = new Point2D(0, parent.getHeight() - dialogSize.getY());
    }


    public Dialog getDialog() {
        Dialog dialog = new Dialog(tileSet.getTile(1), Color.BLACK, Color.RED, dialogSize, dialogPos, "This is a test");
        return dialog;
    }

    public boolean shouldDrawDialog() {
        return true;
    }
}
