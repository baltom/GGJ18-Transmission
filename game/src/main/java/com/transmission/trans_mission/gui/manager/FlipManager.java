package com.transmission.trans_mission.gui.manager;

public class FlipManager {

    private final Double horizontalOffset;
    private boolean shouldFlip;
    private boolean isFlipped;

    public FlipManager(Double horizontalOffset) {
        this.horizontalOffset = horizontalOffset;
    }

    public boolean isShouldFlip() {
        return shouldFlip;
    }

    public void setShouldFlip(boolean shouldFlip) {
        this.shouldFlip = shouldFlip;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public void setFlipped(boolean flipped) {
        isFlipped = flipped;
    }

    public double getHorizontalOffset() {
        if (shouldFlip) {
            shouldFlip = false;
            isFlipped = !isFlipped;
            if (!isFlipped) {
                return -horizontalOffset;
            } else if (isFlipped) {
                return horizontalOffset;
            }
        }
        return 0;
    }
}
