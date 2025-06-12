package com.example.hms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private AnchorPane login_form;

    @FXML
    private CheckBox login_form_checkbox;

    @FXML
    private Hyperlink login_form_createacc;

    @FXML
    private Button login_form_login;

    @FXML
    private PasswordField login_form_password;

    @FXML
    private ComboBox<?> login_form_selectuser;

    @FXML
    private TextField login_form_showpassword;

    @FXML
    private TextField login_form_user;

    @FXML
    private AnchorPane main_form;

    @FXML
    private CheckBox new_acc_chekbox;

    @FXML
    private TextField new_acc_email;

    @FXML
    private AnchorPane new_acc_form;

    @FXML
    private Hyperlink new_acc_loginhere;

    @FXML
    private PasswordField new_acc_password;

    @FXML
    private TextField new_acc_showpassword;

    @FXML
    private Button new_acc_signup;

    @FXML
    private TextField new_acc_user;


////  database tools
//    private Connection connect;
//    private PreparedStatement prepare;
//    private ResultSet result;
//
//
//
//    private AlertMsg alert = new AlertMsg();   //AlertMsg class.
//
//    public void CreNewAcc(){
//
//        if (new_acc_email.getText().isEmpty()
//                || new_acc_user.getText().isEmpty()
//                || new_acc_password.getText().isEmpty()) {
//            // Alret msgs
//
//            alert.errorMsg("Please fill in all fields before proceeding");
//        }
//        else{
//           //Whether the user name already exits or not
//            String checkUsername = "SELECT * FROM admin WHERE username = '" + new_acc_user.getText() + "'";
//
//            connect = Database.connDB();
//
//            try{
//
//                prepare = connect.prepareStatement(checkUsername);
//                result = prepare.executeQuery();
//
//                if (result.next()){
//
//                    alert.errorMsg(new_acc_user.getText() + "is already exist!");
//
//                }else {
//
//                    String insertData = "INSERT INTO admin {email, username, password} VALUES{?,?,?}";
//
//
//                }
//            } catch (Exception e) {
//              e.printStackTrace();
//            }
//
//
//        }
//    }


@FXML
private void handleSignUp(ActionEvent event) {
    String email = new_acc_email.getText();
    String username = new_acc_user.getText();
    String password = new_acc_password.getText();

    if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields.");
        return;
    }

    String checkUserSql = "SELECT * FROM admin WHERE email = ? OR username = ?";
    String insertSql = "INSERT INTO admin (email, username, password) VALUES (?, ?, ?)";

    try (Connection conn = DB_Connection.connDB();
         PreparedStatement checkStmt = conn.prepareStatement(checkUserSql)) {

        checkStmt.setString(1, email);
        checkStmt.setString(2, username);
        ResultSet rs = checkStmt.executeQuery();

        if (rs.next()) {
            // User already exists
            showAlert(Alert.AlertType.WARNING, "User Exists", "User already exists. Please go to login page.");
            return;
        }

        try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
            insertStmt.setString(1, email);
            insertStmt.setString(2, username);
            insertStmt.setString(3, password); // Consider hashing passwords in real apps

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Account Created", "User account created successfully.");
                clearSignupFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed", "Could not create account.");
            }
        }

    } catch (Exception e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while connecting to the database.");
    }
}

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    private void clearSignupFields() {
        new_acc_email.clear();
        new_acc_user.clear();
        new_acc_password.clear();
        new_acc_showpassword.clear(); // This should be a valid TextField too
    }


    @FXML
    private void handleLogin(ActionEvent event) {
        String user = login_form_user.getText();
        String pass = login_form_password.getText();

        if (user.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter both username and password.");
            return;
        }

        String sql = "SELECT * FROM admin WHERE (username = ? OR email = ?) AND password = ?";

        try (Connection conn = DB_Connection.connDB();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user);
            stmt.setString(2, user); // can be email or username
            stmt.setString(3, pass); // note: hash password in production

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Successful login
                SessionData.setLoggedInAdmin(user);// logged user data saved  this is for admin data to be displayed that which admin is logged

                // - load new scene
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminMainForm2.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Dashboard");
                stage.show();

                // Close login window
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Incorrect username/email or password.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login.");
        }
    }

    public void switchForm(ActionEvent event){

        if(event.getSource() == login_form_createacc ){
//new acc form show
            login_form.setVisible(false);
            new_acc_form.setVisible(true);
        }else if (event.getSource() == new_acc_loginhere){
//login form show
            login_form.setVisible(true);
            new_acc_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
