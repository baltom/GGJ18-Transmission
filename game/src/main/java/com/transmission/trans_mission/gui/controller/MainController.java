package com.transmission.trans_mission.gui.controller;

import com.transmission.trans_mission.contract.DrawCallback;
import com.transmission.trans_mission.gui.components.ResizableCanvas;
import com.transmission.trans_mission.gui.manager.GameManager;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, DrawCallback {

    @FXML
    private StackPane pane;
    private ResizableCanvas cnvMain;

    private GameManager gameManager;
    private Scene scene;

    public void initialize(URL location, ResourceBundle resources) {
        cnvMain = new ResizableCanvas(this);
        pane.getChildren().add(cnvMain);

        cnvMain.widthProperty().bind(pane.widthProperty());
        cnvMain.heightProperty().bind(pane.heightProperty());

        gameManager = new GameManager();

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        gameManager.startGame(((image, pos, size) -> {
                            Platform.runLater(() -> {
                                GraphicsContext gc = cnvMain.getGraphicsContext2D();
                                if (gc != null) {
                                    if (image == null) {
                                        gc.setFill(Color.DARKBLUE);
                                        gc.fillRect(0, 0, pane.getWidth(), pane.getHeight());
                                    } else {
                                        gc.drawImage(image, pos.getX(), pos.getY(), size.getX(), size.getY());
                                    }
                                }
                            });
                        }));
                        return null;
                    }
                };
            }
        };
        service.start();

        cnvMain.setOnMouseClicked(gameManager::clickOnScreen);
    }

    @Override
    public void redrawScreen() {
        GraphicsContext gc = cnvMain.getGraphicsContext2D();
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(0, 0, pane.getWidth(), pane.getHeight());
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}
