package controllers;

import enums.Dialog;
import models.DB;
import models.Post;
import views.View;

import java.util.ArrayList;

public class PostCellController extends Controller{

    public Dialog handleLikingPost(String postID){
        String userName = View.getLoggedInUser().getUserName();
        if (!DB.isLikedBy(userName, postID)) {
            DB.addLike(postID, userName);
            System.out.println("liked");
            return Dialog.MESSAGE_LIKED;
        }else {
            System.out.println("removed");
            DB.removeLike(postID, userName);
            return Dialog.MESSAGE_LIKE_REMOVED;
        }
    }

    public ArrayList<Post> handleSeeComments(String postID) {
        return Post.getComments(postID);
    }


}
