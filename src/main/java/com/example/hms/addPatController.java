package com.example.hms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

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
}
