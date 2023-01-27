package com.epam.hospital.model;

public abstract class Entity {
    public static final int START_SEQ = 1;

    private int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
