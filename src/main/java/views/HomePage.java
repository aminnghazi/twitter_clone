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

import java.io.IOException;

public class HomePage extends View{
    public GridPane homePage;
    public VBox middleContainer;

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane page = FXMLLoader.load(this.getClass().getResource("/fxml/home-page.fxml"));
        Scene scene = new Scene(page);
        primaryStage.setScene(scene);
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.setMaximized(true);
    }
    public void initialize(){
        loadPostsView();
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
    private void loadPostsView(){
        // TODO: 8/2/2022 change from parent
        try {
            Parent posts = FXMLLoader.load(this.getClass().getResource("/fxml/posts-view.fxml"));
            middleContainer.getChildren().clear();
            middleContainer.getChildren().add(posts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
