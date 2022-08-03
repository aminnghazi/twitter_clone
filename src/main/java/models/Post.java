package models;

import javafx.scene.image.Image;
import views.View;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class Post extends Media {
    private int viewsCount;
    private boolean isAd;

    public Post(String senderUsername, Image image, String text, String parentID, boolean isAd) {
        super(senderUsername, image, text, parentID);
        this.isAd = isAd;
//        DB.allPosts.add(this);
    }
    public static ArrayList<Post> getAllPosts(){
        //gives all posts sorted by time
        ArrayList<Post> posts = DB.getAllPosts();
//        Collections.sort(posts);
        // TODO: 7/29/2022 sort by date 
        return posts;
        
    }

    public void setViewsCount(int viewsCount) {
        this.viewsCount = viewsCount;
    }

    public boolean isAd() {
        return isAd;
    }

    public void setAd(boolean ad) {
        isAd = ad;
    }
    public int getViewsCount() {
        return viewsCount;
    }

    public static ArrayList<Post> getComments(String postID){
        ArrayList<Post> comments = DB.getComments(postID);
//        Collections.sort(comments);
        // TODO: 7/29/2022 sort by date 
        return comments;
    }

    @Override
    public String toString() {
        return super.toString() + "Post{" +
                "viewsCount=" + viewsCount +
                ", isAd=" + isAd +
                '}';
    }

//    public static Post getEmptyComment(){
//        return new Post("","-1","No comments to show","-1",false);
//    }
//    @Override
//    public int compareTo(Post o) {//sort by time
//        try {
//            DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//            LocalDateTime objectCreationTime = LocalDateTime.parse(o.getDateCreated(), df);
//            LocalDateTime thisCreationTime = LocalDateTime.parse(this.getDateCreated(), df);
//            return objectCreationTime.compareTo(thisCreationTime);
//        }catch (DateTimeException e){
//            return 0;
//        }
//    }
}
