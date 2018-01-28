package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.character.CharacterContainer;
import com.transmission.trans_mission.contract.DrawTileCallback;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

import static com.transmission.trans_mission.gui.manager.CutsceneManager.*;
import static com.transmission.trans_mission.gui.manager.MusicManager.*;
import static com.transmission.trans_mission.gui.manager.SceneManager.*;

public class GameManager {

    public final static int CUTSCENE_START = 0;
    public final static int MAIN_MENU_START = 1;
    public final static int INTRO_CUTSCENE = 2;
    public final static int GAME_START = 3;

    private final int START_STEP = CUTSCENE_START;

    private CharacterContainer character;
    private GameLoopManager gameLoopManager;
    private TileManager tileManager;
    private DialogManager dialogManager;
    private CutsceneManager cutsceneManager;
    private MainMenuManager mainMenuManager;
    private MusicManager musicManager;
    private InteractionManager interactionManager;
    private DrawTileCallback callback;

    public GameManager(Canvas parent) {
        musicManager = new MusicManager();
        tileManager = new TileManager();
        tileManager.setTileScale(3.);
        tileManager.loadAllTiles(START_STEP);
        cutsceneManager = new CutsceneManager();
        interactionManager = new InteractionManager();

        switch (START_STEP) {
            case CUTSCENE_START:
                cutsceneManager.addCutscene(START_SCENE, tileManager.getTileSet("TransSiberian_Train_Helicoptershot").setScale(3.));
            case MAIN_MENU_START:
                mainMenuManager = new MainMenuManager(tileManager.getTileSet("TransSiberian_Train").setScale(3.), parent);
            case INTRO_CUTSCENE:
                cutsceneManager.addCutscene(INTRO_SCENE, tileManager.getTileSet("TransSiberian_Train_Cutscene_MurderDiscovery").setScale(3.));
            case GAME_START:
                dialogManager = new DialogManager(parent, tileManager.getTileSet("Skirtlook_portrait"));
                cutsceneManager.addCutscene(MURDER_CUTSCENE, tileManager.getTileSet("Transsiberian_Cutscene_Sunglasses").setScale(3.));
        }

        gameLoopManager = new GameLoopManager(dialogManager, interactionManager);
        gameLoopManager.addBackgroundTileSet(TRAIN_SCENE, tileManager.getTileSet("TransSiberian_Train_Interior_withPeople").setScale(3.));
        gameLoopManager.addBackgroundTileSet(TRAIN_SCENE, tileManager.getTileSet("TransSiberian_Train_Interior_Seats_foreground").setScale(3.));
        gameLoopManager.addBackgroundTileSet(TOILET_SCENE, tileManager.getTileSet("Transsiberian_Train_ToiletFemaleInterior").setScale(3.));
        gameLoopManager.addBackgroundTileSet(MURDER_SCENE, tileManager.getTileSet("Transsiberian_Toilet_MurderCloseup").setScale(3.));
        gameLoopManager.addBackgroundTileSet(PUBE_SCENE, tileManager.getTileSet("TransSiberian_PubeScene").setScale(3.));

        character = new CharacterContainer(tileManager.getTileSet("SkirtlookHolmes").setScale(2), 2.5, new Point2D(545, 428));
        gameLoopManager.setCharacter(character);

        gameLoopManager.addGameLogicItem(character);
        gameLoopManager.addRenderItem(character);

    }

    public void startGame(DrawTileCallback callback) {
        this.callback = callback;
        try {
            switch (START_STEP) {
                case CUTSCENE_START:
                    musicManager.playSong(MAIN_MENU_THEME);
                    cutsceneManager.playCutscene(START_SCENE, 100, callback);
                case MAIN_MENU_START:
                    mainMenuManager.setEnabled(true, callback);
                    while (!mainMenuManager.startGame()) {
                        Thread.sleep(500);
                    }
                case INTRO_CUTSCENE:
                    musicManager.playSong(null);
                    cutsceneManager.playCutscene(INTRO_SCENE, 200, callback);
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
        Optional<String> interaction =
                interactionManager.isAnInteraction(
                        gameLoopManager.getCurrentScene(),
                        mouseEvent.getX(), mouseEvent.getY(),
                        character.getPos(),
                        gameLoopManager.getCurrentMaxRange());

        interaction.ifPresent(this::handleInteraction);
        if (dialogManager.shouldDrawDialog() && dialogManager.isWithinDialogBounds(mouseEvent.getX(), mouseEvent.getY())) {
            dialogManager.clickDialog(mouseEvent);
        } else if (gameLoopManager.isGameRunning()) {
            character.characterMove(mouseEvent, gameLoopManager.getBoundsMap());
        } else if (mainMenuManager != null) {
            mainMenuManager.clickElement(mouseEvent);
        }
    }

    private void handleInteraction(String event) {
        switch (event) {
            case "TOILET_DOOR":
                musicManager.playSong(TOILET_THEME);
                gameLoopManager.changeScene(TOILET_SCENE);
                break;
            case "OUT_TOILET_DOOR":
                musicManager.playSong(MAIN_THEME);
                gameLoopManager.changeScene(TRAIN_SCENE);
                break;
            case "DEAD_WOMAN":
                gameLoopManager.changeScene(MURDER_SCENE);
                break;
            case "PUBE":
                gameLoopManager.changeScene(PUBE_SCENE);
                break;
            case "PUBE_SCENE":
                gameLoopManager.playCutscene(MURDER_CUTSCENE, cutsceneManager);
                //cutsceneManager.playCutscene(MURDER_CUTSCENE, 200, callback);
                System.out.println("Cutscene!");
                break;
        }
        System.out.println(event);
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
