package com.transmission.trans_mission.character;

import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.contract.GameLogicCallback;
import com.transmission.trans_mission.contract.RenderCallback;
import com.transmission.trans_mission.gui.components.TileAnimation;
import com.transmission.trans_mission.gui.containers.Tile;
import com.transmission.trans_mission.gui.containers.TileSet;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import static com.transmission.trans_mission.character.MovementDirection.*;

public class CharacterContainer implements GameLogicCallback, RenderCallback {

    private final TileAnimation animation;
    private TileSet tileSet;
    private Double velocity;
    private Point2D pos;
    private boolean moving;
    private Point2D target;
    private Point2D oldPos;
    private boolean hasMoved;
    private Double angle;
    private boolean flipTile;

    public CharacterContainer(TileSet tileSet, Double velocity, Point2D pos) {
        this.tileSet = tileSet;
        this.velocity = velocity;
        this.pos = pos;
        this.hasMoved = true;

        animation = new TileAnimation(Duration.millis(500), 3);
        animation.setCycleCount(Animation.INDEFINITE);
    }

    @Override
    public void gameLogic(double delta) {
        if (isMoving()) {
            animation.play();
            Point2D posOffset = new Point2D(pos.getX() + getCurrentTile().getWidth() / 2,
                    pos.getY() + getCurrentTile().getHeight() / 2);
            if (posOffset.distance(target) < 0.5) {
                setMoving(false);
            } else {
                hasMoved = true;
                angle = calculateAngle(posOffset, target);
                oldPos = pos;
                pos = new Point2D(pos.getX() + ((velocity * Math.cos(angle)) * delta),
                        pos.getY() + ((velocity * Math.sin(angle)) * delta));
            }
        } else {
            animation.pause();
        }
    }

    private Double calculateAngle(Point2D source, Point2D target) {
        Double deltaX = target.getX() - source.getX();
        Double deltaY = target.getY() - source.getY();
        return Math.atan2(deltaY, deltaX);
    }

    public Double getAngle(Double atan2) {
        Double angle = Math.toDegrees(atan2);

        if (angle < 0) {
            angle += 360;
        }

        return angle;
    }

    @Override
    public void render(DrawTileCallback gc) {
        if (hasMoved) {
            hasMoved = false;
            Tile currentTile = getCurrentTile();
            Point2D size = new Point2D(currentTile.getWidth(), currentTile.getHeight());
            if (oldPos != null) {
                gc.draw(null, oldPos, size);
            }
            gc.draw(currentTile.getImage(), pos, size);
        }
    }

    private Tile getCurrentTile() {
        MovementDirection dir = getCurrentDirection();
        flipTile(false);
        switch (dir) {
            case NORTH:
                animation.setOffset(6);
                break;
            case SOUTH:
                animation.setOffset(3);
                break;
            case EAST:
                flipTile(true);
                animation.setOffset(0);
                break;
            case WEST:
                animation.setOffset(0);
                break;
            case NORTH_EAST:
                animation.setOffset(6);
                break;
            case NORTH_WEST:
                animation.setOffset(6);
                break;
            case SOUTH_EAST:
                animation.setOffset(3);
                break;
            case SOUTH_WEST:
                animation.setOffset(3);
                break;
        }
        return tileSet.getTile(animation.getLastIndex());
    }

    private void flipTile(boolean b) {
        flipTile = b;
    }

    public void characterMove(MouseEvent keyEvent) {
        setMoving(true);
        target = new Point2D(keyEvent.getX(), keyEvent.getY());
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void stopMovement() {
        setMoving(false);
    }

    private MovementDirection getCurrentDirection() {
        if (angle != null) {
            Double angle = getAngle(this.angle);
            MovementDirection directions[] = {WEST, SOUTH_WEST, SOUTH, SOUTH_EAST, EAST, NORTH_EAST, NORTH, NORTH_WEST};
            return directions[(int) Math.round(((angle % 360) / 45)) % 8];
        }
        return WEST;
    }
}
