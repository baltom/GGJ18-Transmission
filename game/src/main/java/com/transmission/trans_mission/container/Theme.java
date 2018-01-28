package com.transmission.trans_mission.container;

import javafx.concurrent.Task;
import javafx.scene.media.AudioClip;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

public class Theme extends Task {

    private final String name;
    private AudioClip audioClip;

    public Theme(String name) {
        this.name = name;
    }

    @Override
    protected Object call() throws Exception {
        int s = INDEFINITE;
        audioClip = new AudioClip(getClass().getResource("/music/" + name).toExternalForm());
        audioClip.setVolume(0.5f);
        audioClip.setCycleCount(s);
        audioClip.play();
        return null;
    }

    public void stop() {
        audioClip.stop();
    }
}
