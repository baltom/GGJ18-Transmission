package com.transmission.trans_mission.gui.controller;

import com.transmission.trans_mission.contract.DrawCallback;
import com.transmission.trans_mission.contract.Drawable;
import com.transmission.trans_mission.gui.components.ResizableCanvas;
import com.transmission.trans_mission.gui.containers.Dialog;
import com.transmission.trans_mission.gui.containers.MenuItem;
import com.transmission.trans_mission.gui.manager.GameManager;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
        cnvMain.setId("canvas");

        pane.widthProperty().addListener((e) -> updateSize());
        pane.heightProperty().addListener((e) -> updateSize());

        gameManager = new GameManager(cnvMain);

        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() {
                        gameManager.startGame(((drawable) -> {
                            Platform.runLater(() -> {
                                GraphicsContext gc = cnvMain.getGraphicsContext2D();
                                if (gc != null) {
                                    drawOnScreen(gc, drawable);
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

    private void updateSize() {
        gameManager.updateSize(cnvMain);
    }

    private void drawOnScreen(GraphicsContext gc, Drawable drawable) {
        if (drawable == null) {
            gc.setFill(Color.DARKBLUE);
            gc.fillRect(0, 0, pane.getWidth(), pane.getHeight());
        } else if (drawable.getItem() instanceof Image) {
            if (drawable.getSize() == null) {
                gc.drawImage((Image) drawable.getItem(), drawable.getPos().getX(), drawable.getPos().getY(), cnvMain.getWidth(), cnvMain.getHeight());
            } else {
                gc.drawImage((Image) drawable.getItem(), drawable.getPos().getX(), drawable.getPos().getY(), drawable.getSize().getX(), drawable.getSize().getY());
            }
        } else if (drawable.getItem() instanceof Dialog) {
            Dialog dialog = (Dialog) drawable.getItem();

            // Dialog Background
            gc.setFill(dialog.getBackgroundColor());
            gc.fillRect(dialog.getPos().getX(), dialog.getPos().getY(),
                    dialog.getSize().getX(), dialog.getSize().getY());

            // Dialog image
            gc.drawImage(dialog.getTile().getImage(), dialog.getPos().getX(),
                    dialog.getPos().getY(), dialog.getSize().getY(), dialog.getSize().getY());

            // Dialog border
            gc.setStroke(dialog.getBorderColor());
            gc.strokeRect(dialog.getPos().getX(), dialog.getPos().getY(), dialog.getSize().getX(), dialog.getSize().getY());

            // Dialog text
            gc.setFill(Color.WHITE);
            gc.setFont(new Font("Comic Sans MS", 22.));
            gc.fillText(dialog.getText(),
                    dialog.getPos().getX() + dialog.getSize().getX() / 2 - (dialog.getText()).length(),
                    dialog.getPos().getY() + dialog.getSize().getY() / 2);
        } else if (drawable.getItem() instanceof MenuItem) {
            MenuItem menu = (MenuItem) drawable.getItem();

            gc.setFill(menu.getBackgroundColor());
            gc.fillRect(menu.getPos().getX(), menu.getPos().getY(), menu.getSize().getX(), menu.getSize().getY());


            Font font = new Font("Comic Sans MS", 22.);
            gc.setFill(Color.BLACK);
            gc.setFont(font);

            final Text text = new Text(menu.getText());
            text.setFont(font);

            final double width = text.getLayoutBounds().getWidth();

            gc.fillText(menu.getText(),
                    menu.getPos().getX() + menu.getSize().getX() / 2 - (width / 2),
                    menu.getPos().getY() + menu.getSize().getY() / 2);

            gc.setStroke(menu.getBorderColor());
            gc.strokeRect(menu.getPos().getX(), menu.getPos().getY(), menu.getSize().getX(), menu.getSize().getY());
        }
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
