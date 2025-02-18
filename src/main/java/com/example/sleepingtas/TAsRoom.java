package com.example.sleepingtas;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TAsRoom {

    private final ReentrantLock mutex = new ReentrantLock();
    private int waitingChairs, numOfTAs;
    private int totalStudentsDone, backLaterCounter;
    private LinkedList<Student> studentList;
    private LinkedList<Student> studentBackLater;
    private Semaphore available;
    private Random r = new Random();
    private TableView<TA> tasTable;
    private boolean[] chairs;
    ObservableList<ChairState> chairStates;
    TableView<ChairState> chairsTable;
    private TextArea backLater;



    public TAsRoom(int numOfChairs, int numOfTAs, int numOfStudents, ObservableList<ChairState> chairStates, TableView<ChairState> chairsTable , TableView<TA> tasTable, TextArea backLater) {
        this.waitingChairs = numOfChairs;
        this.numOfTAs = numOfTAs;
        this.studentList = new LinkedList<Student>();
        this.studentBackLater = new LinkedList<Student>();
        this.available = new Semaphore(numOfTAs);
        this.tasTable = tasTable;
        this.chairs = new boolean[numOfChairs]; ///
        this.chairStates = chairStates;
        this.chairsTable = chairsTable;
        this.backLater = backLater;
    }


    public void getHelp(TA ta){
        Student student;
        synchronized(studentList){
            while(studentList.isEmpty()){
                System.out.println("( TA-" + ta.getTaID() + " )is Waiting For a Student and Sleeps On His Desk");

                javafx.application.Platform.runLater(() -> {
                    ta.setState("Sleeping...");
                    tasTable.refresh();
                });

                try {
                    Thread.sleep(500);
                    studentList.wait(); // Makes The TA Wait If No Students Are Available
                } catch (InterruptedException ex) {
                    Logger.getLogger(TAsRoom.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            student = studentList.poll();
            System.out.println("( Student-" + student.getStudentID() + " ) Finds ( TA-" + ta.getTaID() + " )");

            javafx.application.Platform.runLater(() -> {
                ta.setState(student.getName());
                tasTable.refresh();
                synchronized (chairStates) {
                    if (student.getSeatNumber() != -1) {
                        chairs[student.getSeatNumber()] = false;
                        chairStates.get(student.getSeatNumber()).setState("Empty...");
                        chairsTable.refresh();
                        student.setSeatNumber(-1);
                    }
                }
            });

        }
        try{
            available.acquire();

            System.out.println("( Student-" + student.getStudentID() + " ) Asks ( TA-" + ta.getTaID() + " )");
            int delay = 4000 + r.nextInt(1000,2000);
            Thread.sleep(delay); // Simulation For The Ask
            System.out.println("\nCompleted Help By The ( TA-" + ta.getTaID() + " ) For The ( Student-" + student.getStudentID() + " ) in  ( " + delay / 1000 + " ) Secnods\n");
            // set ta state to sleeping but we'll see
            mutex.lock();
            try{totalStudentsDone++;}
            finally{mutex.unlock();}

            available.release();
        }catch (InterruptedException ex) {
            Logger.getLogger(TAsRoom.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
//    public void sleepTA(){
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TAsRoom.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public void enterRoom(Student student) {
        System.out.println("( Student-" + student.getStudentID() + " ) Enter The TAs Room");
        synchronized(studentList){
            if(available.availablePermits() > 0){
                studentList.offer(student); // Add Student To Student List
                studentList.notify(); // Wakes Up a TA Thread
            }else if(studentList.size() >= waitingChairs){
                System.out.println("\nNo chair available " + "for ( Student-" + student.getStudentID() + " ) So Student leaves and will come back later\n");
                studentBackLater.add(student);

                javafx.application.Platform.runLater(() -> {
                    backLater.appendText(student.getName() + "\n");
                });

                mutex.lock();
                try{backLaterCounter++;}
                finally{mutex.unlock();}
            }else {
                studentList.offer(student); // Add Student To Student List
                System.out.println("\nAll TAs are busy so ( Student-" + student.getStudentID() + " ) takes a chair and waits\n");
                // GUI For Update Chair States
                synchronized (chairStates) {
                    for (int i = 0; i < waitingChairs; i++) {
                        if (!chairs[i]) {
                            chairs[i] = true;
                            student.setSeatNumber(i);

                            javafx.application.Platform.runLater(() -> {
                                chairStates.get(student.getSeatNumber()).setState(student.getName());
                                chairsTable.refresh();
                            });

                            break;
                        }
                    }
                }
                System.out.println("===================================== " + student.getName() + " Sits On Chair-" + student.getSeatNumber() + " =====================================");

                studentList.notify();
            }
        }
    }

    public List<Student> Backlater() {
        return studentBackLater;
    }
    public int getTotalStudentsDone(){
        return totalStudentsDone;
    }
    public int getBackLaterCounter(){
        return backLaterCounter;
    }

    public String printSummary() {
//        System.out.println("\n=== Summary ===");
        return ("\nTotal Students: "+totalStudentsDone+
                "\nTotal Students Helped: "+getTotalStudentsDone()
                +"\nTotal Students Returned: "+getBackLaterCounter());
    }

}




