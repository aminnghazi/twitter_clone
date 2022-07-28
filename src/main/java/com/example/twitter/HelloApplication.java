package com.example.twitter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.LoginPage;
import views.View;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Hello!");
        View.setStage(stage);
        LoginPage loginPage = new LoginPage();
        loginPage.start(View.getStage());
        stage.setTitle("Twitter");
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}