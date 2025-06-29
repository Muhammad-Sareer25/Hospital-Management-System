package com.example.hms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdMnFm2Controller {
    @FXML
    private Label AdminNameLabel;

    @FXML
    private Label LoggedPerson;

    @FXML
    private Label adminIdLabel;

    @FXML
    private Button Admin_Pat_btn;

    @FXML
    private Button Admin_doc_btn;
    @FXML
    public void initialize() {
        String username = SessionData.getLoggedInAdmin();

        if (username != null) {
            try (Connection conn = DB_Connection.connDB();
                 PreparedStatement stmt = conn.prepareStatement("SELECT admin_id, username FROM admin WHERE username = ?")) {

                stmt.setString(1, username);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    int id = rs.getInt("admin_id");
                    String name = rs.getString("username");

                    adminIdLabel.setText("" + id);
                    AdminNameLabel.setText("Welcome, " + name);
                    LoggedPerson.setText("Logged in as: " + name);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void handlePatientButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Patient3.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Patient Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleDoctorButton(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("doc4.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow(); // get current stage
            stage.setScene(new Scene(root));
            stage.setTitle("Doctor Page");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
