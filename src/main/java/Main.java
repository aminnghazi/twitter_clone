import models.DB;
import views.LoginPage;

public class Main {
    public static void main(String[] args) {
        System.out.println(DB.start());
        LoginPage loginPage = new LoginPage();
        loginPage.run();
    }
}
