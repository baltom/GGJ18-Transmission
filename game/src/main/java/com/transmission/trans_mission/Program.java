package com.transmission.trans_mission;

import com.transmission.trans_mission.gui.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Program extends Application {

    private static final double WINDOW_SCALE = 0.9;

    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main_view.fxml"));
        Parent parent = fxmlLoader.load();

        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();

        Scene scene = new Scene(parent, bounds.getWidth() * WINDOW_SCALE, bounds.getHeight() * WINDOW_SCALE);

        ((MainController) fxmlLoader.getController()).setScene(scene);

        primaryStage.setTitle("Trans Mission - GameJam 2018");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
