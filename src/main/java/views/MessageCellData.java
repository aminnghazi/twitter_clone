package views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import models.Message;

import javax.sound.midi.MidiFileFormat;
import java.awt.*;
import java.io.IOException;

public class MessageCellData {
    @FXML
    HBox messageCell;
    @FXML
    private Label label;

    public MessageCellData() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/message.fxml"));
        fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(Message item) {
        if (item.getSenderUsername().equals(View.getLoggedInUser().getUserName())) {
            messageCell.getStyleClass().clear();
            messageCell.getStyleClass().add("message-cell-right");
            messageCell.setAlignment(Pos.BASELINE_LEFT);
        }else {
            messageCell.setAlignment(Pos.BASELINE_RIGHT);

        }
        label.setText(item.getText());
//        System.out.println(messageCell);
    }

    public HBox getContent() {
        return messageCell;
    }
}
