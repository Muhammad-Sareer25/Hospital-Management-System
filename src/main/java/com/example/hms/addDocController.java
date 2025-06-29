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

public class addDocController {

    @FXML
    private TextField add_doc_adrs;

    @FXML
    private TextField add_doc_cont;

    @FXML
    private TextField add_doc_id;

    @FXML
    private TextField add_doc_name;

    @FXML
    private AnchorPane add_doc_scene;

    @FXML
    private TextField add_doc_sp;

    @FXML
    private Button bck_btn;

    @FXML
    private Button doc_sub_btn;






    @FXML
    private void backToMainDoctorForm(ActionEvent event) {
        try {
            // Load the previous FXML scene (main patient view)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("doc4.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Doctor Page");
            stage.show();

            // Close the current window (i.e., AddPatient form)
            ((Stage) ((Node) event.getSource()).getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void handleSubmitDoctor(ActionEvent event) {
        String docId = add_doc_id.getText();
        String docName = add_doc_name.getText();
        String specialization = add_doc_sp.getText();
        //String patId = add_doc_patId.getText();  // optional if no patient is assigned
        String contact = add_doc_cont.getText();
        String address = add_doc_adrs.getText();

        if (docId.isEmpty() || docName.isEmpty() || specialization.isEmpty() ||
                contact.isEmpty() || address.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields must be filled.");
            return;
        }

        try {
            long contactNumber = Long.parseLong(contact);

            Connection conn = DB_Connection.connDB();
            if (conn == null) {
                showAlert(Alert.AlertType.ERROR, "Database connection failed.");
                return;
            }

            String insertQuery = "INSERT INTO doctors (doc_id, doc_name, specialization,  doc_cont, doc_add) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(insertQuery);
            pstmt.setString(1, docId);
            pstmt.setString(2, docName);
            pstmt.setString(3, specialization);

            pstmt.setLong(4, contactNumber);
            pstmt.setString(5, address);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Doctor record added successfully.");
                clearDoctorFormFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to add doctor.");
            }

            conn.close();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Contact must be numeric.");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database error: " + e.getMessage());
        }
    }


    private void clearDoctorFormFields() {
        add_doc_id.clear();
        add_doc_name.clear();
        add_doc_sp.clear();
        add_doc_cont.clear();
        add_doc_adrs.clear();

    }
    private void showAlert(Alert.AlertType type, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle("Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
