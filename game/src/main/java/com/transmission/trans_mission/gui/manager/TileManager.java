package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.TileMapSettings;
import com.transmission.trans_mission.gui.containers.Tile;
import com.transmission.trans_mission.gui.containers.TileSet;
import com.transmission.trans_mission.settings.CharacterTileSettings;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

import static com.transmission.trans_mission.gui.manager.GameManager.*;

public class TileManager {

    private HashMap<String, TileSet> tileSets;
    @Getter @Setter
    private Tile activeTile;
    @Getter @Setter
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
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Helicoptershot.png", 64., 36.));
            case MAIN_MENU_START:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train.png", 64., 36.));
            case INTRO_CUTSCENE:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Cutscene_MurderDiscovery.png", 147., 104.));
            case GAME_START:
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Interior_withPeople.png", true));
                loadTileMap(new CharacterTileSettings("TransSiberian_Train_Interior_Seats_foreground.png", true));
                loadTileMap(new CharacterTileSettings("SkirtlookHolmes.png", 31., 31.));
                loadTileMap(new CharacterTileSettings("Skirtlook_portrait.png", true));
                loadTileMap(new CharacterTileSettings("Transsiberian_Train_ToiletFemaleInterior.png", true));
                loadTileMap(new CharacterTileSettings("Transsiberian_Toilet_MurderCloseup.png", true));
                loadTileMap(new CharacterTileSettings("TransSiberian_PubeScene.png", true));
                loadTileMap(new CharacterTileSettings("Transsiberian_Cutscene_Sunglasses.png", 64., 36.));
        }
    }

}
