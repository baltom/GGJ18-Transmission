package com.transmission.trans_mission.character;

import com.sun.istack.internal.NotNull;
import com.transmission.trans_mission.container.BoundsMap;
import com.transmission.trans_mission.container.Pos;
import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.contract.GameLogicCallback;
import com.transmission.trans_mission.contract.RenderCallback;
import com.transmission.trans_mission.gui.components.TileAnimation;
import com.transmission.trans_mission.gui.containers.Tile;
import com.transmission.trans_mission.gui.containers.TileDrawable;
import com.transmission.trans_mission.gui.containers.TileSet;
import com.transmission.trans_mission.gui.manager.FlipManager;
import javafx.animation.Animation;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.transmission.trans_mission.character.MovementDirection.*;

public class CharacterContainer implements GameLogicCallback, RenderCallback {

    private TileAnimation animation;
    private TileSet tileSet;
    private Double velocity;
    private Point2D pos;
    private boolean moving;
    private Point2D target;
    private Double angle;
    private FlipManager flipManager;
    private List<Point2D> allowedPoints;
    private BoundsMap boundsMap;

    public CharacterContainer(@NotNull TileSet tileSet, Double velocity, Point2D pos) {
        this.tileSet = tileSet;
        this.velocity = velocity;
        this.pos = pos;
        this.flipManager = new FlipManager(tileSet.getTile(0).getWidth());

        setMoving(false);
    }

    @Override
    public boolean gameLogic(double delta) {
        if (isMoving()) {
            animation.setAnimationLength(2);
            animation.play();
            Point2D posOffset;
            if (flipManager.isFlipped()) {
                posOffset = new Point2D(pos.getX() - getCurrentTile().getWidth() / 2,
                        pos.getY() + getCurrentTile().getHeight() / 2 + 40);
            } else {
                posOffset = new Point2D(pos.getX() + getCurrentTile().getWidth() / 2,
                        pos.getY() + getCurrentTile().getHeight() / 2 + 40);
            }
            if (posOffset.distance(target) < 1.5) {
                setMoving(false);
            } else {
                angle = calculateAngle(posOffset, target);
                pos = new Point2D((pos.getX() + flipManager.getHorizontalOffset()) + ((velocity * Math.cos(angle)) * delta),
                        pos.getY() + ((velocity * Math.sin(angle)) * delta));
            }
            return true;
        } else {
            animation.play();
        }
        return false;
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
        Tile currentTile = getCurrentTile();
        Point2D size = new Point2D(currentTile.getWidth(), currentTile.getHeight());
        gc.draw(new TileDrawable(size, pos, currentTile.getImage()));
    }

    private Tile getCurrentTile() {
        if (isMoving()) {
            MovementDirection dir = getCurrentDirection();
            switch (dir) {
                case NORTH:
                    animation.setOffset(29);
                    break;
                case SOUTH:
                    animation.setOffset(27);
                    break;
                case EAST:
                    animation.setOffset(25);
                    break;
                case WEST:
                    animation.setOffset(23);
                    break;
                case NORTH_EAST:
                    animation.setOffset(25);
                    break;
                case NORTH_WEST:
                    animation.setOffset(23);
                    break;
                case SOUTH_EAST:
                    animation.setOffset(27);
                    break;
                case SOUTH_WEST:
                    animation.setOffset(27);
                    break;
            }
        }
        return tileSet.getTile(animation.getLastIndex());
    }

    public void characterMove(MouseEvent keyEvent, BoundsMap map) {
        if (isWithinAllowedBounds(keyEvent.getX(), keyEvent.getY(), map)) {
            setMoving(true);
            target = new Point2D(keyEvent.getX(), keyEvent.getY());
        } else {
            setMoving(true);
            target = findClosestPoint(keyEvent.getX(), keyEvent.getY());
        }
    }

    private Point2D findClosestPoint(double x, double y) {
        final double[] shortestDistance = {-1};
        Point2D target = new Point2D(x, y);
        AtomicReference<Point2D> foundPoint = new AtomicReference<>();
        allowedPoints.forEach(p -> {
            double distance = p.distance(target);
            if (shortestDistance[0] == -1 || distance < shortestDistance[0]) {
                shortestDistance[0] = distance;
                foundPoint.set(p);
            }
        });
        return foundPoint.get();
    }

    public void setBoundsMap(BoundsMap boundsMap) {
        this.boundsMap = boundsMap;
        this.allowedPoints = new ArrayList<>();
        calculateClosestPoints();
    }

    private void calculateClosestPoints() {
        Pos[] pos = {boundsMap.getBottomLeft(), boundsMap.getBottomRight(), boundsMap.getTopRight(), boundsMap.getTopLeft()};
        for (int i = 0; i < pos.length; i++) {
            int nextPos = i >= pos.length - 1 ? 0 : i + 1;
            Point2D start = new Point2D(pos[i].getX(), pos[i].getY());
            Point2D goal = new Point2D(pos[nextPos].getX(), pos[nextPos].getY());
            Point2D current = new Point2D(start.getX(), start.getY());
            double speed = 20;
            do {
                Double angle = calculateAngle(current, goal);

                current = new Point2D(current.getX() + speed * Math.cos(angle),
                        current.getY() + speed * Math.sin(angle));
                allowedPoints.add(current);
            } while (goal.distance(current) > 10);
        }
    }

    private boolean isWithinAllowedBounds(double x, double y, BoundsMap map) {
        return map.isWithinBounds((int) x, (int) y);
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        if (!moving) {
            animation = new TileAnimation(Duration.millis(200 * 22), 22);
            animation.setCycleCount(Animation.INDEFINITE);
        } else {
            animation = new TileAnimation(Duration.millis(200 * 2), 2);
            animation.setCycleCount(Animation.INDEFINITE);
        }
        this.moving = moving;
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
