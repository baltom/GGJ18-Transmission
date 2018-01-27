package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.contract.TileMapSettings;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TileSet {

    private List<Tile> tiles;

    public TileSet(TileMapSettings settings, Double tileScale) {
        tiles = new ArrayList<>();
        Image image = new Image(getClass().getResourceAsStream("/" + settings.name()));
        if (settings.getEntireSheet()) {
            tiles.add(new Tile(image, tileScale));
        } else {
            PixelReader reader = image.getPixelReader();

            int maxVert = (int) (image.getHeight() / (settings.getHeight() + settings.getPadding() * 2));
            int maxHor = (int) (image.getWidth() / (settings.getWidth()) + settings.getPadding() * 2);

            for (int vertical = 0; vertical < maxVert; vertical++) {
                Double y = vertical * settings.getHeight();
                for (int horizontal = 0; horizontal < maxHor; horizontal++) {
                    try {
                        Double x = horizontal * settings.getWidth();
                        WritableImage newImage = new WritableImage(reader, x.intValue(), y.intValue(), (int) settings.getWidth(), (int) settings.getHeight());
                        if (!isBlankImage(newImage)) {
                            tiles.add(new Tile(newImage, tileScale));
                        }
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Woot");
                    }
                }
            }
        }
    }

    private boolean isBlankImage(WritableImage writableImage) {
        PixelReader reader = writableImage.getPixelReader();
        boolean isBlank = true;
        for (int y = 0; y < writableImage.getHeight(); y++) {
            for (int x = 0; x < writableImage.getWidth(); x++) {
                Color color = reader.getColor(x, y);
                if (!color.toString().equals("0x00000000")) {
                    isBlank = false;
                    break;
                }
            }
        }
        return isBlank;
    }

    public Tile getTile(int num) {
        if (num < 0 || num >= tiles.size()) return null;
        return getTiles().get(num);
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public int getTileCount() {
        return tiles.size();
    }

    public TileSet setScale(double v) {
        tiles.forEach(t -> t.setScale(v));
        return this;
    }
}
