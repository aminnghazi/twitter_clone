package controllers;


import javafx.scene.control.ListCell;
import models.Post;
import views.PostCellData;

public class PostCell extends ListCell<Post> {
    private static int  num=0;
    @Override
    protected void updateItem(Post item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            PostCellData data = new PostCellData();
            data.setInfo(item);
            setGraphic(data.getContent());
        }
    }
}
