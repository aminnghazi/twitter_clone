package controllers;

import enums.Dialog;
import models.DB;
import views.View;

public class SuggestonsViewController {
    public SuggestonsViewController() {
    }

    public Dialog handleFollowing(String userName) {
        String currentUserName = View.getLoggedInUser().getUserName();
        if (userName == currentUserName)
            return Dialog.SELF_FOLLOW;
        Dialog dialog;
        if (!DB.isFollowing(currentUserName,userName)) {
            dialog = DB.Follow(userName,currentUserName);
            if (dialog == Dialog.SUCCESS)
                return Dialog.FOLLOWED;
            else
                return Dialog.ALREADY_FOLLOWING;
        }
        else {
            dialog = DB.UnFollow(currentUserName, userName);
            if (dialog== Dialog.SUCCESS)
                return Dialog.UNFOLLOWED;
            else return dialog;
        }
    }
}
