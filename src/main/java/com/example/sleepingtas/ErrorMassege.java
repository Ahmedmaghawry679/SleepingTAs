package com.example.sleepingtas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ErrorMassege{

    public static void showErrorMessage(String massege){
        Stage window = new Stage();
        window.setTitle("Error");
        window.initModality(Modality.APPLICATION_MODAL); // Block Input Events Or User Interaction With Other Windows

        Label label = new Label(massege);
        label.setTextFill(Color.WHITE);
        label.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setBackground(new Background(new BackgroundFill(Color.RED, CornerRadii.EMPTY, null)));

        Scene scene = new Scene(layout, 300, 200, Color.RED);
        window.setScene(scene);
        window.showAndWait(); // A Stage Must Closed Before Back To The Caller This Blocks Any Processing On Other Stages Until This Is Done

    }
}
