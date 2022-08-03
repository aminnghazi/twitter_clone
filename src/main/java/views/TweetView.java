package views;

import controllers.TweetViewController;
import enums.Dialog;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
import java.net.URL;
import java.util.ResourceBundle;

public class TweetView implements Initializable {
    private static TweetViewController controller = new TweetViewController();
//    public ImageView profilePic;
    public GridPane gridPane;
    public Circle profileCircle;
    public Label error;
    public TextArea postText;
    String parentID="-1";
    Image postImage;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        String profilePic = View.getLoggedInUser().getProfilePicture();
//        Image image = Utility.decodeImageFile(profilePic);
        Image image = new Image(this.getClass().getResource("/pics/profile.png").toExternalForm());
        ImagePattern imagePattern = new ImagePattern(image);
        profileCircle.setStyle("-fx-border-color: firebrick");
        profileCircle.setFill(imagePattern);
        //        Image image
//        profilePic.setImage();
    }


    public void exitClicked(MouseEvent mouseEvent) {
        gridPane.setPrefHeight(View.getScreenHeight()*3/5);
        gridPane.setPrefWidth(View.getScreenWidth()*3/5);
        Pane pane = (Pane) mouseEvent.getSource();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void tweetClicked(MouseEvent mouseEvent) {
        Dialog dialog =controller.addNewPost(postText.getText(),postImage);
        if (dialog == Dialog.SUCCESS)
            View.showDialog("Post added successfully");
        else
            error.setText(dialog.toString());
    }

    public Dialog handleAddComment(String text, Post parentPost) {
        boolean isAd;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;
        Post comment = new Post(View.getLoggedInUser().getUserName(), null, text, parentPost.getID(),isAd);
        DB.addPost(comment);
        return Dialog.COMMENT_ADDED_SUCCESSFULLY;
    }

    public void attachImage(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png")
                ,new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}
