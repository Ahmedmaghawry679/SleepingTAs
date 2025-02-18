package com.example.sleepingtas;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.System.exit;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationScene {
    public static void showScene( Stage window, int numChairs, int numTAs, int numStudents) {




        // Chairs Table
        ObservableList<ChairState> chairStates = FXCollections.observableArrayList();
        TableView<ChairState> chairsTable = new TableView<>();
        chairsTable.setItems(chairStates);
        chairsTable.setPrefHeight(200);
        chairsTable.setPrefWidth(300);
        chairsTable.setStyle("-fx-font-size: 14px;");

        TableColumn<ChairState, String> chairColumn = new TableColumn<>("Chair");
        chairColumn.setCellValueFactory(new PropertyValueFactory<>("name") );
        chairColumn.setMinWidth(147.5);

        TableColumn<ChairState, String> chairStateColumn = new TableColumn<>("State");
        chairStateColumn.setCellValueFactory(new PropertyValueFactory<>("state") );
        chairStateColumn.setMinWidth(147.5);

        chairsTable.getColumns().addAll(chairColumn, chairStateColumn);


        // TAs Table
        ObservableList<TA> tas = FXCollections.observableArrayList();
        TableView<TA> tasTable = new TableView<>();
        tasTable.setItems(tas);
        tasTable.setPrefHeight(200);
        tasTable.setPrefWidth(300);
        tasTable.setPrefWidth(300);
        tasTable.setStyle("-fx-font-size: 14px;");

        TableColumn<TA, String> taColumn = new TableColumn<>("TA");
        taColumn.setCellValueFactory(new PropertyValueFactory<>("name") );
        taColumn.setMinWidth(147.5);

        TableColumn<TA, String> taStateColumn = new TableColumn<>("State");
        taStateColumn.setCellValueFactory(new PropertyValueFactory<>("state") );
        taStateColumn.setMinWidth(147.5);

        tasTable.getColumns().addAll(taColumn, taStateColumn);


        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Region spacer3 = new Region();
        HBox.setHgrow(spacer3, Priority.ALWAYS);
        Region spacer4 = new Region();
        HBox.setHgrow(spacer4, Priority.ALWAYS);


        // HBox For TAs Table and Chairs Table
        HBox tablesHBox = new HBox(10, spacer4,chairsTable,tasTable ,spacer1);
        tablesHBox.setAlignment(Pos.CENTER);



        // Back Later List
        Label backlaterLabel = new Label("Students Back Later");
        backlaterLabel.setFont(new Font("Lucida Console", 20));

        TextArea backLater = new TextArea();
        backLater.setPrefWidth(610);
        backLater.setEditable(false);
//        backLater.appendText("Student-5\n");
//        backLater.appendText("Student-6\n");




        // Hbox For Back Later list
        HBox backHBox = new HBox(spacer2, backLater, spacer3);
        backHBox.setAlignment(Pos.CENTER);

        // Buttons
        Button homeButton = new Button("Home");
        homeButton.setDisable(true); // Initially disabled
        homeButton.setOnAction(e -> InputScene.showScene(window)); // Replace Scene1 with your actual scene class

        Button summaryButton = new Button("Get Summary");
        summaryButton.setDisable(true); // Initially disabled


        HBox buttonsBox = new HBox(10, summaryButton, homeButton);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.setPadding(new Insets(10));

        // Layout
        VBox root = new VBox(10, tablesHBox, backlaterLabel,backHBox, buttonsBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 700, 500);
        window.setScene(scene);
        window.setTitle("Sleeping TAs Simulation");
        window.show();



        runThreads(homeButton,summaryButton, numChairs,numTAs, numStudents, chairStates, chairsTable,  tas,  tasTable, backLater);



    }

    private static void runThreads(Button homeButton,Button summaryButton, int numChairs, int numTAs, int numStudents, ObservableList<ChairState> chairStates, TableView<ChairState> chairsTable, ObservableList<TA> tas, TableView<TA> tasTable, TextArea backLater){
        TAsRoom room = new TAsRoom(numChairs, numTAs, numStudents,  chairStates, chairsTable, tasTable, backLater); // Initialize The Room
        ExecutorService exec = Executors.newFixedThreadPool(12);
        Random r = new Random();

        exec.execute( new Thread(() -> {
            exec.execute(new Thread(() -> {
            for(int i = 1; i <= numChairs; i++){
                ChairState chairState = new ChairState(i);
                chairStates.add(chairState);
            }
            }));

            for(int i = 1; i <= numTAs; i++){
                TA ta = new TA(room, i);
                Thread thTA = new Thread(ta);
                tas.add(ta);
                exec.execute(thTA);
            }


            for(int i = 1; i <= numStudents; i++){
                Student student = new Student(room, i);
                Thread thStudent = new Thread(student);
                exec.execute(thStudent);

                double val = r.nextGaussian() * 2000 + 500;
                int delay = Math.abs((int) Math.round(val));
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimulationScene.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            List<Student> backLaterList = room.Backlater();

            for(int i =0; i<backLaterList.size(); i++){
                Student student = backLaterList.get(i);
                Thread thStudent = new Thread(student);
                exec.execute(thStudent);

                double val = r.nextGaussian() * 2000 + 500;
                int Delay = Math.abs((int) Math.round(val));
                try {
                    Thread.sleep(Delay); // Simulate Enter Room Process
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimulationScene.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            try {
                exec.awaitTermination((15), SECONDS);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationScene.class.getName()).log(Level.SEVERE, null, ex);
            }
            exec.shutdown();


            javafx.application.Platform.runLater(() -> {
                if(exec.isShutdown()) {
                    summaryButton.setDisable(false);
                    homeButton.setDisable(false);
                    summaryButton.setOnAction(e -> showSummary(room)); // Replace with summary display logic
                }
            });


        }));









    }

    private static void showSummary(TAsRoom room) {
        Stage window = new Stage();
        window.setTitle("Sleeping TAs Summary");
        window.initModality(Modality.APPLICATION_MODAL); // Block Input Events Or User Interaction With Other Windows

        Text title = new Text("Summary");
        title.setFont(new Font("Lucida Console", 40));

        TextArea summaryArea = new TextArea();
        summaryArea.setEditable(false);
        summaryArea.appendText(room.printSummary()); ///
        summaryArea.setFont(new Font("Arial", 25));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(title, summaryArea, closeButton);


        Scene scene = new Scene(layout, 400, 300);
        window.setScene(scene);
        window.showAndWait(); // A Stage Must Closed Before Back To The Caller This Blocks Any Processing On Other Stages Until This Is Done
    }


}










/*
//        for(int i = 1; i<= numChairs; i++){
//            ChairState chairState = new ChairState(i);
//            chairStates.add(chairState);
//            new Thread(() -> {
//                if(chairState.getId() == 1 || chairState.getId() == 3){
//                    chairState.setState("Student-" + chairState.getId());
//                }
//                try {
//                    Thread.sleep(10000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }
//
//        for(int i = 1; i<= numTAs; i++){
//            TAState ta = new TAState(i);
//            tas.add(ta); // GUI
//            new Thread(() -> {
//                if(ta.getId() == 2 || ta.getId() == 4){
//                    ta.setState("Student-" + ta.getId());
//                }
//                try {
//                    Thread.sleep(10000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }).start();
//        }


//        // Add Chairs
//        new Thread(() -> {
//            for(int i = 1; i <= numChairs; i++){
//                ChairState chairState = new ChairState(i);
//                chairStates.add(chairState); // GUI
//                if(i == 1 || i == 3){
//                    chairState.setState("Student-" + i);
//                }
//                try {
//                    Thread.sleep(1000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//
//        // Add TAs
//        new Thread(() -> {
//            for(int i = 1; i <= numTAs; i++){
//                TAState ta = new TAState(i);
//                tas.add(ta); // GUI
//                if(i == 2 || i == 4){
//                    ta.setState("Student-" + i);
//                }
//                try {
//                    Thread.sleep(1000);
//                }catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
 */

//        new Thread(() -> {
//            javafx.application.Platform.runLater(() -> {
//                // tasTable.refresh();
//                // chairsTable.refresh();
//            });
//
//        }).start();
