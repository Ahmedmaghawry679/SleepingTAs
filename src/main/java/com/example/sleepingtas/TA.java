package com.example.sleepingtas;


import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

public class TA implements Runnable{
    private TAsRoom room;
    private int taID;

    private String state = "Sleeping...";
    private String name;


    public TA(TAsRoom room, int id){
        this.room = room;
        this.taID = id;
        this.name = "TA-" + id;
    }


    @Override
    public void run() {
        while (true) {
            room.getHelp(this);
        }

    }

    public TAsRoom getRoom() {
        return room;
    }

    public void setRoom(TAsRoom room) {
        this.room = room;
    }

    public int getTaID() {
        return taID;
    }

    public void setTaID(int taID) {
        this.taID = taID;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
