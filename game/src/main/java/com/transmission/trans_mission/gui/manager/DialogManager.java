package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.gui.containers.Dialog;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import org.apache.commons.lang.StringUtils;

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
        Dialog dialog = new Dialog(tileSet.getTile(1), Color.BLACK, Color.RED, dialogSize, dialogPos, StringUtils.repeat("Testing ", 100));
        return dialog;
    }

    public boolean shouldDrawDialog() {
        return false;
    }

    public boolean isWithinDialogBounds(double x, double y) {
        return dialogPos.getX() < x && dialogPos.getY() < y &&
                dialogPos.getX() + dialogSize.getX() > x &&
                dialogPos.getY() + dialogSize.getY() > y;
    }

    public void clickDialog(MouseEvent mouseEvent) {

    }
}
