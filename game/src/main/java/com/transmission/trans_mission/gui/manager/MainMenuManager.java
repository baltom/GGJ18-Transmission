package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.gui.containers.MenuItem;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.transmission.trans_mission.gui.manager.MusicManager.SELECT_1;
import static com.transmission.trans_mission.gui.manager.MusicManager.SELECT_2;

public class MainMenuManager {

    private final Double MENU_ITEM_WIDTH = 200.;

    private final TileSet tileset;
    private Canvas canvas;
    private boolean enabled;
    private List<MenuItem> menuItems;
    private boolean startGame = false;
    private MusicManager musicManager;

    public MainMenuManager(TileSet tileSet, Canvas canvas) {
        this.tileset = tileSet;
        this.canvas = canvas;
        this.musicManager = new MusicManager();
    }

    public void setEnabled(boolean enabled, DrawTileCallback callback) throws InterruptedException {
        this.enabled = enabled;
        int tile = 1;
        while (this.enabled) {
            callback.draw(new TileDrawable(null, new Point2D(0, 0), tileset.getTile(tile++).getImage()));
            if (tile >= tileset.getTileCount()) tile = 1;
            initMenu();
            drawMenuItems(callback);
            Thread.sleep(20);
        }
    }

    private void drawMenuItems(DrawTileCallback callback) {
        menuItems.forEach(callback::draw);
    }

    public boolean startGame() {
        return startGame;
    }

    public void clickElement(MouseEvent mouseEvent) {
        if (enabled) {
            Optional<MenuItem> found = menuItems.stream().filter(e -> e.isInsideBounds(mouseEvent.getX(), mouseEvent.getY())).findAny();
            if (found.isPresent()) {
                playSelectSound();
                if (found.get().equals(menuItems.get(0))) {
                    this.enabled = false;
                    this.startGame = true;
                }
            }
        }
    }

    private void playSelectSound() {
        if (Math.random() < 0.5) {
            musicManager.playSong(SELECT_1, false);
        } else {
            musicManager.playSong(SELECT_2, false);
        }
    }

    public void updateDisplaySize(Canvas pane) {
        this.canvas = pane;
    }

    private void initMenu() {
        Point2D menuPosStart = new Point2D((canvas.getWidth() / 2 - MENU_ITEM_WIDTH / 2), canvas.getHeight() / 3);
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(getPos(0, menuPosStart), "Start Game", MENU_ITEM_WIDTH));
        menuItems.add(new MenuItem(getPos(1, menuPosStart), "About", MENU_ITEM_WIDTH));
    }

    private Point2D getPos(int i, Point2D menuPosStart) {
        return new Point2D(menuPosStart.getX(), menuPosStart.getY() + (i * 70));
    }
}
