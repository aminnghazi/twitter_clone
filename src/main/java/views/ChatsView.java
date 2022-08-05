package views;

import controllers.ChatCell;
import controllers.UserCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Card;
import models.DB;
import models.User;

import java.util.ArrayList;

public class ChatsView extends View{
    public ListView listView;
    ObservableList<Card> cards ;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void initialize(){
        fillChatsListView();
        listView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell call(ListView<Card> param) {
                return new ChatCell();
            }
        });
    }

    private void fillChatsListView() {
//        ArrayList<String> groupChats = DB.getGroups(View.getLoggedInUser().getUserName());
        ArrayList<String> pvChats = DB.getFollowings(View.getLoggedInUser().getUserName());// TODO: 8/6/2022 replace 
        ArrayList<Card> chatCards = new ArrayList<>();

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
}
