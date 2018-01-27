package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Circle implements Drawable<Point2D> {

    private final Point2D pos;
    private final Point2D size;

    public Circle(Point2D pos, Point2D size) {
        this.pos = pos;
        this.size = size;
    }

    @Override
    public Point2D getSize() {
        return size;
    }

    @Override
    public Point2D getPos() {
        return null;
    }

    @Override
    public Point2D getItem() {
        return pos;
    }

    @Override
    public Color getBackgroundColor() {
        return null;
    }

    @Override
    public Color getBorderColor() {
        return null;
    }
}
