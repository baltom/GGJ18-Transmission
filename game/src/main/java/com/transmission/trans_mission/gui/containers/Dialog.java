package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Dialog implements Drawable<Dialog> {

    private Tile tile;
    private Color backgroundColor;
    private Color borderColor;
    private Point2D size;
    private Point2D pos;
    private String text;

    public Dialog(Tile tile, Color backgroundColor, Color borderColor, Point2D size, Point2D pos, String text) {
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.size = size;
        this.pos = pos;
        this.text = text;
        this.tile = tile;
    }

    @Override
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Color getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
    }

    @Override
    public Point2D getSize() {
        return size;
    }

    public void setSize(Point2D size) {
        this.size = size;
    }

    @Override
    public Point2D getPos() {
        return pos;
    }

    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    @Override
    public Dialog getItem() {
        return this;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Dialog{" +
                "backgroundColor=" + backgroundColor +
                ", borderColor=" + borderColor +
                ", size=" + size +
                ", pos=" + pos +
                ", text='" + text + '\'' +
                ", item='" + getItem() + '\'' +
                '}';
    }
}
