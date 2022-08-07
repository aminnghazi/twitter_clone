package controllers;

import enums.Dialog;
import models.DB;
import models.Post;
import models.User;
import views.View;

public class TweetViewController extends Controller{
    public Dialog addNewPost(String text, String image,String parentID) {
        User user = View.getLoggedInUser();
        if (text == null)
            return Dialog.EMPTY_TEXT;
        // TODO: 7/21/2022 may need change for adding image
        boolean isAd;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;

        Post post = new Post(user.getUserName(), image, text,parentID,isAd);
        return DB.addPost(post);
    }


//
//        public Dialog addComment(String text, String image,String parentID) {
//            User user = View.getLoggedInUser();
//            if (text == null)
//                return Dialog.EMPTY_TEXT;
//            // TODO: 7/21/2022 may need change for adding image
//            boolean isAd;
//            if (View.getLoggedInUser().isBusiness())
//                isAd = true;
//            else
//                isAd = false;
//            if (DB.getPost(parentID) == null) {
//                return Dialog.POST_DOES_NOT_EXIST;
//            }
//            Post post = new Post(user.getUserName(), image, text,parentID,isAd);
//            return DB.addPost(post);
//        }
}
