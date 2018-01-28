package com.transmission.trans_mission.container;

import javafx.scene.shape.Polygon;
import lombok.Data;

import java.util.List;

@Data
public class Interaction {

    private int map;
    private String event;
    private List<Pos> pos;
    private boolean enabled;
    private transient Polygon polygon;

    public Interaction init() {
        if (pos != null) {
            polygon = new Polygon();
            pos.forEach(p -> {
                polygon.getPoints().add((double) p.getX());
                polygon.getPoints().add((double) p.getY());
            });
        }
        return this;
    }
}
