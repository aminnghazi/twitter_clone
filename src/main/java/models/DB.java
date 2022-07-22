package models;

import enums.Dialog;

import java.util.ArrayList;

public class DB {
    public static final ArrayList<String> likes = new ArrayList<>();
    public static final ArrayList<String> follows = new ArrayList<>();
    public final static ArrayList<Post> allPosts = new ArrayList<>();
    public final static ArrayList<User> allUsers = new ArrayList<>();
    public static Dialog Like(String userName, String postID){
        System.out.println("hi");
        int index = isLiked(userName,postID);
        if (index >= 0) {
            likes.remove(index);
            return Dialog.MESSAGE_LIKE_REMOVED;
        }
        likes.add(userName + "#" + postID);
        return Dialog.MESSAGE_LIKED;
    }

    public static int isLiked(String userName, String postID){
        for (int i = 0; i < likes.size(); i++) {
            String[] split = likes.get(i).split("#");
            if (split[0].equals(userName) && split[1].equals(postID))
                return i;
        }
        return -1;
    }
    public static int getLikesCount(String postID){
        int count = 0;
        for (int i = 0; i < likes.size(); i++) {
            String[] split = likes.get(i).split("#");
            if (split[1].equals(postID))
                count++;
        }
        return count;
    }
    public static ArrayList<Post> getAllPosts(){
        //all posts except comments
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.getParentID().equals("-1"))
                posts.add(post);
        }
        return posts;
    }
    public static ArrayList<Post> getComments(String postID){
        ArrayList<Post> comments = new ArrayList<>();
        for (Post post : allPosts) {
            if (post.getParentID().equals(postID))
                comments.add(post);
        }
        return comments;
    }

}
