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
//            setText(item.toString());
            // TODO: 8/2/2022 preload image, because update is called often
            // TODO: 8/2/2022 change parent
            PostCellData data = new PostCellData();
            data.setInfo(item);
            setGraphic(data.getContent());
        }
    }
}
