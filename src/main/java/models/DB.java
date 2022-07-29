import com.mysql.cj.callback.UsernameCallback;

import java.io.PipedOutputStream;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.lang.*;

public class Connection {
    //Statement statement;
    static void AddUser(User user, Statement statement){
        statement.executeUpdate("INSERT INTO user (UserName,Password,JointDate,BirthDate,FirstName,LastName,Business,ProfilePicture)" +
                " VALUES ('"
                +user.getUserName()+"','"
                +user.getPassword()+"','"
                +LocalDate.now()+"','"
                +user.getBirthDate()+"','"
                +user.getFirstName()+"','"
                +user.getLastName()+"',"
                +user.isBusiness()+","
                +((user.getProfilePicture().isEmpty())? "null,":"'"+user.getProfilePicture()+ "'")
                +")" );
    }

    static User getUser(String userName, Statement statement) {
        ResultSet  resultSet = statement.executeQuery("SELECT*FROM user WHERE UserName='"+userName+"'");
        resultSet.next();
        user = new User(resultSet.getString("UserName"), resultSet.getString("Password"), null, null);
        user.setFirstName(resultSet.getString("FirstName"));
        user.setLastName(resultSet.getString("LastName"));
        user.setJointDate(resultSet.getDate("JointDate").toLocalDate());
        user.setBirthDate(resultSet.getDate("BirthDate").toLocalDate());
        user.setIsBusiness(resultSet.getBoolean("Business"));
        user.setProfilePicture(resultSet.getString("ProfilePicture"));
    }catch (SQLException e) {
        e.printStackTrace();
    }
        return user;
}
    static void deleteUser(String userName, Statement statement){
        try {
            statement.executeUpdate("DELETE FROM user WHERE UserName='"+userName+"'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    static ArrayList<User> getAllUsers(Statement statement){
        ArrayList<String> userNames = getAllUserNames(statement);
        ArrayList<User> users = new ArrayList<>();
        for(int i=0; i< userNames.size(); i++){
            users.add(getUser(userNames.get(i),statement));
        }
        return users;
    }
    static ArrayList<String> getAllUserNames(Statement statement){
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
    static void addPost(Post post, Statement statement){
        statement.executeUpdate("INSERT INTO post (ParentID,PostText,PostImage,SenderID,CreateDate," +
                //"viewsCount,likesCount" +      //چه باید کرد؟
                ",isAdd)" +
                " VALUES ('"
                +post.getParentID()+"','"
                +post.getText()+"','"
                +((post.getPostImage().isEmpty())? "null,":"'"+user.getPostImage()+ "'")+"','"
                +post.getSenderID()+"','"
                +post.getCreateDate()+"','"
                //+post.viewsCount()+"','"
                //+post.likesCount()+"','"
                +post.isAdd()
                +"')" );
    }
    static Post getPost(int PostID, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT*FROM post");
        boolean fnx = true;
        while (resultSet.next() && fnx) {
            if (resultSet.getString("PostID") == PostID)
                fnx = false;// اینجا ممکنه باگ بخوره باید چک بشه
        }
        Post post = new Post(resultSet.getString("SenderID"), resultSet.getString("PostImage"), resultSet.getString("PostText"), resultSet.getString("ParentID"));

        post.setJointDate(resultSet.getDate("CreateDate"));
        post.setBusiness(resultSet.getBoolean("isAdd"));
        return post;
    }
    static ArrayList<Post> getUserPosts(String userName, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT*FROM post WHERE SenderID="+userName);
        ArrayList<Post> posts = ArrayList<Post>();
        while (resultSet.next()){
            posts.add(getPost(resultSet.getInt("PostID"),statement));
        }
        return posts;
    }
    static ArrayList<Post> GetAllPosts(){
        ArrayList<Post> posts = new ArrayList<Post>();
        ResultSet resultSet = statement.executeQuery("SELECT*FROM post");
        while (resultSet.next()){
            posts.add(getUser(resultSet.getString("Post")));
        }
        return posts;
    }
    static void deletePost(int postID, Statement statement){
        statement.executeUpdate("DELETE FROM Post WHERE PostID="+postID);
    }
    static ArrayList<Post> getFollowingsPost(String userName, Statement statement){
        ArrayList<String> followings= getFollowings(userName, statement);
        ArrayList<Post> allFollowingPost = new ArrayList<Post>();
        for(int i=0; i<followings.size(); i++){
            allFollowingPost.addAll(getUserPosts(followings.get(i),statement));
        }
        return allFollowingPost;
    }
    static ArrayList<Post> getComments(int parentID){}
    static void editPost(int postID, String newText, Statement statement){
        statement.executeUpdate("UPDATE post SET Text="+newText+" WHERE PostID="+postID);//???
    }
    static void addLike(int postID, String userName, Statement statement){
        statement.executeUpdate("INSERT INTO like (PostID,LikeDate,LikeUserID) " +
                "VALUES ('"
                +postID+"','"
                +LocalDate.now()+"','"
                +userName+"')"
        );
    }
    static void removeLike(int postID, String userName, Statement statement){
        statement.executeUpdate("DELETE FROM Like WHERE PostID="+postID+" AND LikeUserID="+userName);
    }
    static boolean isLikedBy(String userName, String postID, Statement statement){
        boolean is;
        try {
            statement.executeQuery("SELECT 1 FROM Like WHERE LikeUserID="+userName+" AND PostID="+postID);
            is = true;
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }
    static int getLikesCount(String postID, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT PostID FROM post");
        int i=0;
        while (resultSet.next()){
            if(resultSet.getString("PostID") == postID){
                i++;
            }
        }
        return i;
        //ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Follow WHERE FollowingID=userName");
    }
    static ArrayList<java.time.LocalDate> getLikesDate(int postID, Statement statement){
        ArrayList<java.time.LocalDate> dates = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT LikeDate FROM like WHERE PostID="+postID);
        while (resultSet.next()){
            dates.add(resultSet.getDate("LikeDate"));
        }
        return dates;
    }
    static ArrayList<String> getFollowers(String userName, Statement statement){
        ArrayList<String> followers = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT Follower FROM follow WHERE Following="+userName);
        while (resultSet.next()){
            followers.add(resultSet.getDate("Follower"));
        }
        return followers;
    }
    static ArrayList<String> getFollowings(String userName, Statement statement){
        ArrayList<String> followings = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT Following FROM follow WHERE Follower="+userName);
        while (resultSet.next()){
            followings.add(resultSet.getDate("Following"));
        }
        return followings;
    }
    static void Follow(String userName, String userName1, Statement statement){
        statement.executeUpdate("INSERT INTO follow (FollowingID,FollowerID,FollowDate) " +
                "VALUES ("
                +userName+"','"
                +userName1+"','"
                +LocalDate.now()+"','"
                +")");
    }
    static void UnFollow(String userName, String userName1, Statement statement){
        statement.executeUpdate("DELETE FROM follow WHERE Follower="+userName+" AND Following="+userName1);
    }
    static int getFollowerCount(String userName, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE Follower="+userName);
        // برگرداندن
    }
    static int getFollowingCount(String userName, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE Follower="+userName);
        // برگرداندن
    }
    static void addView(String postID, String userName, Statement statement){
        statement.executeUpdate("INSERT INTO like (PostID,ViewDate,ViewUserID) " +
                "VALUES ('"
                +postID+"+','"
                +LocalDate.now()+"','"
                +userName
                +"')");
    }
    static boolean isViewedBy(String userName, String postID, Statement statement){
        boolean is;
        try {
            statement.executeQuery("SELECT 1 FROM view WHERE LikeUserID="+userName+" AND PostID="+postID);
            is = true;
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }
    static int getViewsCount(String postID, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM View WHERE PostID="+postID);
        // مشکل سر برگرداندن
    }
    static ArrayList<java.time.LocalDate> getViewsDate(Statement statement){
        ArrayList<java.time.LocalDate> dates = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT ViewDate FROM view");
        while (resultSet.next()){
            dates.add(resultSet.getDate("ViewDate"));
        }
        return dates;
    }
    static void createGroup(String groupID, String ownerID, String groupName, boolean isGroup, Statement statement){
        statement.executeUpdate("INSERT INTO group (GroupID,OwnerID,CreateDate,GroupName,isGroup) " +
                "VALUES ('"
                +groupID+"','"
                +ownerID+"','"
                +LocalDate.now()+"','"
                +groupName+"','"
                +isGroup
                +"')");
    }
    static String getGroupName(String groupID, Statement statement){
        ResultSet resultSet = statement.executeQuery("SELECT GroupName FROM group WHERE GroupID="+groupID);
        return resultSet.getString("GroupID");
    }
    static void changeGroupName(String groupID, String newGroupName, Statement statement){
        ResultSet resultSet = statement.executeUpdate("UPDATE group SET GroupName="+newGroupName+" WHERE GroupID="+groupID);
    }
    static boolean isGroup(String groupID, Statement statement){
        boolean is;
        try {
            statement.executeQuery("SELECT 1 FROM group WHERE GroupID="+groupID);
            is = true;
        } catch (SQLException e) {
            e.printStackTrace();
            is = false;
        }
        return is;
    }
    static void addMember(String groupID, String memberID, boolean isAdmin, Statement statement){
        statement.executeUpdate("INSERT INTO group (GroupID,MemberID,JoinDate,isAdmin) " +
                "VALUES ('"
                +groupID+"','"
                +memberID+"','"
                +LocalDate.now()+"','"
                +isAdmin
                +"')");
    }
    static void deleteMember(String groupID, String memberID, Statement statement){
        statement.executeUpdate("DELETE FROM member WHERE GroupID="+groupID+" AND MemberID="+memberID);
    }
    static ArrayList<String> getGroups(String userName, Statement statement){
        ArrayList<String> groups = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT GroupID FROM group WHERE MemberID="+userName);
        while (resultSet.next()){
            groups.add(resultSet.getString("GroupID"));
        }
        return groups;
    }
    static ArrayList<String> getMembers(String groupID, Statement statement){
        ArrayList<String> members = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT MemberID FROM group WHERE GroupID="+groupID);
        while (resultSet.next()){
            members.add(resultSet.getString("MemberID"));
        }
        return members;
    }
    static void addMessage(String groupID, String senderID, String photoID, String text, Statement statement){
        statement.executeUpdate("INSERT INTO message (GroupID,SenderID,SentDate,PhotoMessage,Text) " +
                "VALUES ('"
                +groupID+"','"
                +senderID+"','"
                +LocalDate.now()+"','"
                +photoID+"','"
                +text
                +"')");
    }
    static void editMessage(int messageID,String text, String photoID, Statement statement){
        statement.executeUpdate("UPDATE message SET Text="+text+" PhotoMessage="+photoID"+ WHERE MessageID="+messageID);
    }
    static void deleteMessage(int messageID, Statement statement){
        statement.executeUpdate("DELETE FROM message WHERE MessageID="+messageID);
    }
    public static void main(String[] args){
        try {
            java.sql.Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc","root","");
            Statement statement = connection.createStatement();




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
            //System.out.println("nohr");
        }
    }
}
