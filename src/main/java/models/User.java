package models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class User {

    private final String Date_created;
    private String userName;
    private String password;// TODO: 7/18/2022 add bio 
    private String profilePicture;
//    private String bio;
    private boolean isBusiness;
    private String securityQuestion;
    private String securityAnswer;


    public User(String userName, String password, String securityQuestionType, String securityQuestionAnswer){
        this.userName=userName;
        this.password=password;
        this.securityQuestion=securityQuestionType;
        this.securityAnswer =securityQuestionAnswer;
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd : HH:mm:ss");
        Date_created = currentTime.format(formatter);
        DB.allUsers.add(this);// TODO: 7/18/2022 replace with data base
    }

    public static User getUserByUserName(String userName){//may return null
        // TODO: 7/18/2022 replace with data base code
        for (User user : DB.allUsers) {
            if (user.userName.equals(userName))
                return user;
        }
        return null;
    }

    public String getDate_created() {
        return Date_created;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setIsBusiness(boolean business) {
        isBusiness = business;
    }

    public boolean isBusiness() {
        return isBusiness;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
