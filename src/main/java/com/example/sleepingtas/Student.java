package com.example.sleepingtas;

import java.util.Date;
public class Student implements Runnable {
    private int studentID;
    private String name;
    private TAsRoom room;
    private int seatNumber = -1;


    public Student(TAsRoom room, int studentID){
        this.room = room;
        this.studentID = studentID;
        this.name = "Student-" + studentID;
    }

    public int getStudentID(){
        return this.studentID;
    }


    @Override
    // Starting Thread
    public void run() {
        room.enterRoom(this);
    }


    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TAsRoom getRoom() {
        return room;
    }

    public void setRoom(TAsRoom room) {
        this.room = room;
    }
}
