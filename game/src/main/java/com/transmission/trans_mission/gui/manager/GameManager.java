package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.contract.DrawTileCallback;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import static com.transmission.trans_mission.gui.manager.MusicManager.MAIN_MENU_THEME;
import static com.transmission.trans_mission.gui.manager.MusicManager.MAIN_THEME;

public class GameManager {

    public final static int CUTSCENE_START = 0;
    public final static int MAIN_MENU_START = 1;
    public final static int GAME_START = 2;

    private final int START_STEP = CUTSCENE_START;

    private CharacterContainer character;
    private GameLoopManager gameLoopManager;
    private TileManager tileManager;
    private DialogManager dialogManager;
    private CutsceneManager cutsceneManager;
    private MainMenuManager mainMenuManager;
    private MusicManager musicManager;

    public GameManager(Canvas parent) {
        musicManager = new MusicManager();
        tileManager = new TileManager();
        tileManager.setTileScale(3.);
        tileManager.loadAllTiles(START_STEP);

        switch (START_STEP) {
            case CUTSCENE_START:
                cutsceneManager = new CutsceneManager(tileManager.getTileSet("TransSiberian_Train_Helicoptershot").setScale(3.));
            case MAIN_MENU_START:
                mainMenuManager = new MainMenuManager(tileManager.getTileSet("TransSiberian_Train").setScale(3.), parent);
            case GAME_START:
                dialogManager = new DialogManager(parent, tileManager.getTileSet("heads"));
        }

        gameLoopManager = new GameLoopManager(dialogManager);
        gameLoopManager.addBackgroundTileSet(tileManager.getTileSet("TransSiberian_Train_Interior").setScale(3.));
        gameLoopManager.addBackgroundTileSet(tileManager.getTileSet("TransSiberian_Train_Interior_Seats_foreground").setScale(3.));

        character = new CharacterContainer(tileManager.getTileSet("SkirtlookHolmes").setScale(2), 2.5, new Point2D(350, 525));
        gameLoopManager.setCharacter(character);

        gameLoopManager.addGameLogicItem(character);
        gameLoopManager.addRenderItem(character);
    }

    public void startGame(DrawTileCallback callback) {
        try {
            switch (START_STEP) {
                case CUTSCENE_START:
                    musicManager.playSong(MAIN_MENU_THEME);
                    cutsceneManager.playCutscene(0, 100, callback);
                case MAIN_MENU_START:
                    mainMenuManager.setEnabled(true, callback);
                    while (!mainMenuManager.startGame()) {
                        Thread.sleep(500);
                    }
                case GAME_START:
                    musicManager.playSong(MAIN_THEME);
                    gameLoopManager.startGameLoop(callback);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void clickOnScreen(MouseEvent mouseEvent) {
        if (mouseEvent.isMetaDown()) {
            System.out.println(mouseEvent.getX() + ", " + mouseEvent.getY());
        }
        if (dialogManager.shouldDrawDialog() && dialogManager.isWithinDialogBounds(mouseEvent.getX(), mouseEvent.getY())) {
            dialogManager.clickDialog(mouseEvent);
        } else if (gameLoopManager.isGameRunning()) {
            character.characterMove(mouseEvent, gameLoopManager.getBoundsMap());
        } else if (mainMenuManager != null) {
            mainMenuManager.clickElement(mouseEvent);
        }
    }

    public void updateSize(Canvas pane) {
        gameLoopManager.setDoARender(true);
        dialogManager.initDialogSize(pane);
        if (mainMenuManager != null) {
            mainMenuManager.updateDisplaySize(pane);
        }
    }

    public void mouseMoved(MouseEvent mouseEvent, Scene scene) {

    }
}
