package views;

import controllers.LoginPageController;
import enums.Dialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import models.DB;

import java.io.IOException;

public class LoginView {//controller of login-view.fxml
    public static LoginPageController controller = new LoginPageController();
    public PasswordField password;
    public TextField userName;
    public Label error;
    public Button themeBtn;
    boolean testMode = true;

    public void loginClicked(MouseEvent mouseEvent) {
        if (testMode) {
            View.setLoggedInUser(DB.getUser("ali"));
            HomePage homePage = new HomePage();
            try {
                homePage.start(View.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
//        View.showDialog("salam");
        String userName = this.userName.getText();
        String password = this.password.getText();
        Dialog dialog = controller.verifyLogin(userName, password);
        if (dialog == Dialog.SUCCESS){
            HomePage homePage = new HomePage();
            try {
                homePage.start(View.getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            error.setText(dialog.toString());
//            System.out.println(dialog);
        }
    }

    public void signUp(MouseEvent mouseEvent) {
        try {
            GridPane signUpView = FXMLLoader.load(this.getClass().getResource("/fxml/sign-up-view.fxml"));
            javafx.scene.control.Dialog dialog = new javafx.scene.control.Dialog();
            dialog.getDialogPane().setContent(signUpView);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeTheme(MouseEvent event) {
//        themeBtn.getStyleClass().clear();
        if (View.theme == 1) {
            themeBtn.setStyle("-fx-background-color: rgb(92,92,92);");
            View.theme = 0;
            return;
        }
        if (View.theme == 0) {
            themeBtn.setStyle("-fx-background-color: white;");
            View.theme = 1;
            return;
        }
    }
}
