package models;

import enums.Dialog;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.lang.*;

public class DB {
    private static Statement statement;
    public static Dialog addUser(User user){
        short a;
        if (user.isBusiness())
            a=1;
        else
            a = 0;
        try {
            statement.executeUpdate("INSERT INTO user (UserName,Password,JointDate,BirthDate,FirstName,LastName,Business,ProfilePicture,securityQuestionType,securityQuestionAnswer)" +
                    " VALUES ('"
                    +user.getUserName()+"','"
                    +user.getPassword()+"','"
                    + Timestamp.from(Instant.now())+"','"//kal
                    +user.getBirthDate()+"','"
                    +user.getFirstName()+"','"
                    +user.getLastName()+"','"
                    +a+"','"+
                    user.getProfilePicture()+"','"
//                    +((user.getProfilePicture().isEmpty())? "null,":"'"+user.getProfilePicture()+ "'")
                    +user.getSecurityQuestion()+"','"
                    +user.getSecurityAnswer()
                    + "')"
            );
            return Dialog.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Dialog.USER_CREATION_FAILED;
        }

    }

    public static User getUser(String userName) {
        User user = null;
        try{
            ResultSet  resultSet = statement.executeQuery("SELECT*FROM user WHERE UserName='"+userName+"'");
            resultSet.next();
            user = new User(resultSet.getString("UserName"), resultSet.getString("Password"), resultSet.getString("securityQuestionType"),
                    resultSet.getString("securityQuestionAnswer"),resultSet.getBoolean("Business"),resultSet.getString("ProfilePicture"));
            user.setFirstName(resultSet.getString("FirstName"));
            user.setLastName(resultSet.getString("LastName"));
            Timestamp ts = new Timestamp(resultSet.getDate("JointDate").getTime());//kal
            user.setJoinDate(ts);//kal
            user.setBirthDate(resultSet.getDate("BirthDate").toLocalDate());
//            user.setProfilePicture(resultSet.getString("ProfilePicture"));
        }catch (SQLException e) {
//            e.printStackTrace();
        }
        return user;
    }
    public static Dialog deleteUser(String userName){
        try {
            statement.executeUpdate("DELETE FROM user WHERE UserName='"+userName+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static ArrayList<User> getAllUsers(){
        ArrayList<String> userNames = getAllUserNames();
        ArrayList<User> users = new ArrayList<>();
        for(int i=0; i< userNames.size(); i++){
            users.add(getUser(userNames.get(i)));
        }
        return users;
    }

    public static ArrayList<String> getAllUserNames(){
        ArrayList<String> userNames = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM user");
            while(resultSet.next()){
                userNames.add(resultSet.getString("UserName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userNames;
    }

    public static Dialog addPost(Post post){
        int a;
        if (post.isAd()==false)
            a = 0;
        else
            a =1;
        try {
            statement.executeUpdate("INSERT INTO post (ParentID,PostText,PostImage,SenderID,CreateDate,isAdd,postID)" +
                    " VALUES ('"
                    +post.getParentID()+"','"
                    +post.getText()+"','"
                    +post.getImage()+"','"
//                    +((post.getImage().isEmpty())? "null,":"'"+post.getImage()+ "'")+",'"
                    +post.getSenderUsername()+"','"
                    +Timestamp.from(Instant.now())+"','"//kal
                    +a+"','"
                    +post.getSenderUsername()+ Long.toString(Instant.now().toEpochMilli())+"'"
                    +")" );
            return Dialog.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Post getPost(String postID){
        Post post = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post WHERE postID='"+postID+"'");
            resultSet.next();
            post = new Post(resultSet.getString("SenderID"), resultSet.getString("PostImage"), resultSet.getString("PostText"), resultSet.getString("ParentID"),resultSet.getBoolean("isAdd"));
            Timestamp ts = new Timestamp(resultSet.getDate("CreateDate").getTime());//kal
            post.setDateCreated(ts);//kal
            post.setID(resultSet.getString("postID"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
    public static ArrayList<String> getUserPostIDs(String userName){
        ArrayList<String> postIDs = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post WHERE SenderID='"+userName+"'" + "ORDER BY sqlID DESC");
            while(resultSet.next()){
                postIDs.add(resultSet.getString("PostID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postIDs;
    }
    public static ArrayList<Post> getUserPosts(String userName){
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<String> postIDs = getUserPostIDs(userName);
        for(int i=0; i< postIDs.size(); i++){
            posts.add(getPost(postIDs.get(i)));
        }
        return posts;
    }
    public static ArrayList<String> getAllPostIDs(){
        ArrayList<String> postIDs = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post ORDER BY sqlID DESC");
            while(resultSet.next()){
                postIDs.add(resultSet.getString("PostID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postIDs;
    }
    public static ArrayList<Post> getAllPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        ArrayList<String> postIDs = getAllPostIDs();
        for(int i=0; i< postIDs.size(); i++){
            posts.add(getPost(postIDs.get(i)));
        }
        return posts;
    }
    public static Dialog deletePost(String postID){
        try {
            statement.executeUpdate("DELETE FROM Post WHERE PostID='"+postID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static ArrayList<Post> getFollowingsPost(String userName){
        ArrayList<String> followings= getFollowings(userName);
        ArrayList<Post> allFollowingPost = new ArrayList<Post>();
        for(int i=0; i<followings.size(); i++){
            allFollowingPost.addAll(getUserPosts(followings.get(i)));
        }
        return allFollowingPost;
    }
    public static ArrayList<Post> getComments(String parentID){
        ArrayList<Post> posts = getAllPosts();
        for (Post post : posts) {
         if (post.getParentID().equals(parentID))
             posts.add(post);
        }
        return posts;
    }

    public static Dialog editPost(String postID, String newText,String newPostImage){
        try {
            statement.executeUpdate("UPDATE post SET Text='"+newText+"' AND PostImage='"+newPostImage+"' WHERE PostID='"+postID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static void addLike(String postID, String userName){
        try {
            statement.executeUpdate("INSERT INTO like (PostID,LikeDate,LikeUserID) " +
                    "VALUES ('"
                    +postID+"','"
                    +Timestamp.from(Instant.now())+"','"//kal
                    +userName+"')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Dialog removeLike(String postID, String userName){
        try {
            statement.executeUpdate("DELETE FROM Like WHERE PostID='"+postID+"' AND LikeUserID='"+userName+"'");
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.SUCCESS;
        }
        return Dialog.OPERATION_FAILED;
    }
    public static boolean isLikedBy(String userName, String postID){
        boolean is = false;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM like WHERE UserName='"+userName+"' AND PostID='"+postID+"'");
            resultSet.next();
            if(resultSet.getInt(1)>0){
                is = true;
            }
            else{
                is = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
    public static int getLikesCount(String postID){
        int count = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM like WHERE UserName='"+postID+"'");
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static ArrayList<Timestamp> getLikesDate(int postID){
        ArrayList<Timestamp> dates = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT LikeDate FROM like WHERE PostID='"+postID+"'");
            Timestamp ts;//kal
            while (resultSet.next()){
                ts = new Timestamp(resultSet.getDate("LikeDate").getTime());//kal
                dates.add(ts);//kal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
    public static ArrayList<String> getFollowers(String userName){
        ArrayList<String> followers = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT FollowerID FROM follow WHERE FollowingID='"+userName+"'");
            while (resultSet.next()){
                followers.add(resultSet.getString("FollowerID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followers;
    }
    public static ArrayList<String> getFollowings(String userName){
        ArrayList<String> followings = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT FollowingID FROM follow WHERE FollowerID='"+userName+"'");
            while (resultSet.next()){
                followings.add(resultSet.getString("FollowingID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followings;
    }
    public static Dialog Follow(String userName, String userName1){
        try {
            statement.executeUpdate("INSERT INTO follow (FollowingID,FollowerID,FollowDate) " +
                    "VALUES ('"
                    +userName+"','"
                    +userName1+"','"
                    +Timestamp.from(Instant.now())+"'"//kal
                    +")");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog UnFollow(String userName, String userName1){
        try {
            statement.executeUpdate("DELETE FROM follow WHERE FollowerID='"+userName+"' AND FollowingID='"+userName1+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static int getFollowerCount(String userName){
        int count = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM follow WHERE FollowerID='"+userName+"'");
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static int getFollowingCount(String userName){
        int count = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE FollowerID='"+userName+"'");
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static Dialog addView(String postID, String userName){
            try {
                statement.executeUpdate("INSERT INTO like (PostID,ViewDate,ViewUserID) " +
                        "VALUES ('"
                        +postID+"+','"
                        +Timestamp.from(Instant.now())+"','"//kal
                        +userName
                        +"')");
                return Dialog.SUCCESS;
            } catch (SQLException e) {
                e.printStackTrace();
                return Dialog.FOLLOWED;
            }
        }
    public static boolean isViewedBy(String userName, String postID){
        boolean is = false;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT 1 FROM view WHERE LikeUserID='"+userName+"' AND PostID='"+postID+"'");
            resultSet.next();
            if(resultSet.getInt(1)>0){
                is = true;
            }
            else{
                is = false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
    public static int getViewsCount(String postID){
        int count = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE PostID='"+postID+"'");
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    static ArrayList<Timestamp> getViewsDate(){
        ArrayList<Timestamp> dates = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT ViewDate FROM view");
            Timestamp ts;//kal
            while (resultSet.next()){
                ts = new Timestamp(resultSet.getDate("ViewDate").getTime());//kal
                dates.add(ts);//kal
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
    public static Dialog createGroup(String groupID, String ownerID, String groupName, boolean isGroup){
        int a;
        if (isGroup == true)
            a=1;
        else
            a=0;
        try {
            statement.executeUpdate("INSERT INTO group (GroupID,OwnerID,CreateDate,GroupName,isGroup) " +
                    "VALUES ('"
                    +groupID+"','"
                    +ownerID+"','"
                    +Timestamp.from(Instant.now())+"','"//kal
                    +groupName+"',"
                    +a
                    +")");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static String getGroupName(String groupID){
        String groupName = "";
        try {
            ResultSet resultSet = statement.executeQuery("SELECT GroupName FROM group WHERE GroupID='"+groupID+"'");
            resultSet.next();
            groupName = resultSet.getString("GroupName");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupName;
    }
    public static Dialog changeGroupName(String groupID, String newGroupName){
        try {
            statement.executeUpdate("UPDATE group SET GroupName='"+newGroupName+"' WHERE GroupID='"+groupID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static boolean isGroup(String groupID){//kal
        boolean is = false;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT isGroup FROM group WHERE GroupID='"+groupID+"'");
            resultSet.next();
            is = resultSet.getBoolean("isGroup");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return is;
    }
    public static Dialog addMember(String groupID, String memberID, boolean isAdmin){
        try {
            statement.executeUpdate("INSERT INTO group (GroupID,MemberID,JoinDate,isAdmin) " +
                    "VALUES ('"
                    +groupID+"','"
                    +memberID+"','"
                    +Timestamp.from(Instant.now())+"','"//kal
                    +isAdmin
                    +"')");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog deleteMember(String groupID, String memberID){
        try {
            statement.executeUpdate("DELETE FROM member WHERE GroupID='"+groupID+"' AND MemberID='"+memberID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static ArrayList<String> getGroups(String userName){
        ArrayList<String> groups = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT GroupID FROM group WHERE MemberID='"+userName+"'");//kal
            while (resultSet.next()){
                groups.add(resultSet.getString("GroupID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups;
    }
    public static ArrayList<String> getMembers(String groupID, Statement statement){
        ArrayList<String> members = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT MemberID FROM group WHERE GroupID='"+groupID+"'");//kal
            while (resultSet.next()){
                members.add(resultSet.getString("MemberID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    public static Dialog addMessage(String groupID, String senderID, String photoID, String text){
        try {
            statement.executeUpdate("INSERT INTO message (GroupID,SenderID,SentDate,PhotoMessage,Text) " +
                    "VALUES ('"
                    +groupID+"','"
                    +senderID+"','"
                    +Timestamp.from(Instant.now())+"','"//kal
                    +photoID +"','"
//                    +((photoID.isEmpty())? "null,":"'"+photoID+ "'")
                    +text
                    +"')");// TODO: 7/30/2022 may be wrong
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog editMessage(int messageID,String text, String photoID){
        try {
            statement.executeUpdate("UPDATE message SET Text='"+text+"' PhotoMessage='"+photoID+"'+ WHERE MessageID='"+messageID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
            return Dialog.OPERATION_FAILED;
//            e.printStackTrace();
        }
    }
    public static Dialog deleteMessage(int messageID){
        try {
            statement.executeUpdate("DELETE FROM message WHERE MessageID='"+messageID+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog editPassword(String userName,String newPassword){
        try {
            statement.executeUpdate("UPDATE user SET Password='"+newPassword+"' WHERE UserName='"+userName+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog editFirstName(String userName,String firstName){
        try {
            statement.executeUpdate("UPDATE user SET FirstName='"+firstName+"' WHERE UserName='"+userName+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog editLastName(String userName,String lastName){
        try {
            statement.executeUpdate("UPDATE user SET LastName='"+lastName+"' WHERE UserName='"+userName+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static Dialog editProfilePicture(String userName,String profilePicture){
        try {
            statement.executeUpdate("UPDATE user SET ProfilePicture='"+profilePicture+"' WHERE UserName='"+userName+"'");
            return Dialog.SUCCESS;
        } catch (SQLException e) {
//            e.printStackTrace();
            return Dialog.OPERATION_FAILED;
        }
    }
    public static boolean isFollowing(String followerUserName,String followedUserName ){
        for (String follower : DB.getFollowers(followedUserName)) {
            if (DB.getUser(follower).getUserName().equals(followerUserName))
                return true;
        }
        return false;
    }
    public static Dialog start(){
        try {
            java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
            statement = connection.createStatement();
            return Dialog.DATABASE_CONNECTED;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(Dialog.DATABASE_NOT_CONNECTED);
            //System.out.println("nohr");
        }
        return Dialog.OPERATION_FAILED;
    }
}
