package models;

public class BusinessUser extends User{
    public BusinessUser(String userName , String passWord ,String securityQuestion,String securityAnswer){
        super(userName,passWord,securityQuestion,securityAnswer);
        setIsBusiness(true);
    }

}
