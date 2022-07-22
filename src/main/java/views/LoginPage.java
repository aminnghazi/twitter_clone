package views;

import controllers.LoginPageController;
import enums.Dialog;
import javafx.stage.Stage;

public class LoginPage extends View{
    private static LoginPageController controller = new LoginPageController();

    @Override
    public void run() {
        this.showOptions();
        String chosenOption = this.getChoice();

        switch (chosenOption){
            case "1":
            case "login":
                this.login();
                break;
            case "2":
            case "register":
                this.register();
                break;
            case "3":
            case "forgot my password":
                this.forgotPassword();
                break;
            case "4":
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println(Dialog.INVALID_CHOICE);
        }
        run();//loop
    }
    @Override
    public void showOptions() {
        System.out.println("please select an option");
        System.out.println("1. login");
        System.out.println("2. register");
        System.out.println("3. forgot my password");
        System.out.println("4. exit");
    }
    private void register() {
        String userName = this.getInput("Enter username");
        String password = this.getInput("Enter password");
        String repeatedPassword = this.getInput("Repeat your password");
        String securityQuestion = this.sequrityQuestion();//choosing security question
        String securityAnswer = this.getInput("enter answer to the security question");

        if (securityQuestion == null) return;
        String accountType = this.chooseAccountType();//choosing account type
        if (accountType == null) return;
        Dialog dialog;
        if (accountType.equals("normal"))
          dialog=controller.verifyRegister(userName,password,repeatedPassword,"normal",securityQuestion,securityAnswer);
        else if (accountType.equals("business"))
          dialog=controller.verifyRegister(userName,password,repeatedPassword,"business",securityQuestion,securityAnswer);
        else return;

        if (dialog == Dialog.SUCCESS){
            System.out.println(Dialog.USER_CREATED);
        }else {
            System.out.println(dialog);
        }

        this.run();//loop
    }
    private void login(){
        String userName = this.getInput("Enter username");
        String password = this.getInput("Enter password");
        Dialog dialog = controller.verifyLogin(userName, password);
        if (dialog == Dialog.SUCCESS){
            HomePage homePage = new HomePage();
            homePage.run();
        }
        else {
            System.out.println(dialog);
        }
        this.run();//loop
    }
    public void forgotPassword(){
        String userName = this.getInput("Enter your userName");
        String securityQuestion = controller.getSecurityQuestion(userName);
        if (securityQuestion == null) {
            System.out.println(Dialog.USER_DOES_NOT_EXIST);
            return;
        }
        System.out.println(securityQuestion);
        String answer = getInput();
        Dialog dialog = controller.forgotPassword(userName,answer);
        if (dialog == Dialog.SUCCESS)
            resetPassword(userName);
        else
            System.out.println(dialog);
        this.run();
    }
    private void resetPassword(String userName){
        String password = this.getInput("Enter password");
        String repeatedPassword = this.getInput("Repeat your password");
        Dialog dialog = controller.resetPassword(userName, password, repeatedPassword);
        if (dialog != Dialog.SUCCESS){
            System.out.println(dialog);//changing password failed
        }
        else {
            System.out.println("password changed successfully");
        }
    }
    private String sequrityQuestion() {
        System.out.println("choose your security question(pick a number):");
        System.out.println("1. what is the name of your first grade teacher?");
        System.out.println("2. what is name of your first pet?");
        System.out.println("3. who is your best friend");
        String securityQuestion;
        String choice = this.getChoice();//security question type
        switch (choice){
            case "1":
                securityQuestion="teacher";
                break;
            case "2":
                securityQuestion="pet";
                break;
            case "3":
                securityQuestion="friend";
                break;
            default:
                return null;
        }
        return securityQuestion;
    }
    private String chooseAccountType() {
        System.out.println("choose your account type");
        System.out.println("1. Normal Account");
        System.out.println("2. Business Account");
        String accountType = getChoice();
        if (accountType.equals("1") || accountType.equals("normal account"))
            return "normal";
        else if (accountType.equals("2") || accountType.equals("business account"))
            return "business";
        else {
            System.out.println(Dialog.INVALID_CHOICE);
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
}
