package com.example.hms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class addPatController {


    @FXML
    private Button Back_btn;

    @FXML
    private TextField add_pat_address;

    @FXML
    private TextField add_pat_age;

    @FXML
    private TextField add_pat_cont;

    @FXML
    private TextField add_pat_fname;

    @FXML
    private TextField add_pat_gend;

    @FXML
    private TextField add_pat_id;

    @FXML
    private TextField add_pat_lname;

    @FXML
    private AnchorPane add_pat_scene;

    @FXML
    private Button pat_sub_button;


    @FXML
    private void backToMainPatientForm(ActionEvent event) {
        try {
            // Load the previous FXML scene (main patient view)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Patient3.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Patient Management");
            stage.show();

            // Close the current window (i.e., AddPatient form)
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method for submit button
    @FXML
    private void handleSubmitPatient(ActionEvent event) {
        String patId = add_pat_id.getText();
        String fname = add_pat_fname.getText();
        String lname = add_pat_lname.getText();
        String contact = add_pat_cont.getText();
        String gender = add_pat_gend.getText();
        String ageText = add_pat_age.getText();
        String address = add_pat_address.getText();

        if (patId.isEmpty() || fname.isEmpty() || lname.isEmpty() || contact.isEmpty() ||
                gender.isEmpty() || ageText.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled.");
            return;
        }

        try {
            long contactNumber = Long.parseLong(contact);
            int age = Integer.parseInt(ageText);

            Connection conn = DB_Connection.connDB();
            if (conn == null) {
                showAlert(Alert.AlertType.ERROR, "Database connection failed.");
                return;
            }

            String insertQuery = "INSERT INTO Patients (pat_id, First_name, Last_name, pat_Contact, gender, age, address) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, patId);
            pstmt.setString(2, fname);
            pstmt.setString(3, lname);
            pstmt.setLong(4, contactNumber);
            pstmt.setString(5, gender);
            pstmt.setInt(6, age);
            pstmt.setString(7, address);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Patient record added successfully.");
                clearFormFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to add patient.");
            }

            conn.close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Contact must be numeric. Age must be an integer.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }

    private void clearFormFields() {
        add_pat_id.clear();
        add_pat_fname.clear();
        add_pat_lname.clear();
        add_pat_cont.clear();
        add_pat_gend.clear();
        add_pat_age.clear();
        add_pat_address.clear();
    }

    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
