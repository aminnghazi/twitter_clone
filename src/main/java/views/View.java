package views;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

public abstract class View extends Application {
    private static User loggedInUser = null;

    public static void setLoggedInUser(User loggedInUser) {
        View.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return View.loggedInUser;
    }


    //this class has no controller for now.Currently, it only opens other pages
}
