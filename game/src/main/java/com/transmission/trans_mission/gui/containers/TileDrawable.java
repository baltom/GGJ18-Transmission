package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class TileDrawable implements Drawable<Image> {

    private Point2D size;
    private Point2D pos;
    private Image item;

    public TileDrawable(Point2D size, Point2D pos, Image item) {
        this.size = size;
        this.pos = pos;
        this.item = item;
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
    public Image getItem() {
        return item;
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
