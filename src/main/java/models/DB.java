package models;

import enums.Dialog;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.lang.*;

public class DB {
    private static Statement statement;
    public static void addUser(User user){
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
                    +LocalDate.now()+"','"
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
        } catch (SQLException e) {
            e.printStackTrace();
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
            user.setJoinDate(resultSet.getDate("JointDate").toLocalDate());
            user.setBirthDate(resultSet.getDate("BirthDate").toLocalDate());
//            user.setProfilePicture(resultSet.getString("ProfilePicture"));
        }catch (SQLException e) {
//            e.printStackTrace();
        }
        return user;
    }
    public static void deleteUser(String userName){
        try {
            statement.executeUpdate("DELETE FROM user WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            System.out.println("ooo");
            e.printStackTrace();
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
    public static void addPost(Post post){
        try {
            statement.executeUpdate("INSERT INTO post (ParentID,PostText,PostImage,SenderID,CreateDate,viewsCount,likesCount,isAdd)" +
                    " VALUES ('"
                    +post.getParentID()+"','"
                    +post.getText()+"',"
                    +((post.getImage().isEmpty())? "null,":"'"+post.getImage()+ "'")+",'"
                    +post.getSenderUsername()+"','"
                    +post.getDateCreated()+"','"
                    +"0','"
                    +"0',"
                    +post.isAd()
                    +")" );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Post getPost(String postID){
        Post post = null;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post WHERE PostID='"+postID+"'");
            resultSet.next();
            post = new Post(resultSet.getString("SenderID"), resultSet.getString("PostImage"), resultSet.getString("PostText"), resultSet.getString("ParentID"));
            post.setDateCreated(resultSet.getDate("CreateDate"));
            post.setAd(resultSet.getBoolean("isAdd"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }
    public static ArrayList<String> getUserPostIDs(String userName){
        ArrayList<String> postIDs = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post WHERE SenderID='"+userName+"'");
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
            ResultSet resultSet = statement.executeQuery("SELECT*FROM post");
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
    public static void deletePost(String postID){
        try {
            statement.executeUpdate("DELETE FROM Post WHERE PostID='"+postID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void editPost(String postID, String newText,String newPostImage){
        try {
            statement.executeUpdate("UPDATE post SET Text='"+newText+"' AND PostImage='"+newPostImage+"' WHERE PostID='"+postID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void addLike(String postID, String userName){
        try {
            statement.executeUpdate("INSERT INTO like (PostID,LikeDate,LikeUserID) " +
                    "VALUES ('"
                    +postID+"','"
                    + LocalDate.now()+"','"
                    +userName+"')"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void removeLike(String postID, String userName){
        try {
            statement.executeUpdate("DELETE FROM Like WHERE PostID='"+postID+"' AND LikeUserID='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    public static ArrayList<java.time.LocalDate> getLikesDate(String postID){
        ArrayList<java.time.LocalDate> dates = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT LikeDate FROM like WHERE PostID='"+postID+"'");
            while (resultSet.next()){
                dates.add(resultSet.getDate("LikeDate").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
    public static ArrayList<String> getFollowers(String userName){
        ArrayList<String> followers = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT Follower FROM follow WHERE Following='"+userName+"'");
            while (resultSet.next()){
                followers.add(resultSet.getString("Follower"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followers;
    }
    public static ArrayList<String> getFollowings(String userName){
        ArrayList<String> followings = new ArrayList<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT Following FROM follow WHERE Follower='"+userName+"'");
            while (resultSet.next()){
                followings.add(resultSet.getString("Following"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return followings;
    }
    public static void Follow(String userName, String userName1){
        try {
            statement.executeUpdate("INSERT INTO follow (FollowingID,FollowerID,FollowDate) " +
                    "VALUES ("
                    +userName+"','"
                    +userName1+"','"
                    +LocalDate.now()+"','"
                    +")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void UnFollow(String userName, String userName1){
        try {
            statement.executeUpdate("DELETE FROM follow WHERE Follower='"+userName+"' AND Following='"+userName1+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getFollowerCount(String userName){
        int count = 0;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE Follower='"+userName+"'");
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
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE Follower='"+userName+"'");
            resultSet.next();
            count = resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    public static void addView(String postID, String userName){
        try {
            statement.executeUpdate("INSERT INTO like (PostID,ViewDate,ViewUserID) " +
                    "VALUES ('"
                    +postID+"+','"
                    +LocalDate.now()+"','"
                    +userName
                    +"')");
        } catch (SQLException e) {
            e.printStackTrace();
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
    public static int getViewsCount(String postID, Statement statement){
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
    public static ArrayList<java.time.LocalDate> getViewsDate(Statement statement){
        ArrayList<java.time.LocalDate> dates = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT ViewDate FROM view");
            while (resultSet.next()){
                dates.add(resultSet.getDate("ViewDate").toLocalDate());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dates;
    }
    public static void createGroup(String groupID, String ownerID, String groupName, boolean isGroup, Statement statement){
        try {
            statement.executeUpdate("INSERT INTO group (GroupID,OwnerID,CreateDate,GroupName,isGroup) " +
                    "VALUES ('"
                    +groupID+"','"
                    +ownerID+"','"
                    +LocalDate.now()+"','"
                    +groupName+"',"
                    +isGroup
                    +")");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static String getGroupName(String groupID, Statement statement){
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
    public static void changeGroupName(String groupID, String newGroupName, Statement statement){
        try {
            statement.executeUpdate("UPDATE group SET GroupName='"+newGroupName+"' WHERE GroupID='"+groupID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static boolean isGroup(String groupID, Statement statement){
        boolean is = false;
        try {
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM group WHERE GroupID='"+groupID+"'");
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
    public static void addMember(String groupID, String memberID, boolean isAdmin, Statement statement){
        try {
            statement.executeUpdate("INSERT INTO group (GroupID,MemberID,JoinDate,isAdmin) " +
                    "VALUES ('"
                    +groupID+"','"
                    +memberID+"','"
                    +LocalDate.now()+"','"
                    +isAdmin
                    +"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteMember(String groupID, String memberID, Statement statement){
        try {
            statement.executeUpdate("DELETE FROM member WHERE GroupID='"+groupID+"' AND MemberID='"+memberID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<String> getGroups(String userName, Statement statement){
        ArrayList<String> groups = new ArrayList<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT GroupID FROM group WHERE MemberID="+userName);
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
            ResultSet resultSet = statement.executeQuery("SELECT MemberID FROM group WHERE GroupID="+groupID);
            while (resultSet.next()){
                members.add(resultSet.getString("MemberID"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }
    public static void addMessage(String groupID, String senderID, String photoID, String text, Statement statement){
        try {
            statement.executeUpdate("INSERT INTO message (GroupID,SenderID,SentDate,PhotoMessage,Text) " +
                    "VALUES ('"
                    +groupID+"','"
                    +senderID+"','"
                    +LocalDate.now()+"','"
                    +((photoID.isEmpty())? "null,":"'"+photoID+ "'")
                    +",'"
                    +text
                    +"')");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editMessage(int messageID,String text, String photoID){
        try {
            statement.executeUpdate("UPDATE message SET Text='"+text+"' PhotoMessage='"+photoID+"'+ WHERE MessageID='"+messageID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteMessage(int messageID){
        try {
            statement.executeUpdate("DELETE FROM message WHERE MessageID='"+messageID+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editPassword(String userName,String newPassword){
        try {
            statement.executeUpdate("UPDATE user SET Password='"+newPassword+"' WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editFirstName(String userName,String firstName){
        try {
            statement.executeUpdate("UPDATE user SET FirstName='"+firstName+"' WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editLastName(String userName,String lastName){
        try {
            statement.executeUpdate("UPDATE user SET LastName='"+lastName+"' WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void editProfilePicture(String userName,String profilePicture){
        try {
            statement.executeUpdate("UPDATE user SET ProfilePicture='"+profilePicture+"' WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Dialog start(){
        try {
            java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
            statement = connection.createStatement();
            return Dialog.DATABASE_CONNECTED;
//            statement.executeUpdate("DELETE FROM account WHERE Followers='FHJHF' AND Followings='FHHBT'");
//            System.out.println("Deleted successfully");





//            statement.executeUpdate("UPDATE account SET Password='lfhgkf' WHERE Followers='jhgf' AND Followings='hdfhc'");
//            System.out.println("Updated successfully");



//            statement.executeUpdate("INSERT INTO account (ID,Password,Followers,Followings) VALUES ('jsjghbff','HAKFSF','FHJHF','FHHBT')" );
//            System.out.println("Inserted successfully");

//            ResultSet resultSet = statement.executeQuery("SELECT*FROM account");
//            while (resultSet.next()){
//                System.out.println(resultSet.getString("ID"));
//                System.out.println(resultSet.getString("Password"));
//                //System.out.println(resultSet.getString("JoinDate"));
//                //System.out.println(resultSet.getString("BirthDate"));
//                System.out.println(resultSet.getString("Followers"));
//                System.out.println(resultSet.getString("Followings"));
//            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(Dialog.DATABASE_NOT_CONNECTED);
            //System.out.println("nohr");
        }
        return Dialog.OPERATION_FAILED;
    }
}
