package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class MenuItem implements Drawable<MenuItem> {

    private final Point2D pos;
    private final Point2D size;
    private final String text;

    public MenuItem(Point2D pos, String text, Double width) {
        this.pos = pos;
        this.size = new Point2D(width, 50);
        this.text = text;

    }

    @Override
    public Point2D getSize() {
        return size;
    }

    @Override
    public Point2D getPos() {
        return pos;
    }

    @Override
    public MenuItem getItem() {
        return this;
    }

    @Override
    public Color getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public Color getBorderColor() {
        return Color.BLACK;
    }

    public String getText() {
        return text;
    }

    public boolean isInsideBounds(double x, double y) {
        return (this.getPos().getX() < x && this.getPos().getY() < y &&
                this.getPos().getX() + this.getSize().getX() > x &&
                this.getPos().getY() + this.getSize().getY() > y);
    }
}
