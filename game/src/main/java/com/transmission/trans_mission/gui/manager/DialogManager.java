package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.gui.containers.Dialog;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class DialogManager {

    private Canvas parent;
    private Point2D dialogPos;
    private Point2D dialogSize;
    private TileSet tileSet;

    private Dialog currentDialog;
    private boolean shouldDraw;
    private boolean hasRequested;

    public DialogManager(Canvas parent, TileSet tileSet) {
        initDialogSize(parent);
        this.tileSet = tileSet;
    }

    public void initDialogSize(Canvas parent) {
        this.parent = parent;
        dialogSize = new Point2D(parent.getWidth(), 150);
        dialogPos = new Point2D(0, parent.getHeight() - dialogSize.getY());
    }


    public Dialog getDialog() {
        hasRequested = true;
        return currentDialog;
    }

    public boolean shouldDrawDialog() {
        return shouldDraw;
    }

    public boolean isWithinDialogBounds(double x, double y) {
        return dialogPos.getX() < x && dialogPos.getY() < y &&
                dialogPos.getX() + dialogSize.getX() > x &&
                dialogPos.getY() + dialogSize.getY() > y;
    }

    public void clickDialog(MouseEvent mouseEvent) {
        if (hasRequested) {
            hasRequested = false;
            shouldDraw = false;
        }
    }

    public void displayDialog(int scene) {
        shouldDraw = true;
        currentDialog = new Dialog(tileSet.getTile(0), Color.BLACK, Color.RED, dialogSize, dialogPos, "Is that a hair?");
    }

    public void removeDialog() {
        shouldDraw = false;
        hasRequested = false;
        currentDialog = null;
    }
}
