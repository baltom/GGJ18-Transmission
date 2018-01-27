package com.transmission.trans_mission.contract;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public interface Drawable<T> {

    Point2D getSize();

    Point2D getPos();

    T getItem();

    Color getBackgroundColor();

    Color getBorderColor();
}
