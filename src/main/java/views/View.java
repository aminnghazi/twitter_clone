package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.stage.Stage;
import models.User;

import java.io.IOException;
import java.util.Optional;

public abstract class View extends Application {
    private static User loggedInUser = null;

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        View.stage = stage;
    }

    protected static Stage stage;
    public static void setLoggedInUser(User loggedInUser) {
        View.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return View.loggedInUser;
    }
    protected static void showDialog(String text){
        try {
            DialogPane dialogPane = FXMLLoader.load(View.class.getResource("/fxml/dialog-box.fxml"));
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            dialog.setDialogPane(dialogPane);
            Dialogs controller = new Dialogs();
            controller.setDialog(text);
            dialog.showAndWait();
//            Optional<ButtonType> clickedButton = dialog.showAndWait();

        }catch (IOException ioException){
            System.out.println("dialog can not be shown");
        }
    }

    //this class has no controller for now.Currently, it only opens other pages
}
