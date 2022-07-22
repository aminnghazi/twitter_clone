package views;

import models.User;

import java.util.Scanner;

public abstract class View {
    private static final Scanner scanner = new Scanner(System.in);
    private static User loggedInUser = null;

    protected static Scanner getScanner(){//common scanner in all of the views
        return View.scanner;
    }

    public static void setLoggedInUser(User loggedInUser) {
        View.loggedInUser = loggedInUser;
    }

    public static User getLoggedInUser() {
        return View.loggedInUser;
    }
    protected String getInput(){//getting inputs from user
        return View.getScanner().nextLine().trim();
    }
    protected String getInput(String dialog){//getting inputs from user and showing a message
        System.out.println(dialog + ":");
        return View.getScanner().nextLine().trim();
    }

    protected String getChoice(){ //getting choice options from user
        return View.getScanner().nextLine().trim().toLowerCase();
    }

    public abstract void run();
    public abstract void showOptions();

}
