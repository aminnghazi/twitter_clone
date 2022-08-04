package views;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.DB;

import java.io.IOException;

public class HomePage extends View{
    public GridPane homePage;
    public VBox middleContainer;
    public VBox userContainer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane postsView = FXMLLoader.load(this.getClass().getResource("/fxml/home-page.fxml"));
        Scene scene = new Scene(postsView);
        System.out.println(scene);
        primaryStage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaximized(true);
    }
    public void initialize(){
        loadPostsView();
        loadUsersView();
    }

    private void loadPostsView(){
        // TODO: 8/2/2022 change from parent
        //            Parent posts = FXMLLoader.load(this.getClass().getResource("/fxml/posts-view.fxml"));
        PostsView postsView = new PostsView();
        middleContainer.getChildren().clear();
        middleContainer.getChildren().add(postsView.getStackPane());
    }
    private void loadUsersView() {
        UsersView usersView = new UsersView();
        userContainer.getChildren().clear();
        userContainer.getChildren().add(usersView.getStackPane());
    }

    public void homeClicked(MouseEvent mouseEvent) {
        loadPostsView();
    }

    public void tweetButtonClicked(MouseEvent mouseEvent) {
        // TODO: 8/1/2022 change to correct format insteadof pane
        try {
            GridPane tweetView = FXMLLoader.load(this.getClass().getResource("/fxml/tweet-view.fxml"));
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(tweetView);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.show();
//            middleContainer.getChildren().clear();
//            middleContainer.getChildren().add(parent);
        } catch (IOException e) {
            System.out.println("couldnt load tweet page");
            e.printStackTrace();
        }
    }


}
