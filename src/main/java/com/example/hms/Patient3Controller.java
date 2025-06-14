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

public class Patient3Controller  implements javafx.fxml.Initializable {

    @FXML
    private Label LoggedPerson;

    @FXML
    private AnchorPane MAIN_base_patient;

    @FXML
    private TableView<Patient> Pat_Table;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Add;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Age;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Con;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Fname;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Gen;

    @FXML
    private TableColumn<?, ?> Tab_Pat_Lname;

    @FXML
    private TableColumn<?, ?> Tab_Pat_id;

    @FXML
    private Button add_pat_btn;

    @FXML
    private StackPane mainContent;

    @FXML
    private AnchorPane rootPane;


    public void initialize(URL url, ResourceBundle rb) {
        Tab_Pat_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        Tab_Pat_Fname.setCellValueFactory(new PropertyValueFactory<>("fname"));
        Tab_Pat_Lname.setCellValueFactory(new PropertyValueFactory<>("lname"));
        Tab_Pat_Con.setCellValueFactory(new PropertyValueFactory<>("contact"));
        Tab_Pat_Gen.setCellValueFactory(new PropertyValueFactory<>("gender"));
        Tab_Pat_Age.setCellValueFactory(new PropertyValueFactory<>("age"));
        Tab_Pat_Add.setCellValueFactory(new PropertyValueFactory<>("address"));

        Pat_Table.setItems(getPatientList());
    }

    private ObservableList<Patient> getPatientList() {
        ObservableList<Patient> list = FXCollections.observableArrayList();
        String query = "SELECT * FROM patients";

        try (Connection conn = DB_Connection.connDB()) {
            assert conn != null;
            try (PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {

                    //System.out.println("Fetched ID: " + rs.getString("pat_id"));//This will print each pat_id as it’s read from the database.

                    Patient p = new Patient(
                            rs.getString("pat_id"),
                            rs.getString("First_name"),
                            rs.getString("Last_name"),
                            rs.getLong("pat_Contact"), // long b/c int can't retrive that much long value
                            rs.getString("gender"),
                            rs.getInt("age"),
                            rs.getString("address")
                    );
                    list.add(p);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @FXML
    private void add_pat_formOPEN(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addPat.fxml"));
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