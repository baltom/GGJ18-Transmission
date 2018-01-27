package com.transmission.trans_mission.contract;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public interface DrawTileCallback {

    void draw(Image image, Point2D pos, Point2D size);
}
