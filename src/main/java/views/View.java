package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

import java.util.Scanner;

public abstract class View extends Application {
    public static Stage stage;
    private static User loggedInUser = null;

    public static void setStage(Stage stage) {
        View.stage = stage;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setScene(Scene scene){
        stage.setScene(scene);
    }
    public static void setLoggedInUser(User loggedInUser) {
        View.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return View.loggedInUser;
    }


    //this class has no controller for now.Currently, it only opens other pages
}
