package views;

import controllers.PostCellController;
import enums.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.StageStyle;
import models.DB;
import models.Post;
import models.User;
import models.Utility;

import java.io.IOException;

public class PostCellData {
    private static PostCellController controller = new PostCellController();
    @FXML
    private Label name;
    @FXML
    private ImageView profilePic;
    @FXML
    private ImageView like;
    @FXML
    private ImageView comment;
    @FXML
    private Label userName;
    @FXML
    private GridPane gridPane;
    @FXML
    private Label postText;
    @FXML
    private Pane pane;
    @FXML
    private VBox vbox;
    @FXML
    private Label replyingTo;
    @FXML
    private Hyperlink replyID;
    @FXML
    private Rectangle imageRec;

    public PostCellData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/post.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Post post)
    {
        setListeners(post);

        User sender = DB.getUser(post.getSenderUsername());

        name.setText(sender.getFirstName() + " " + sender.getLastName());
        if (post.isAd())
            postText.setText("(ad)" + post.getText());
        else
            postText.setText(post.getText());

        postText.setMaxWidth(View.getScreenWidth()/3);

        userName.setText("@"+post.getSenderUsername());

        gridPane.setPrefHeight(View.getScreenHeight()/5 + postText.getHeight() + imageRec.getHeight());

        profilePic.setImage(Utility.decodeImageFile(DB.getUser(post.getSenderUsername()).getProfilePicture()));

        if (DB.isLikedBy(View.getLoggedInUser().getUserName(),post.getID()))
            like.setImage(new Image(getClass().getResource("/pics/liked.png").toExternalForm()));




    }

    private void setListeners(Post post) {
        like.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog dialog = controller.handleLikingPost(post.getID());
                if (dialog == Dialog.MESSAGE_LIKE_REMOVED)
                    like.setImage(new Image(getClass().getResource("/pics/notLiked.png").toExternalForm()));
                else if (dialog == Dialog.MESSAGE_LIKED)
                    like.setImage(new Image(getClass().getResource("/pics/liked.png").toExternalForm()));
            }
        });
        comment.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                addComment(post);
            }
        });
        if (!post.getImage().equals("-1")) {
            Image postImage = Utility.decodeImageFile(post.getImage());
            imageRec.setFill(new ImagePattern(postImage));
            setImageRecSize(postImage.getWidth(), postImage.getHeight(), imageRec, 0.4);
        }

        if (!post.getParentID().equals("-1")){
            replyingTo.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    System.out.println("Go to profile");// TODO: 8/3/2022 add feature
                }
            });
            replyingTo.setText("Replying to ");
            String parentUserName = DB.getUser(DB.getPost(post.getParentID()).getSenderUsername()).getUserName();
            replyID.setText("@"+parentUserName);
        }
    }

    private void addComment(Post post) {
            TweetView controller = new TweetView();
            controller.setParentID(post.getID());
            javafx.scene.control.Dialog dialog = new javafx.scene.control.Dialog();
            dialog.getDialogPane().setContent(controller.getContent());
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.show();
    }

    private void setImageRecSize(double imageWidth, double imageHeight, Rectangle rectangle,double widthPortion){

    imageRec.setArcHeight(30);imageRec.setArcWidth(30);
    imageRec.setEffect(new DropShadow(1, Color.GRAY));  // Shadow

    double scale = widthPortion * View.getScreenWidth()/imageWidth ;
        imageRec.setWidth(imageWidth*scale);
        imageRec.setHeight(imageHeight*scale);

}
    public GridPane getContent()
    {
        return gridPane;
    }
}
