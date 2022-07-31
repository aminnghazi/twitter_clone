package views;

import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Dialogs extends Application {
    public Label label;
    String text;
    public void setDialog(String text){
        this.text = text;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    public void initialize(){
        label.setText(text);
    }
}
