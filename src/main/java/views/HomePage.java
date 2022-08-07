package views;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HomePage extends View{
    public GridPane homePage;
    public VBox userContainer;
    public VBox middleContainer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane postsView = FXMLLoader.load(this.getClass().getResource("/fxml/home-page.fxml"));
        Scene scene = new Scene(postsView);
//        System.out.println(scene);
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
//        loadChatsView();
//        loadChatView();
//        userContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (chatsView != null)
//                    recipient = chatsView.getRecipient();
//            }
//        });
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

    private void loadChatsView() {
        ChatsView chatsView = new ChatsView(middleContainer);
        userContainer.getChildren().clear();
        userContainer.getChildren().add(chatsView.getStackPane());
    }



    public void homeClicked(MouseEvent mouseEvent) {
        loadPostsView();
        loadUsersView();
    }

    public void tweetButtonClicked(MouseEvent mouseEvent) {
            TweetView controller = new TweetView();
            Dialog dialog = new Dialog();
            dialog.getDialogPane().setContent(controller.getContent());
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.show();
    }


    public void messageBtnClicked(MouseEvent mouseEvent) {
        loadChatsView();//loading list of chats you have
    }


}
