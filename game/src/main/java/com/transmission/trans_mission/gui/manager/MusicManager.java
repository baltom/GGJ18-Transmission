package com.transmission.trans_mission.gui.manager;

import com.transmission.trans_mission.container.Theme;

public class MusicManager {

    public static final String TOILET_THEME = "dassen.wav";
    public static final String END_THEME = "end_theme.wav";
    public static final String MAIN_MENU_THEME = "main_menu.wav";
    public static final String MAIN_THEME = "main_theme.wav";
    public static final String SELECT_1 = "select1.wav";
    public static final String SELECT_2 = "select2.wav";

    private Thread currentTheme;
    private Theme theme;

    public MusicManager() {
    }

    public void playSong(String name) {
        if (currentTheme != null) {
            theme.stop();
            currentTheme.interrupt();
        }
        theme = new Theme(name);
        currentTheme = new Thread(theme);
        currentTheme.start();
    }
}
