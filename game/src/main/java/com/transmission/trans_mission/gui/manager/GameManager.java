package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.contract.DrawTileCallback;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

public class GameManager {

    private CharacterContainer character;
    private GameLoopManager gameLoopManager;
    private TileManager tileManager;
    private DialogManager dialogManager;
    private CutsceneManager cutsceneManager;

    public GameManager(Canvas parent) {
        tileManager = new TileManager();
        tileManager.setTileScale(2.);
        tileManager.loadAllTiles();
        dialogManager = new DialogManager(parent, tileManager.getTileSet("heads"));
        cutsceneManager = new CutsceneManager(tileManager.getTileSet("TransSiberian_Train_Helicoptershot").setScale(1.));

        gameLoopManager = new GameLoopManager(dialogManager);

        character = new CharacterContainer(tileManager.getTileSet("character"), 1.5, new Point2D(0, 0));

        gameLoopManager.addGameLogicItem(character);
        gameLoopManager.addRenderItem(character);
    }

    public void startGame(DrawTileCallback callback) {
        try {
            cutsceneManager.playCutscene(0, 100, callback);
            gameLoopManager.startGameLoop(callback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnScreen(MouseEvent mouseEvent) {
        character.characterMove(mouseEvent);
    }

    public void updateSize(Canvas pane) {
        dialogManager.initDialogSize(pane);
    }
}
