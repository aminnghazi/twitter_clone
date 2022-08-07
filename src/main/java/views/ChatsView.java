package views;

import controllers.ChatCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import models.Card;
import models.DB;
import models.Post;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatsView implements Initializable {

    @FXML
    private ListView listView;
    @FXML
    private StackPane stackPane;
    @FXML
    private ImageView addImage;

    private VBox middleContainer;

    ObservableList<Card> cards ;
    private String recipient = "-1";


    public ChatsView(VBox middleContainer) {
        this.middleContainer = middleContainer;
        try {
            FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/chats-view.fxml"));
            loader.setController(this);
            stackPane = (StackPane) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillChatsListView();
        loadChatView();
        listView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell call(ListView<Card> param) {
                return new ChatCell();
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Card card = (Card) listView.getSelectionModel().getSelectedItem();
                if (card.getName() == null)
                    recipient = "-1";
                else
                    recipient = findChatId(card.getId());
                loadChatView();
            }
        });
        addImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createGroup();
            }
        });
    }

    private void createGroup() {
        CreateChatView controller = new CreateChatView();
        Dialog dialog = new Dialog();
        dialog.getDialogPane().setContent(controller.getContent());
        dialog.initStyle(StageStyle.TRANSPARENT);
        dialog.show();
    }


    private String  findChatId(String input){
        if (!input.contains("&"))
            return input;//group id
        else {
            String[] result = input.split("\\&");
            if (result[0].equals(View.getLoggedInUser().getUserName()))
                return result[1];
            else
                return result[0];
        }
    }
    private void fillChatsListView() {
        ArrayList<String> groupChats = DB.getGroups(View.getLoggedInUser().getUserName());
        ArrayList<String> pvChats = DB.getFollowings(View.getLoggedInUser().getUserName());// TODO: 8/6/2022 replace
        ArrayList<Card> chatCards = new ArrayList<>();
        User currentUser = View.getLoggedInUser();
        //adding saved messages
        chatCards.add(new Card(currentUser.getFirstName()+" " + currentUser.getLastName(),
                currentUser.getUserName()+"&-1", currentUser.getProfilePicture()));
        //adding group chats
        for (String groupChat : groupChats) {
            chatCards.add(new Card(DB.getGroupName(groupChat),groupChat,DB.getGroupImage(groupChat)));
        }
        //adding pv chats
        for (String pvChat : pvChats) {//adding pv chats to list view
            User user = DB.getUser(pvChat);
            chatCards.add(new Card(user.getFirstName()+" " + user.getLastName()
            ,View.getLoggedInUser().getUserName() + "&" +user.getUserName(),
                    user.getProfilePicture()));
        }
        cards = FXCollections.observableArrayList();
        cards.setAll(chatCards);
        listView.setItems(cards);
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public StackPane getStackPane() {
        return stackPane;
    }
    public void loadChatView() {
        ChatView controller = new ChatView(recipient);
        middleContainer.getChildren().clear();
        middleContainer.getChildren().add(controller.getGridPane());
    }

}
