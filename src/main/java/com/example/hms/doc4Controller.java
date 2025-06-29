package com.example.hms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class doc4Controller implements javafx.fxml.Initializable {


    @FXML
    private AnchorPane MAIN_base_doc;

    @FXML
    private TableView<Doctor> Doc_Table;

    @FXML
    private TableColumn<?, ?> Tab_Doc_cont;

    @FXML
    private TableColumn<?, ?> Tab_Doc_name;

    @FXML
    private TableColumn<?, ?> Tab_Doc_sp;

    @FXML
    private TableColumn<?, ?> Tab_Pat_id_in_Doc;

    @FXML
    private TableColumn<?, ?> Tab_doc_adrs;

    @FXML
    private TableColumn<?, ?> Tab_doc_id;

    @FXML
    private Button add_doc_btn;

    @FXML
    private TextField doc_search_bar;

    @FXML
    private Button doc_search_btn;

    @FXML
    private StackPane mainContentdoc;

    @FXML
    private AnchorPane rootPanedoc;

    @FXML
    private Button bak_btn_doc;


    public void initialize(URL url, ResourceBundle rb) {
        Tab_doc_id.setCellValueFactory(new PropertyValueFactory<>("docId"));
        Tab_Doc_name.setCellValueFactory(new PropertyValueFactory<>("docName"));
        Tab_Doc_sp.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        Tab_Pat_id_in_Doc.setCellValueFactory(new PropertyValueFactory<>("patId"));
        Tab_Doc_cont.setCellValueFactory(new PropertyValueFactory<>("docContact"));
        Tab_doc_adrs.setCellValueFactory(new PropertyValueFactory<>("docAddress"));

        Doc_Table.setItems(getDoctorList());
    }




    private ObservableList<Doctor> getDoctorList() {
        ObservableList<Doctor> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM doctors"; // adjust table name if different

        try (Connection conn = DB_Connection.connDB()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Doctor d = new Doctor(
                            rs.getString("doc_id"),
                            rs.getString("doc_name"),
                            rs.getString("specialization"),
                            rs.getString("pat_id"),
                            rs.getString("doc_cont"),
                            rs.getString("doc_add")
                    );
                    list.add(d);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }



    @FXML
    private void goback(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainForm2.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Dashboard");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Failed to load previous screen.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //search bar method
    @FXML
    private void handleDoctorSearch(ActionEvent event) {
        String docId = doc_search_bar.getText().trim();

        if (docId.isEmpty()) {
            showAlertd(Alert.AlertType.WARNING, "Please enter a Doctor ID to search.");
            return;
        }

        String query = "SELECT * FROM Doctors WHERE doc_id = ?";

        try (Connection conn = DB_Connection.connDB();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, docId);
            var rs = pstmt.executeQuery();

            if (rs.next()) {
                // Format patient info
                String fullName = rs.getString("doc_name");
                String info = """
                    🆔 Doctor ID:              %s
                    📞 Contact:                %s
                    🔬 Specialization:         %s
                    🏠 Address:                %s
                    """.formatted(
                        rs.getString("doc_id"),
                        rs.getString("doc_cont"),
                        rs.getString("specialization"),
                        rs.getString("doc_add")
                );

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Doctor Details");
                alert.setHeaderText("👤 " + fullName);
                alert.setContentText(info);

                // Optional: allow resizing if text is long
                alert.getDialogPane().setPrefWidth(400);
                alert.getDialogPane().setPrefHeight(250);
                alert.setResizable(true);

                alert.showAndWait();

            } else {
                showAlertd(Alert.AlertType.INFORMATION, "No Doctor found with ID: " + docId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlertd(Alert.AlertType.ERROR, "Error: " + e.getMessage());
        }
    }

    private void showAlertd(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void add_doc_formOPEN(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addDoc.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("New Form");
            stage.show();

            // Optional: hide current window if needed
            ((Stage)((Node)event.getSource()).getScene().getWindow()).hide();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}