module com.example.sleepingtas {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.sleepingtas to javafx.fxml;
    exports com.example.sleepingtas;
}