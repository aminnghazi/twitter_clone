package views;

import controllers.TweetViewController;
import enums.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.DB;
import models.Post;
import models.User;
import models.Utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TweetView implements Initializable {
    private static TweetViewController controller = new TweetViewController();
//    public ImageView profilePic;
    @FXML
    public GridPane gridPane;
    @FXML
    public Circle profileCircle;
    @FXML
    public Label error;
    @FXML
    public TextArea postText;
    @FXML
    private Button tweetBtn;
    @FXML
    private Button attachBtn;
    @FXML
    private HBox exitBtn;

    String parentID="-1";
    String postImage = "-1";

    public TweetView() {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/tweet-view.fxml"));
        loader.setController(this);
        try {
            gridPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        String profilePic = View.getLoggedInUser().getProfilePicture();
//        Image image = Utility.decodeImageFile(profilePic);
//        Image image = new Image(this.getClass().getResource("/pics/profile.png").toExternalForm());
//        ImagePattern imagePattern = new ImagePattern(image);
        gridPane.setPrefHeight(View.getScreenHeight()*3/5);
        gridPane.setPrefWidth(View.getScreenWidth()*3/5);
        profileCircle.setStyle("-fx-border-color: firebrick");
        profileCircle.setFill(new ImagePattern(Utility.decodeImageFile(View.getLoggedInUser().getProfilePicture())));

        attachBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                attachImage(event);
            }
        });
        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                exitClicked(event);
            }
        });
        tweetBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                tweetClicked(event);
            }
        });
        //        Image image
//        profilePic.setImage();
    }


    public void exitClicked(MouseEvent mouseEvent) {

        HBox pane = (HBox) mouseEvent.getSource();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void tweetClicked(MouseEvent mouseEvent) {
        Dialog dialog =controller.addNewPost(postText.getText(),postImage,parentID);
        if (dialog == Dialog.SUCCESS)
            View.showDialog("Post added successfully");
        else
            error.setText(dialog.toString());
    }



    public void attachImage(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png")
                ,new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
        postImage = Utility.encodeImageFile(file);
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public GridPane getContent() {
        return gridPane;
    }
}
