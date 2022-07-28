package views;

import controllers.LoginPageController;
import enums.Dialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginPage extends View{
    private static LoginPageController controller = new LoginPageController();



    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = FXMLLoader.load(this.getClass().getResource("/fxml/login-page.fxml"));
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);


    }
}
