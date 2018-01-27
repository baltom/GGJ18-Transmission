package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.contract.GameLogicCallback;
import com.transmission.trans_mission.contract.RenderCallback;

import java.util.ArrayList;
import java.util.List;

public class GameLoopManager {

    private final DialogManager dialogManager;
    private List<RenderCallback> renderItems;
    private List<GameLogicCallback> gameLogic;
    private boolean gameRunning;
    private long lastFpsTime;
    private int fps;
    private boolean doARender;
    private boolean hasDoneInitialRender = false;

    public GameLoopManager(DialogManager dialogManager) {
        renderItems = new ArrayList<>();
        gameLogic = new ArrayList<>();
        this.dialogManager = dialogManager;
    }

    public void addRenderItem(RenderCallback item) {
        renderItems.add(item);
    }

    public void addGameLogicItem(GameLogicCallback item) {
        gameLogic.add(item);
    }

    public void endGame() {
        gameRunning = false;
    }

    public void startGameLoop(DrawTileCallback callback) throws InterruptedException {
        gameRunning = true;
        long lastLoopTime = System.nanoTime();
        final int TARGET_FPS = 60;
        final long OPTIMAL_TIME = 1_000_000_000 / TARGET_FPS;

        while (gameRunning) {
            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = updateLength / ((double) OPTIMAL_TIME);

            lastFpsTime += updateLength;
            fps++;

            if (lastFpsTime >= 1_000_000_000) {
                System.out.println("(FPS: " + fps + ")");
                lastFpsTime = 0;
                fps = 0;
            }

            doGameUpdates(delta);
            render(callback);
            Thread.sleep((lastLoopTime - System.nanoTime() + OPTIMAL_TIME) / 1_000_000);
        }
    }

    private void render(DrawTileCallback callback) {
        if (doARender || !hasDoneInitialRender) {
            hasDoneInitialRender = true;
            doARender = false;
            callback.draw(null);
            renderItems.forEach(render -> render.render(callback));
            if (dialogManager.shouldDrawDialog()) {
                callback.draw(dialogManager.getDialog());
            }
        }
    }

    public void setDoARender(boolean doARender) {
        if (doARender) {
            this.doARender = true;
        }
    }

    private void doGameUpdates(double delta) {
        gameLogic.forEach(logic -> setDoARender(logic.gameLogic(delta)));
    }
}
