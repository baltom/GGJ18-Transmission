package com.transmission.trans_mission.gui.manager;

import com.google.gson.Gson;
import com.transmission.trans_mission.container.Interaction;
import com.transmission.trans_mission.contract.DrawTileCallback;
import com.transmission.trans_mission.gui.containers.Square;
import javafx.geometry.Point2D;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class InteractionManager {

    private HashMap<Integer, List<Interaction>> interactions;

    public InteractionManager() {
        interactions = new HashMap<>();
        Gson gson = new Gson();

        try {
            String s = IOUtils.toString(getClass().getResourceAsStream("/interactions.json"), Charset.forName("UTF-8"));
            Interaction[] inter = gson.fromJson(s, Interaction[].class);
            Arrays.stream(inter).forEach(interaction -> {
                List<Interaction> list = interactions.getOrDefault(interaction.getMap(), new ArrayList<>());
                list.add(interaction.init());
                interactions.put(interaction.getMap(), list);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<String> isAnInteraction(int map, double x, double y, Point2D sourcePos, int maxDistance) {
        return isAnInteraction(map, new Point2D(x, y), sourcePos, maxDistance);
    }

    private Optional<String> isAnInteraction(int map, Point2D mouseClick, Point2D playerPos, int maxDistance) {
        List<Interaction> interactions = this.interactions.getOrDefault(map, new ArrayList<>());
        if (interactions.isEmpty()) return Optional.empty();
        Optional<Interaction> any = interactions.stream()
                .filter(Interaction::isEnabled)
                .filter(p -> {
                    boolean b = mouseClick.distance(playerPos) <= maxDistance;
                    if (!b) {
                        System.out.println("Too far ( " + mouseClick.distance(playerPos) + ")");
                    }
                    return b;
                })
                .filter(interaction -> interaction.getPolygon().contains(mouseClick)).findAny();
        return any.map(Interaction::getEvent);
    }

    public void drawInteractions(DrawTileCallback callback, int currentScene) {
        List<Interaction> interactions = this.interactions.get(currentScene);
        interactions.forEach(interaction -> {
            Square square = new Square(interaction);
            callback.draw(square);
        });
    }
}
