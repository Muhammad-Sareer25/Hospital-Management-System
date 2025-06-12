package com.example.hms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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




}
