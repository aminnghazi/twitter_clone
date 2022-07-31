package controllers;

import enums.Dialog;
import models.DB;
import models.Post;
import models.User;
import views.View;

import java.util.ArrayList;

public class PostsViewController extends Controller{

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
        DB.addPost(post);
        return Dialog.SUCCESS;
    }
    public ArrayList<Post> getAllPosts(){
        return Post.getAllPosts();
    }
    public ArrayList<Post> getFollowingsPost(){
        ArrayList<Post> posts = DB.getFollowingsPost(View.getLoggedInUser().getUserName());
//        String userName = View.getLoggedInUser().getUserName();
//        for (Post post : posts) {
////            if (DB.isFollowing(userName, post.getSenderUsername()))
//            posts.add(post);
//            // TODO: 8/1/2022 may be reveresed
//        }
        return posts;
    }
    public Dialog handleLikingPost(String userName, String postID){
        // TODO: 7/29/2022 add dialog
        if (DB.isLikedBy(userName, postID)) {
            DB.addLike(userName, postID);
            return Dialog.MESSAGE_LIKED;// TODO: 7/29/2022 change with right dialog
        }else {
            DB.removeLike(postID, userName);
            return Dialog.MESSAGE_LIKED;
        }
    }

    public ArrayList<Post> handleSeeComments(String postID) {
        return Post.getComments(postID);
    }

    public Dialog handleAddComment(String text, Post parentPost) {
        boolean isAd;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;
        new Post(View.getLoggedInUser().getUserName(), null, text, parentPost.getID(),isAd);
        return Dialog.COMMENT_ADDED_SUCCESSFULLY;
    }
}
