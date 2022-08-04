package views;

import controllers.UsersViewController;
import enums.Dialog;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import models.Card;
import models.DB;
import models.Utility;

import java.io.IOException;

public class UserCellData {
    // TODO: 8/5/2022 business user 
    @FXML
    private GridPane gridPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Circle profilePic;
    @FXML
    private VBox customItem;
    private static UsersViewController controller = new UsersViewController();
    public UserCellData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            System.out.println(e);
//            throw new RuntimeException(e);
        }
    }
    public void setInfo(Card card)
    {
        name.setText( card.getName());
        id.setText("@" + card.getId());
        Button button = new Button();
        button.getStyleClass().clear();
        if (!DB.isFollowing(View.getLoggedInUser().getUserName(),card.getId())) {
            button.setText("follow");
            button.getStyleClass().add("button-follow");
        }
        else {
            button.setText("UnFollow");
            button.getStyleClass().add("button-unFollow");
        }
        button.setTextAlignment(TextAlignment.RIGHT);

        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog dialog = controller.handleFollowing(card.getId());
                if (dialog == Dialog.FOLLOWED) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button-unFollow");
                    button.setText("UnFollow");
                }
                if (dialog == Dialog.UNFOLLOWED) {
                    button.getStyleClass().clear();
                    button.getStyleClass().add("button-follow");
                    button.setText("follow");

                }
            }
        });
        profilePic.setFill(new ImagePattern(Utility.decodeImageFile(card.getImage())));

        customItem.getChildren().add(button );
    }

    public GridPane getContent()
    {
        return gridPane;
    }

}
