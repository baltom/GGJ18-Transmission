package com.transmission.trans_mission.container;

import javafx.scene.shape.Polygon;

import java.util.List;

public class Interaction {

    private int map;
    private String event;
    private List<Pos> pos;
    private Boolean enabled;
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

    public int getMap() {
        return map;
    }

    public void setMap(int map) {
        this.map = map;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public List<Pos> getPos() {
        return pos;
    }

    public void setPos(List<Pos> pos) {
        this.pos = pos;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    @Override
    public String toString() {
        return "Interaction{" +
                "event='" + event + '\'' +
                ", pos=" + pos +
                ", enabled=" + enabled +
                '}';
    }
}
