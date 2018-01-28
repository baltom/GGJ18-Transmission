package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Dialog implements Drawable<Dialog> {

    private Tile tile;
    private Color backgroundColor;
    private Color borderColor;
    private Point2D size;
    private Point2D pos;
    private String text;


    @Override
    public Dialog getItem() {
        return this;
    }
}
