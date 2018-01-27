package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.TileMapSettings;
import com.transmission.trans_mission.gui.containers.Tile;
import com.transmission.trans_mission.gui.containers.TileSet;
import com.transmission.trans_mission.settings.CharacterTileSettings;

import java.util.HashMap;

import static com.transmission.trans_mission.gui.manager.GameManager.*;

public class TileManager {

    private HashMap<String, TileSet> tileSets;
    private Tile activeTile;
    private Double tileScale;

    public TileManager() {
        this.tileSets = new HashMap<>();
    }

    public void loadTileMap(TileMapSettings settings) {
        TileSet tileSet = new TileSet(settings, tileScale);
        tileSets.put(settings.name().split("\\.")[0], tileSet);
    }

    public TileSet getTileSet(String name) {
        return tileSets.get(name);
    }

    public void loadAllTiles(int START_STEP) {
        switch (START_STEP) {
            case CUTSCENE_START:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Helicoptershot10x.png", 64. * 10, 36. * 10));
            case MAIN_MENU_START:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train10x.png", 64. * 10, 38. * 10));
            case GAME_START:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Interior10x.png", true));
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Interior_Seats_foreground10x.png", true));
                loadTileMap(new CharacterTileSettings("character.png"));
                loadTileMap(new CharacterTileSettings("heads.jpg", 250., 250.));
        }
    }

    public Tile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(Tile activeTile) {
        this.activeTile = activeTile;
    }

    public Double getTileScale() {
        return tileScale;
    }

    public void setTileScale(Double tileScale) {
        this.tileScale = tileScale;
    }
}
