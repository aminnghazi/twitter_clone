package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import models.Post;

public class PostCell extends ListCell<Post> {
    @Override
    protected void updateItem(Post item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            setText(item.toString());
            // TODO: 8/2/2022 preload image, because update is called often 
            // TODO: 8/2/2022 change parent
            try {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/post.fxml"));
                setGraphic(root);
            } catch (Exception e) {
//                System.out.println("noooo");
                e.printStackTrace();
            }
        }
    }
}
