package com.transmission.trans_mission.container;

import javafx.scene.shape.Polygon;

public class BoundsMap {

    private int id;
    private Pos bottomLeft;
    private Pos bottomRight;
    private Pos topLeft;
    private Pos topRight;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pos getBottomLeft() {
        return bottomLeft;
    }

    public void setBottomLeft(Pos bottomLeft) {
        this.bottomLeft = bottomLeft;
    }

    public Pos getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(Pos bottomRight) {
        this.bottomRight = bottomRight;
    }

    public Pos getTopLeft() {
        return topLeft;
    }

    public void setTopLeft(Pos topLeft) {
        this.topLeft = topLeft;
    }

    public Pos getTopRight() {
        return topRight;
    }

    public void setTopRight(Pos topRight) {
        this.topRight = topRight;
    }

    @Override
    public String toString() {
        return "BoundsMap{" +
                "id=" + id +
                ", bottomLeft=" + bottomLeft +
                ", bottomRight=" + bottomRight +
                ", topLeft=" + topLeft +
                ", topRight=" + topRight +
                '}';
    }

    public boolean isWithinBounds(int x, int y) {
        Polygon shape = new Polygon(
                getTopLeft().getX(), getTopLeft().getY(),
                getTopRight().getX(), getTopRight().getY(),
                getBottomLeft().getX(), getBottomLeft().getY(),
                getBottomRight().getX(), getBottomRight().getY());
        return shape.contains(x, y);
    }
}
