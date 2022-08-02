package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import models.DB;
import models.Post;

import java.net.URL;
import java.util.ResourceBundle;

public class PostsView implements Initializable {

    public ListView<Post> listView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Post> posts = FXCollections.observableArrayList(DB.getAllPosts());
        listView.setItems(posts);
        listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new PostCell();
            }
        });
        listView.setOrientation(Orientation.HORIZONTAL);
    }
}
