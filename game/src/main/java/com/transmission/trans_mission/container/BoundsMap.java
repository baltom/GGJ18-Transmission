package com.transmission.trans_mission.container;

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
        Pos test = new Pos(x, y);
        Pos[] points = {getTopLeft(), getTopRight(), getBottomRight(), getBottomLeft()};
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = points.length - 1; i < points.length; j = i++) {
            if ((points[i].getY() > test.getY()) != (points[j].getY() > test.getY()) &&
                    (test.getX() < (points[j].getX() - points[i].getX()) * (test.getY() - points[i].getY()) / (points[j].getY() - points[i].getY()) + points[i].getX())) {
                result = !result;
            }
        }
        return result;
    }
}
