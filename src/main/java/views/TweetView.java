package views;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import models.User;

import java.net.URL;
import java.util.ResourceBundle;

public class TweetView implements Initializable {
    public ImageView profilePic;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: 8/2/2022 set profile pic
//        Image image
//        profilePic.setImage();
    }


    public void exitClicked(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }
}
