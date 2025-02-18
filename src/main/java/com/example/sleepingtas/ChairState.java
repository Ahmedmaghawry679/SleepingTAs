package com.example.sleepingtas;

public class ChairState {
    private int id;
    private String name;


    private String state = "Empty...";

    public ChairState(int id) {
        this.id = id;
        this.name = "Chair-" + id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
