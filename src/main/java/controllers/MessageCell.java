package controllers;

import javafx.scene.control.ListCell;
import models.Message;
import views.MessageCellData;
import views.UserCellData;

public class MessageCell extends ListCell<Message> {
    @Override
    protected void updateItem(Message item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        }
        else {
            MessageCellData data = new MessageCellData();
            data.setInfo(item);
            setGraphic(data.getContent());
        }
    }
}
