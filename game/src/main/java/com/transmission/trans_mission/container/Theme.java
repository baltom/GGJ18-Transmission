package com.transmission.trans_mission.container;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class Theme extends Task {

    private final String name;
    private final Boolean eternal;
    private AudioClip audioClip;

    public Theme(String name) {
        this(name, true);
    }

    public Theme(String name, Boolean eternal) {
        this.name = name;
        this.eternal = eternal;
    }

    @Override
    protected Object call() throws Exception {
        int s = INDEFINITE;
        audioClip = new AudioClip(getClass().getResource("/music/" + name).toExternalForm());
        audioClip.setVolume(0.5f);
        if (this.eternal) {
            audioClip.setCycleCount(s);
        }
        audioClip.play();
        return null;
    }

    public void stop() {
        audioClip.stop();
    }
}
