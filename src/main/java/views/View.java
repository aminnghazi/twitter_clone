package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.User;
import java.io.IOException;

public abstract class View extends Application {
    private static User loggedInUser = null;
    private static double screenWidth=1920;
    private static double screenHeight=1080;

    public static int theme=1;

    public static double getScreenWidth() {
        return screenWidth;
    }

    public static void setScreenWidth(double screenWidth) {
        View.screenWidth = screenWidth;
    }

    public static double getScreenHeight() {
        return screenHeight;
    }

    public static void setScreenHeight(double screenHeight) {
        View.screenHeight = screenHeight;
    }

    private static Image baseImage = new Image(View.class.getResource("/pics/profile.jpg").toString());

    public static Image getBaseImage() {
        return baseImage;
    }

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
            FXMLLoader loader = new FXMLLoader(View.class.getResource("/fxml/dialog-box.fxml"));
            DialogPane dialogPane = loader.load();
            Dialogs controller = loader.getController();
            javafx.scene.control.Dialog<ButtonType> dialog = new javafx.scene.control.Dialog<>();
            controller.setDialog(text);
            dialog.setDialogPane(dialogPane);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();
//            Optional<ButtonType> clickedButton = dialog.showAndWait();

        }catch (IOException ioException){
            System.out.println("dialog can not be shown");
        }
    }

}
