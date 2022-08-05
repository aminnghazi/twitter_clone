package views;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ChatView extends View{
    public Label name;
    public Label id;
    public Button image;
    public Button send;

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void galleryButtonClicked(MouseEvent mouseEvent) {
    }

    public void sendClicked(MouseEvent mouseEvent) {
        send();
    }
    public void keyPressed(KeyEvent ke) {
            if (ke.getCode().equals(KeyCode.ENTER)) {
                send();
            }
    }

    private void send() {
    }
}
