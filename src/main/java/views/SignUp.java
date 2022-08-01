package views;

import enums.Dialog;
import enums.Messages;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SignUp implements Initializable {
    public ComboBox comboBox;
    public GridPane gridPane;
    public PasswordField password;
    public TextField userName;
    public TextField lastName;
    public TextField firstName;
    public PasswordField repeatedPassword;
    public ComboBox AccountType;
    public Label error;
    public TextField answer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            comboBox.getItems().add(Messages.TEACHER);
            comboBox.getItems().add(Messages.PET);
            comboBox.getItems().add(Messages.FRIEND);
            AccountType.getItems().add("Normal account");
            AccountType.getItems().add("Business account");

    }


    public void exitclicked(MouseEvent mouseEvent) {
        Pane pane = (Pane) mouseEvent.getSource();
        Stage stage = (Stage) pane.getScene().getWindow();
        stage.close();
    }

    public void Cancelclicked(MouseEvent mouseEvent) {
        Button btn = (Button) mouseEvent.getSource();
        Stage stage = (Stage) btn.getScene().getWindow();
        stage.close();
    }

    public void signUpClicked(MouseEvent mouseEvent) {
        String firstName = this.firstName.getText();
        String lastName = this.lastName.getText();
        String answer = this.answer.getText();
        String repeatedPassword = this.repeatedPassword.getText();
        String password = this.password.getText();
        String userName = this.userName.getText();
        String picture = "-1";
        String accountType;
        String securityQestion;
        if (AccountType.getValue() == null){
            error.setText("Choose your account type");
            return;
        }else {
            switch (AccountType.getValue().toString().toLowerCase()) {
                case "normal account":
                    accountType = "normal";
                    System.out.println(accountType);
                    break;
                case "Business account":
                    accountType = "business";
                    break;
                default:
                    return;
            }
        }

    if (comboBox.getValue() == null){
        error.setText("Choose your security question type");
        return;
    }else {
        switch (comboBox.getValue().toString()){
            case "what is the name of your first grade teacher?":
                securityQestion="teacher";
                break;
            case "what is name of your childhood pet?":
                securityQestion="pet";
                break;
            case "who is your best friend":
                securityQestion="friend";
                break;
            default:
                error.setText("wrong security question");
                return;
        }
    }
        Dialog dialog = LoginView.controller.verifyRegister
                (userName,firstName,lastName,password,repeatedPassword,accountType,
                        securityQestion,answer);
        if (dialog == Dialog.SUCCESS) {
            View.showDialog("Account created successfully");
            return;
        }
        else
            error.setText(dialog.toString());
    }

    public void profileButtonClicked(MouseEvent mouseEvent) {
        Button button = (Button) mouseEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png")
                ,new FileChooser.ExtensionFilter("Image files", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        try {
            Image image = ImageIO.read(selectedFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
