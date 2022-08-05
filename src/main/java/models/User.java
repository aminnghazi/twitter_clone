package models;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class User {



    private Timestamp joinDate;
    private LocalDate birthDate;
    private String userName;
    private String password;// TODO: 7/18/2022 add bio
    private String profilePicture;
    private String securityQuestion;
    private String securityAnswer;
    private String firstName;
    private String lastName;



    public User(String userName, String password, String securityQuestionType, String securityQuestionAnswer, boolean isBusiness, String profilePicture,String firstName, String lastName){
        this.userName=userName;
        this.password=password;
        this.securityQuestion=securityQuestionType;
        this.securityAnswer =securityQuestionAnswer;
        this.isBusiness = isBusiness;
        this.birthDate = LocalDate.now();// TODO: 7/30/2022 change with real one
        this.firstName = firstName;
        this.lastName = lastName;
//        System.out.println(profilePicture);
        if (profilePicture.equals("null"))
            this.profilePicture ="-1";
        else
            this.profilePicture = profilePicture;
//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd : HH:mm:ss");
//        joinDate = currentTime.format(formatter);

    }
    public ArrayList<User> getSuggestedUsers(){
        return DB.getAllUsers();
    }

    private boolean isBusiness;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public void setBusiness(boolean business) {
        isBusiness = business;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
