package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import models.Card;
import models.DB;
import models.Message;
import models.Utility;

import java.io.IOException;
import java.util.ArrayList;

public class ChatCellData {
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
    public ChatCellData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/user.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            System.out.println(e);
        }
    }

    public void setInfo(Card card) {
        name.setText( card.getName());
        id.setText("@" + card.getId());
        profilePic.setFill(new ImagePattern(Utility.decodeImageFile(card.getImage())));
        Label label = new Label();
        label.getStyleClass().add("label-normal");
        // TODO: 8/5/2022 not efficient
        ArrayList<Message> messages = DB.getMessages(card.getId());
        if (messages.size() > 0)
            label.setText(messages.get(messages.size()-1).getText());
        else
            label.setText("send your first message!");
        customItem.getChildren().add(label);
    }

    public Node getContent() {
        return gridPane;
    }
}
