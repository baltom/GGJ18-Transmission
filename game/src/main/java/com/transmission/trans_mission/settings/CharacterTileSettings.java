package com.transmission.trans_mission.settings;

import com.transmission.trans_mission.contract.TileMapSettings;
import lombok.Value;

@Value
public class CharacterTileSettings implements TileMapSettings {

    String name;
    double height;
    double width;
    double padding;
    boolean entireSheet;

    public CharacterTileSettings(String name) {
        this(name, 0.);
    }

    public CharacterTileSettings(String name, Double padding) {
        this.name = name;
        this.padding = padding;
        this.width = 48.;
        this.height = 56.;
        this.entireSheet = false;
    }

    public CharacterTileSettings(String s, double width, double height) {
        this.name = s;
        this.padding = 0.;
        this.width = width;
        this.height = height;
        this.entireSheet = false;
    }

    public CharacterTileSettings(String s, boolean cover) {
        this.name = s;
        this.entireSheet = cover;
        this.padding = 0.0;
        this.width = 0.0;
        this.height = 0.0;
    }

    @Override
    public String name() {
        return name;
    }
}
