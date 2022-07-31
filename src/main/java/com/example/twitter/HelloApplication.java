package com.example.twitter;

import javafx.scene.image.Image;
import models.DB;
import views.LoginPage;
import views.View;
import javafx.application.Application;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        System.out.println(DB.start());
        View.setStage(stage);
        stage.setTitle("Hello!");
        stage.setTitle("Twitter");
        stage.setFullScreen(true);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/pics/icon.png")));
        stage.show();
        LoginPage loginPage = new LoginPage();
        loginPage.start(stage);
    }

    public static void main(String[] args) {
        launch();
    }
}