package com.transmission.trans_mission.settings;

import com.transmission.trans_mission.contract.TileMapSettings;

public class CharacterTileSettings implements TileMapSettings {

    private final String name;
    private final Double height;
    private final Double width;
    private final Double padding;

    public CharacterTileSettings(String name) {
        this(name, 0.);
    }

    public CharacterTileSettings(String name, Double padding) {
        this.name = name;
        this.padding = padding;
        this.width = 48.;
        this.height = 56.;
    }

    public CharacterTileSettings(String s, double width, double height) {
        this.name = s;
        this.padding = 0.;
        this.width = width;
        this.height = height;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getPadding() {
        return padding;
    }
}
