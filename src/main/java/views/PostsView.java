package views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.DB;
import models.Post;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PostsView  implements Initializable{

    @FXML
    private  ListView listView;
    @FXML
    private StackPane stackPane;

    ObservableList<Post> posts;

    public PostsView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/posts-view.fxml"));
        fxmlLoader.setController(this);
        try
        {
            StackPane stackPane = (StackPane) fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);

        }
    }


    public StackPane getStackPane() {
        return stackPane;
    }

    public void setStackPane(StackPane stackPane) {
        this.stackPane = stackPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        posts = FXCollections.observableArrayList();
        posts.setAll(DB.getAllPosts());
        listView.setItems(posts);
        listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new PostCell();
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                postClicked((Post) listView.getSelectionModel().getSelectedItem());
            }
        });

    }
    private void postClicked(Post post){
        posts.setAll(DB.getComments(post.getID()));
        listView.setItems(posts);
        listView.getItems().add(post);


    }
}
