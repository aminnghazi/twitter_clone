package controllers;

import javafx.scene.control.ListCell;
import models.Card;
import views.ChatCellData;
import views.UserCellData;

public class ChatCell extends ListCell<Card> {
    @Override
    protected void updateItem(Card item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            ChatCellData data = new ChatCellData();
            data.setInfo(item);
            setGraphic(data.getContent());
        }
    }
}
