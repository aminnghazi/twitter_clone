package views;

import controllers.ChatViewController;
import controllers.MessageCell;
import controllers.UserCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatView implements Initializable {
    @FXML
    public Label name;
    @FXML
    public Label id;
    @FXML
    public Button image;
    @FXML
    public Button send;
    @FXML
    public GridPane gridPane;
    @FXML
    private TextField text;
    @FXML
    private ListView listView;
    @FXML
    private Circle profile;

    ObservableList<Message> messages ;
    String recipient;
    // TODO: 8/6/2022 add saved message

    private ChatViewController controller = new ChatViewController();

    public ChatView(String recipient) {
        this.recipient = recipient;
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/chat-view.fxml"));
        loader.setController(this);
        try {
            gridPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        listView.getStylesheets().clear();
        listView.getStylesheets().add(View.getStyleClass_List());
        //setting up list view
        fillMessages();
        listView.setCellFactory(new Callback<ListView<Message>, ListCell<Message>>() {
            @Override
            public ListCell<Message> call(ListView<Message> param) {
                return new MessageCell();
            }
        });
        //Showing chat info
        User user;
        if (recipient.equals("-1")){
            user = View.getLoggedInUser();
            profile.setFill(new ImagePattern(Utility.decodeImageFile("-1")));
        }else {
            user = DB.getUser(recipient);
            if (user != null)
                profile.setFill(new ImagePattern(Utility.decodeImageFile(user.getProfilePicture())));
            else
                profile.setFill(new ImagePattern(Utility.decodeImageFile(DB.getGroupImage(recipient))));
        }
        if (user != null) {
            name.setText(user.getFirstName() + " " + user.getLastName());
            id.setText(user.getUserName());
        }else {
            name.setText(DB.getGroupName(recipient));
            id.setText(recipient);
        }
        send.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                send();
            }
        });
        gridPane.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                if (ke.getCode().equals(KeyCode.ENTER)) {
                    send();
                }
            }
        });
    }

    private void fillMessages() {
        messages = FXCollections.observableArrayList();

//        System.out.println(id);
        messages.setAll(controller.getMessages(recipient,View.getLoggedInUser().getUserName()));
        listView.setItems(messages);
    }


    public void galleryButtonClicked(MouseEvent mouseEvent) {
    }

    public void sendClicked(MouseEvent mouseEvent) {
        send();
    }


    private void send() {
        String messageText = text.getText();
        text.clear();
        controller.sendMessage(View.getLoggedInUser().getUserName(),
                recipient,messageText,"-1");
        fillMessages();
    }
    public GridPane getGridPane() {
        return gridPane;
    }

}
