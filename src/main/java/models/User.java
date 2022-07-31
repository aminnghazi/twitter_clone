package models;

import java.time.LocalDate;

public class User {

    private LocalDate joinDate;
    private LocalDate birthDate;
    private String userName;
    private String password;// TODO: 7/18/2022 add bio
    private String profilePicture;
    private String securityQuestion;
    private String securityAnswer;
    private String firstName;
    private String lastName;



    public User(String userName, String password, String securityQuestionType, String securityQuestionAnswer, boolean isBusiness, String profilePicture){
        this.userName=userName;
        this.password=password;
        this.securityQuestion=securityQuestionType;
        this.securityAnswer =securityQuestionAnswer;
        this.isBusiness = isBusiness;
        this.joinDate = LocalDate.now();
        this.birthDate = LocalDate.now();// TODO: 7/30/2022 change with real one
        this.firstName = "name";// TODO: 7/30/2022
        this.lastName = "last Name";// TODO: 7/30/2022
        if (profilePicture == null)
            profilePicture = "-1";
        else this.profilePicture = profilePicture;
//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd : HH:mm:ss");
//        joinDate = currentTime.format(formatter);

    }

    private boolean isBusiness;


//    public static User getUserByUserName(String userName){//may return null
//        for (User user : DB.allUsers) {
//            if (user.userName.equals(userName))
//                return user;
//        }
//        return null;
//    }

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

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
