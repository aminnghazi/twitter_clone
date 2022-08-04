package controllers;

import javafx.scene.control.ListCell;
import models.Card;
import models.Post;
import views.PostCellData;
import views.UserCellData;

public class UserCell extends ListCell<Card> {
    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            UserCellData data = new UserCellData();
            data.setInfo(item);
            setGraphic(data.getContent());
        }    }
}
