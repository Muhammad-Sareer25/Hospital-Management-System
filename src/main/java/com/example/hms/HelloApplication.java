package com.example.hms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());






        stage.setTitle("Hospital Management System");
        stage.setScene(scene);
  // stage.setFullScreen(true);  // full screen view
        stage.show();
    }

    public static void main(String[] args) {

        launch();
    }
}