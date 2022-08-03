package views;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import models.DB;
import models.Post;
import models.User;
import models.Utility;

import java.io.IOException;

public class PostCellData {
    @FXML
    private Label name;
    @FXML
    private ImageView postPic;
    @FXML
    private ImageView profilePic;
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
        User sender = DB.getUser(post.getSenderUsername());
        name.setText(sender.getFirstName() + " " + sender.getLastName());
        postText.setText(post.getText());
        postText.setMaxWidth(View.getScreenWidth()/3);
        userName.setText("@"+post.getSenderUsername());
        postPic.setImage(View.getBaseImage());
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
        postPic.fitWidthProperty().bind(pane.widthProperty().subtract(40));
        postPic.fitHeightProperty().bind(pane.heightProperty().subtract(40));
        profilePic.setImage(View.getBaseImage());
    }

    public GridPane getContent()
    {
        return gridPane;
    }
}
