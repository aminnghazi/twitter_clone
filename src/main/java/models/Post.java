package models;

import views.View;

import java.util.ArrayList;

public class Post extends Media {
    private int viewsCount;
    private boolean isAd;

    public Post(String senderUsername, String image, String text,String parentID) {
        super(senderUsername, image, text, parentID);
        this.viewsCount = 0;
        if (View.getLoggedInUser().isBusiness())
            isAd = true;
        else
            isAd = false;
        DB.addPost(this);
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
