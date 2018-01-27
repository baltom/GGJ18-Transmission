package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.contract.DrawTileCallback;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class GameManager {

    private CharacterContainer character;
    private GameLoopManager gameLoopManager;
    private TileManager tileManager;

    public GameManager() {
        tileManager = new TileManager();
        tileManager.setTileScale(2.);
        tileManager.loadAllTiles();

        gameLoopManager = new GameLoopManager();

        character = new CharacterContainer(tileManager.getTileSet("character"), 1.5, new Point2D(0, 0));

        gameLoopManager.addGameLogicItem(character);
        gameLoopManager.addRenderItem(character);
    }

    public void startGame(DrawTileCallback callback) {
        try {
            gameLoopManager.startGameLoop(callback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnScreen(MouseEvent mouseEvent) {
        character.characterMove(mouseEvent);
    }
}
