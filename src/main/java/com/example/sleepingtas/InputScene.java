package com.example.sleepingtas;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class InputScene {
    public static void showScene(Stage window) {

        // Create main VBox layout
        VBox root1 = new VBox(30);
        root1.setAlignment(Pos.CENTER);
        root1.setPadding(new Insets(10));
        root1.setPrefSize(700, 500);


        // Title Text
        Text title = new Text("Sleeping TAs");
        title.setFont(new Font("Lucida Console", 40));


        // Number of TAs HBox
        HBox taBox = new HBox(10);
        taBox.setSpacing(10);
        taBox.setPadding(new Insets(15));
        taBox.setAlignment(Pos.CENTER);
        // taLabel
        Label taLabel = new Label("Number Of TAs     ");
        taLabel.setFont(new Font("Lucida Console", 20));
        // taChoiceBox
        ChoiceBox taChoiceBox = new ChoiceBox<>();
        taChoiceBox.setPrefWidth(150);
        taChoiceBox.getItems().addAll(1,2,3,4,5);
        taChoiceBox.setValue(1);
        // Gather all in taBox
        HBox.setHgrow(taChoiceBox, Priority.ALWAYS); // Allow ChoiceBox to grow
        taBox.getChildren().addAll(taLabel, taChoiceBox);

        // Number of Chairs HBox
        HBox chairBox = new HBox(10);
        chairBox.setSpacing(10);
        chairBox.setPadding(new Insets(15));
        chairBox.setAlignment(Pos.CENTER);
        // chairLabel
        Label chairLabel = new Label("Number Of Chairs  ");
        chairLabel.setFont(new Font("Lucida Console", 20));
        // chairChoiceBox
        ChoiceBox chairChoiceBox = new ChoiceBox<>();
        chairChoiceBox.setPrefWidth(150);
        chairChoiceBox.getItems().addAll(1,2,3,4,5);
        chairChoiceBox.setValue(1);
        // Gather all in chairBox
        HBox.setHgrow(chairChoiceBox, Priority.ALWAYS); // Allow ChoiceBox to grow
        chairBox.getChildren().addAll(chairLabel, chairChoiceBox);

        // Number of Students HBox
        HBox studentBox = new HBox(10);
        studentBox.setSpacing(10);
        studentBox.setPadding(new Insets(15));
        studentBox.setAlignment(Pos.CENTER);
        // studentBoxLabel
        Label studentLabel = new Label("Number Of Students");
        studentLabel.setFont(new Font("Lucida Console", 20));
        // studentTextField
        TextField studentTextField = new TextField();
        studentTextField.setPrefWidth(150);
        // HBox.setHgrow(studentTextField, Priority.ALWAYS); // Allow TextField to grow
        studentBox.getChildren().addAll(studentLabel, studentTextField);

        // Start Button HBox
        HBox buttonBox = new HBox(10);
        buttonBox.setSpacing(10);
        buttonBox.setPadding(new Insets(15));
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS); // Allow spacer to grow
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS); // Allow spacer to grow
        Button startButton = new Button("Start");
        startButton.setPrefSize(118, 27);
        buttonBox.getChildren().addAll(spacer1, startButton, spacer2);
        startButton.setOnAction(e -> startSimulation(taChoiceBox, chairChoiceBox, studentTextField, window));

        // Add all components to the VBox
        root1.getChildren().addAll(title, taBox, chairBox, studentBox, buttonBox);
        Scene scene1 = new Scene(root1);
        window.setScene(scene1);
        window.setTitle("Sleeping TAs Simulation");
        window.show();


    }

    private static void startSimulation(ChoiceBox<Integer> taChoiceBox, ChoiceBox<Integer> chairChoiceBox, TextField studentTextField , Stage window){
        try{
            int taNum = taChoiceBox.getSelectionModel().getSelectedItem(); // Selected Number Of TAs To Be Pass
            int chairNum = chairChoiceBox.getSelectionModel().getSelectedItem(); // Selected Number Of Chairs To Be Pass
            int studentNum = Integer.parseInt(studentTextField.getText());

            SimulationScene.showScene(window,chairNum, taNum, studentNum);

        }catch (NumberFormatException e){
            ErrorMassege.showErrorMessage("Invalid Number !!!");
            studentTextField.clear();
        }

    }


}
