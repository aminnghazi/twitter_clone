package views;

import controllers.ChatViewController;
import enums.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.DB;
import models.Post;
import models.User;
import models.Utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateChatView implements Initializable {

    @FXML
    Button exitBtn;
    @FXML
    Circle profile;
    @FXML
    Label error;
    @FXML
    TextField name;
    @FXML
    ListView<String> listView;
    @FXML
    ListView<String> listView2;
    @FXML
    Button attachBtn;
    @FXML
    Button createBtn;
    @FXML
    GridPane gridPane;

    String image="-1";

    ObservableList<String> users;
    ObservableList<String> chosenUsers = FXCollections.observableArrayList();
    private static ChatViewController controller = new ChatViewController();

    public CreateChatView(){
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/create-chat.fxml"));
        loader.setController(this);

        try {
            gridPane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillUsers();
        gridPane.setPrefHeight(View.getScreenHeight()*3/5);
        gridPane.setPrefWidth(View.getScreenWidth()*3/7);
        profile.setFill(Color.TRANSPARENT);
        setListeners();

    }

    private void fillUsers() {
        ArrayList<String> followings = DB.getFollowings(View.getLoggedInUser().getUserName());
        users = FXCollections.observableArrayList();
        users.setAll(followings);
        users.removeAll(chosenUsers);
        listView.setItems(users);
        listView2.setItems(chosenUsers);
    }

    private void setListeners() {
        attachBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                attachImage(event);
            }
        });
        createBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createGroup();
            }
        });
        exitBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Button button = (Button) event.getSource();
                Stage stage = (Stage) button.getScene().getWindow();
                stage.close();
            }
        });
        listView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String choice = listView.getSelectionModel().getSelectedItem();
                if (choice != null) {
                    chosenUsers.add(choice);
                    fillUsers();
                }
            }
        });
        listView2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String choice = listView2.getSelectionModel().getSelectedItem();
                if (choice != null) {
                    chosenUsers.remove(choice);
                    fillUsers();
                }
            }
        });
    }

    private void createGroup() {
//        List<T> list = ObservableList<T>.stream().collect(Collectors.toList());

        ArrayList<String> users = new ArrayList<String>(chosenUsers);
        Dialog dialog = controller.addGroup(name.getText(),image,
                View.getLoggedInUser().getUserName(),users);
        if (dialog != Dialog.SUCCESS)
            error.setText(dialog.toString());
        else {
            View.showDialog("Group created successfully");

        }
    }

    private void attachImage(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png")
                ,new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File file = fileChooser.showOpenDialog(stage);
        image = Utility.encodeImageFile(file);
        profile.setFill(new ImagePattern(Utility.decodeImageFile(image)));
    }

    public GridPane getContent(){
        return gridPane;

    }
}
