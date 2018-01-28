package com.transmission.trans_mission.gui.components;

import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.util.Duration;

public class TileAnimation extends Transition {

    private int lastIndex;
    private int offset;
    private int animationLength;

    public TileAnimation(
            Duration duration,
            int animationLength) {
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        this.animationLength = animationLength;
    }

    @Override
    protected void interpolate(double frac) {
        final int index = Math.min((int) Math.floor(frac * animationLength), animationLength - 1);
        if (index != lastIndex) {
            lastIndex = index + offset;
        }
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public void setAnimationLength(int animationLength) {
        this.animationLength = animationLength;
    }
}
