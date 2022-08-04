package views;

import controllers.UserCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Card;
import models.DB;
import models.Post;
import models.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UsersView implements Initializable {
    @FXML
    private StackPane stackPane;
    @FXML
    public ListView listView;
    ObservableList<Card> cards ;


    public UsersView() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/users-view.fxml"));
        fxmlLoader.setController(this);
        try
        {
            stackPane = (StackPane) fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUsers();
        listView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell call(ListView<Card> param) {
                return new UserCell();
            }
        });
    }
    private void fillUsers() {
        ArrayList<User> users = View.getLoggedInUser().getSuggestedUsers();
        ArrayList<Card> userCards = new ArrayList<>();
        for (User user : users) {
            if (!user.getUserName().equals(View.getLoggedInUser().getUserName()))
                userCards.add(new Card(user.getFirstName()+" " +user.getLastName()
                    ,user.getUserName(),user.getProfilePicture()));
        }
        cards = FXCollections.observableArrayList();
        cards.setAll(userCards);
        listView.setItems(cards);
    }
    public StackPane getStackPane() {
        return stackPane;
    }

}
