module com.example.hms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.hms to javafx.fxml;
    exports com.example.hms;
}