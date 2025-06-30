
package com.example.hms;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class appot5Contoller implements Initializable {

    @FXML
    private TableView<Appointment> Appt_Table;

    @FXML
    private Label LoggedPerson;

    @FXML
    private AnchorPane MAIN_base_app;

    @FXML
    private TableColumn<?, ?> Tab_app_id;

    @FXML
    private TableColumn<?, ?> Tab_app_name;

    @FXML
    private TableColumn<?, ?> Tab_doc_id_in_app;

    @FXML
    private TableColumn<?, ?> Tab_pat_id_in_app;

    @FXML
    private Button add_apt_btn;

    @FXML
    private TextField apt_search_bar;

    @FXML
    private Button apt_search_btn;

    @FXML
    private Button bak_btn_apt;



    private ObservableList<Appointment> getAppointmentList() {
        ObservableList<Appointment> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM appointments"; // adjust if your table name is different

        try (Connection conn = DB_Connection.connDB()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Appointment a = new Appointment(
                            rs.getInt("app_id"),
                            rs.getString("app_date"),
                            rs.getString("pat_id"),
                            rs.getString("doc_id")
                    );
                    list.add(a);
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


    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        Tab_app_id.setCellValueFactory(new PropertyValueFactory<>("appId"));
        Tab_app_name.setCellValueFactory(new PropertyValueFactory<>("appDate"));
        Tab_pat_id_in_app.setCellValueFactory(new PropertyValueFactory<>("patId"));
        Tab_doc_id_in_app.setCellValueFactory(new PropertyValueFactory<>("docId"));

        Appt_Table.setItems(getAppointmentList());
    }



}