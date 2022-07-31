package views;

import controllers.LoginViewController;
import enums.Dialog;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginView {//controller of login-view.fxml
    LoginViewController controller = new LoginViewController();
    public PasswordField password;
    public TextField userName;

    public void loginClicked(MouseEvent mouseEvent) {
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
            System.out.println(dialog);
        }
    }

}
