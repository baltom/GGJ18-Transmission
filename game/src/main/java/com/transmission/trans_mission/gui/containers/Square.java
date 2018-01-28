package com.transmission.trans_mission.gui.containers;

import com.transmission.trans_mission.container.BoundsMap;
import com.transmission.trans_mission.container.Interaction;
import com.transmission.trans_mission.contract.Drawable;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

public class Square implements Drawable<Square> {

    private BoundsMap boundsMap = null;
    private Interaction interaction = null;

    public Square(BoundsMap boundsMap) {
        this.boundsMap = boundsMap;
    }

    public Square(Interaction interaction) {
        this.interaction = interaction;
    }

    @Override
    public Point2D getSize() {
        return null;
    }

    @Override
    public Point2D getPos() {
        return null;
    }

    @Override
    public Square getItem() {
        return this;
    }

    @Override
    public Color getBackgroundColor() {
        return null;
    }

    @Override
    public Color getBorderColor() {
        return null;
    }

    public Point2D getPoint(int num) {
        if (boundsMap != null) {
            switch (num) {
                case 0:
                    return new Point2D(boundsMap.getBottomLeft().getX(), boundsMap.getBottomLeft().getY());
                case 1:
                    return new Point2D(boundsMap.getBottomRight().getX(), boundsMap.getBottomRight().getY());
                case 2:
                    return new Point2D(boundsMap.getTopRight().getX(), boundsMap.getTopRight().getY());
                case 3:
                    return new Point2D(boundsMap.getTopLeft().getX(), boundsMap.getTopLeft().getY());
            }
        } else if (interaction != null) {
            return new Point2D(interaction.getPos().get(num).getX(), interaction.getPos().get(num).getY());
        }
        return null;
    }

    public boolean isInteraction() {
        return interaction != null;
    }
}
