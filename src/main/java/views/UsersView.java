package views;

import controllers.PostCell;
import controllers.USerCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.Card;
import models.Post;

public class UsersView extends View{
    public StackPane stackPane;
    public ListView listView;

    public StackPane getStackPane() {
        return stackPane;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void initialize(){
        fillUsers();
        listView.setCellFactory(new Callback<ListView<Card>, ListCell<Card>>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new USerCell();
            }
        });
    }

    private void fillUsers() {
    }

}
