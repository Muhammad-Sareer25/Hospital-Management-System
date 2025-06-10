package com.example.hms;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
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
