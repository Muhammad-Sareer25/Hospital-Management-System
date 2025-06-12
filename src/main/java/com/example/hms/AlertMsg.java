//package com.example.hms;
//
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ButtonType;
//
//import java.util.Optional;
//
//public class AlertMsg {
//
//    private Alert alert;
//
//    public void errorMsg(String message){
//        alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Error Message");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//
//    }
//
//    public void  successMsg(String message){
//        alert = new  Alert(AlertType.INFORMATION);
//        alert.setTitle("Information Message");
//        alert.setContentText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//    public boolean confirmationMsg(String message){
//
//        alert = new  Alert(AlertType.CONFIRMATION);
//        alert.setTitle("Confirmation Message");
//        alert.setContentText(null);
//        alert.setContentText(message);
//        Optional<ButtonType> option = alert.showAndWait();
//
//        if(option.get().equals((ButtonType.OK) )){
//            return true;
//        }else{
//
//            return false;
//        }
//    }
//}
