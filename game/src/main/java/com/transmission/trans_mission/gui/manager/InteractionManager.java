package com.transmission.trans_mission.gui.manager;

import com.google.gson.Gson;
import com.transmission.trans_mission.container.Interaction;
import javafx.geometry.Point2D;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.*;

public class InteractionManager {

    private HashMap<Integer, List<Interaction>> interactions;

    public InteractionManager() {
        interactions = new HashMap<>();
        Gson gson = new Gson();

        try {
            String s = FileUtils.readFileToString(new File(getClass().getResource("/interactions.json").toURI()), Charset.forName("UTF-8"));
            Interaction[] inter = gson.fromJson(s, Interaction[].class);
            Arrays.stream(inter).forEach(interaction -> {
                List<Interaction> list = interactions.getOrDefault(interaction.getMap(), new ArrayList<>());
                list.add(interaction.init());
                interactions.put(interaction.getMap(), list);
            });
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Optional<String> isAnInteraction(int map, double x, double y, Point2D sourcePos) {
        return isAnInteraction(map, new Point2D(x, y), sourcePos);
    }

    private Optional<String> isAnInteraction(int map, Point2D mouseClick, Point2D playerPos) {
        List<Interaction> interactions = this.interactions.getOrDefault(map, new ArrayList<>());
        if (interactions.isEmpty()) return Optional.empty();
        Optional<Interaction> any = interactions.stream()
                .filter(Interaction::getEnabled)
                .filter(p -> mouseClick.distance(playerPos) <= 100)
                .filter(interaction -> interaction.getPolygon().contains(mouseClick)).findAny();
        return any.map(Interaction::getEvent);
    }
}
