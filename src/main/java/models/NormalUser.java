package models;

public class NormalUser extends User{
    public NormalUser(String userName , String password , String securityQuestion,String securityAnswer){
        super(userName , password, securityQuestion,securityAnswer);
        setIsBusiness(false);
    }
}
