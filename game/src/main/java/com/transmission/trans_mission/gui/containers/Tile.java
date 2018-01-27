package com.transmission.trans_mission.gui.containers;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Tile {

    private Image image;
    private Double scale;

    public Tile(WritableImage croppedImage) {
        this(croppedImage, 1.);
    }

    public Tile(WritableImage newImage, Double tileScale) {
        scale = tileScale;
        image = resample(newImage, tileScale);
    }

    public Tile(Image newImage, Double tileScale) {
        scale = tileScale;
        image = resample(newImage, tileScale);
    }

    private Image resample(Image input, Double scaleFactor) {
        final int W = (int) input.getWidth();
        final int H = (int) input.getHeight();
        final Double S = scaleFactor;

        WritableImage output = new WritableImage(
                W * S.intValue(),
                H * S.intValue()
        );

        PixelReader reader = input.getPixelReader();
        PixelWriter writer = output.getPixelWriter();

        for (int y = 0; y < H; y++) {
            for (int x = 0; x < W; x++) {
                final int argb = reader.getArgb(x, y);
                for (int dy = 0; dy < S; dy++) {
                    for (int dx = 0; dx < S; dx++) {
                        writer.setArgb((int) (x * S + dx), (int) (y * S + dy), argb);
                    }
                }
            }
        }

        return output;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getWidth() {
        return image.getWidth();
    }

    public Double getHeight() {
        return image.getHeight();
    }

    public void setScale(Double scale) {
        this.scale = scale;
        this.image = resample(this.image, scale);
    }
}
