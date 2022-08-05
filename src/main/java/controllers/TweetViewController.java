package controllers;

import enums.Dialog;
import models.DB;
import models.Post;
import models.User;
import views.View;

public class TweetViewController extends Controller{
    public Dialog addNewPost(String text, String image) {
        User user = View.getLoggedInUser();
        if (text == null)
            return Dialog.EMPTY_TEXT;
        // TODO: 7/21/2022 may need change for adding image
        boolean isAd;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;

        Post post = new Post(user.getUserName(), image, text,null,isAd);
        return DB.addPost(post);
    }

    public Dialog handleAddComment(String text, Post parentPost) {
        boolean isAd;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;
        Post comment = new Post(View.getLoggedInUser().getUserName(), null, text, parentPost.getID(),isAd);
        DB.addPost(comment);
        return Dialog.COMMENT_ADDED_SUCCESSFULLY;
    }
}
