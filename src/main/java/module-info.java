module com.example.hms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hms to javafx.fxml;
    exports com.example.hms;
}