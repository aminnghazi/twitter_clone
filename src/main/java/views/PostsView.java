package views;

import controllers.PostCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import models.DB;
import models.Post;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PostsView  implements Initializable{
    // TODO: 8/4/2022 comment button not functional
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
             stackPane = (StackPane) fxmlLoader.load();
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
        listView.getStylesheets().clear();
        listView.getStylesheets().add(View.getStyleClass_List());
        stackPane.getStylesheets().clear();
        stackPane.getStylesheets().add(View.getStyleClass());
        fillPosts();
        listView.setCellFactory(new Callback<ListView<Post>, ListCell<Post>>() {
            @Override
            public ListCell<Post> call(ListView<Post> param) {
                return new PostCell();
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                    if(mouseEvent.getClickCount() == 2){
                        Post post = (Post) listView.getSelectionModel().getSelectedItem();
                        if (post != null)
                            postClicked(post);
                    }
                }
            }
        });
//        stackPane.addEventFilter(MouseEvent.ANY, e -> System.out.println("\n" + e + "\n"));


    }
    private void postClicked(Post post){
//        listView.getItems().add(post);
        posts.clear();
        posts.add(post);
        posts.addAll(DB.getComments(post.getID()));
        listView.setItems(posts);
    }

    private void fillPosts(){
        posts = FXCollections.observableArrayList();
        posts.setAll(DB.getFollowingsPost(View.getLoggedInUser().getUserName()));
        listView.setItems(posts);
    }
}
