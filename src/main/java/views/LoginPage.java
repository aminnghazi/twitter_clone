package views;

import controllers.LoginPageController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPage extends View{
    private static LoginPageController controller = new LoginPageController();
    public BorderPane loginPage;
    public GridPane content;


    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.centerOnScreen();
        BorderPane borderPane = FXMLLoader.load(this.getClass().getResource("/fxml/login-page.fxml"));
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
    }

    public void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/loginView.fxml"));
        GridPane gridPane = loader.load();//loading login view
        content.add(gridPane,1,1);

    }
}
