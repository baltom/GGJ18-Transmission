package com.transmission.trans_mission.gui.containers;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class Tile {

    private Image image;
    private Double scale;

    public Tile(WritableImage croppedImage) {
        this(croppedImage, 1.);
    }

    public Tile(WritableImage newImage, Double tileScale) {
        scale = tileScale;
        image = newImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getWidth() {
        return image.getWidth() * getScale();
    }

    public Double getHeight() {
        return image.getHeight() * getScale();
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }
}
