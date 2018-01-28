package com.transmission.trans_mission.container;

import javafx.scene.shape.Polygon;
import lombok.Data;

@Data
public class BoundsMap {

    private int id;
    private Pos bottomLeft;
    private Pos bottomRight;
    private Pos topLeft;
    private Pos topRight;
    
    public boolean isWithinBounds(int x, int y) {
        Polygon shape = new Polygon(
                getTopLeft().getX(), getTopLeft().getY(),
                getTopRight().getX(), getTopRight().getY(),
                getBottomLeft().getX(), getBottomLeft().getY(),
                getBottomRight().getX(), getBottomRight().getY());
        return shape.contains(x, y);
    }
}
