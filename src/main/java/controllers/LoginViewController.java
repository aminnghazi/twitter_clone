package controllers;

import enums.Dialog;
import models.DB;
import models.User;
import views.View;

public class LoginViewController extends Controller{
    public Dialog verifyLogin(String userName, String password) {
        User user = DB.getUser(userName);
        if (user == null)
            return Dialog.USER_DOES_NOT_EXIST;
        if (user.getPassword().equals(password)){
            View.setLoggedInUser(user);
            return Dialog.SUCCESS;
        }
        return Dialog.WRONG_CREDENTIALS;
    }
}
