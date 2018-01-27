package com.transmission.trans_mission.gui.components;

import com.transmission.trans_mission.contract.DrawCallback;
import javafx.scene.canvas.Canvas;

public class ResizableCanvas extends Canvas {

    public ResizableCanvas(DrawCallback drawCallback) {
        widthProperty().addListener(evt -> drawCallback.redrawScreen());
        heightProperty().addListener(evt -> drawCallback.redrawScreen());
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double prefWidth(double height) {
        return getWidth();
    }

    @Override
    public double prefHeight(double width) {
        return getHeight();
    }
}
