package controllers;

import enums.Dialog;
import models.BusinessUser;
import models.NormalUser;
import models.User;
import views.View;

public class LoginPageController extends Controller{
    public Dialog verifyLogin(String userName, String password) {
        User user = User.getUserByUserName(userName);
        if (user == null)
            return Dialog.USER_DOES_NOT_EXIST;
        if (user.getPassword().equals(password)){
            View.setLoggedInUser(user);
            return Dialog.SUCCESS;
        }
        return Dialog.WRONG_CREDENTIALS;
    }

    public Dialog verifyRegister(String userName, String password,String repeatedPassword,String type,
                                 String securityQuestion, String securityAnswer){
        if (doesAccountExist(userName))
            return Dialog.USER_EXIST;//duplicate account
        Dialog dialog;
        if ((dialog = this.validatePassword(password, repeatedPassword)) != Dialog.SUCCESS){
            return dialog;//password problem
        }
        if (type.toLowerCase().equals("normal")){
            new NormalUser(userName,password,securityQuestion,securityAnswer);
            return Dialog.SUCCESS;//normal user created
        }
        if (type.toLowerCase().equals("business")){
            new BusinessUser(userName,password,securityQuestion,securityAnswer);
            return Dialog.SUCCESS;//business user created
        }
        else return Dialog.USER_CREATION_FAILED;

    }
    public boolean doesAccountExist(String userName){
        return User.getUserByUserName(userName) != null;
    }
    public Dialog validatePassword(String password,String repeatedPassword){
        if (!password.equals(repeatedPassword))
            return Dialog.MISMATCH_PASSWORD;
        if ((password.length()<8))
            return Dialog.SHORT_PASSWORD;
        if (password.length()>15)
            return Dialog.LONG_PASSWORD;
        return Dialog.SUCCESS;
    }
    public String getSecurityQuestion(String userName){
        User user = User.getUserByUserName(userName);
        if (user == null)
            return null;
        String question = user.getSecurityQuestion();
        switch (question){
            case "teacher":
                return "what is the name of your first grade teacher?";
            case "pet":
                return "what is name of your first pet?";
            case "friend":
                return "who is your best friend";
            default:
                return null;
        }
    }
    public Dialog forgotPassword(String userName,String answer) {
        User user = User.getUserByUserName(userName);
        if (user == null)
            return Dialog.USER_DOES_NOT_EXIST;

        String securityAnswer = user.getSecurityAnswer();
        if (securityAnswer.trim().toLowerCase().equals(answer.trim().toLowerCase())) {
            return Dialog.SUCCESS;
        } else {
            return Dialog.WRONG_ANSWER;
        }
    }
    public Dialog resetPassword(String userName, String password, String repeatedPassword){
        Dialog dialog = validatePassword(password, repeatedPassword);
        if (dialog != Dialog.SUCCESS)
            return dialog;
        User user = User.getUserByUserName(userName);
        if (user != null) {
            user.setPassword(password);
            return Dialog.SUCCESS;
        }
        return Dialog.OPERATION_FAILED;
    }

    }
