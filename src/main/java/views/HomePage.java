package views;

import enums.Dialog;

public class HomePage extends View{
    //this class has no controller for now.Currently, it only opens other pages
    @Override
    public void run() {
        this.showOptions();
        // TODO: 7/20/2022 at phase 2:setting and posts and suggestion will be shown simultaneously
        String chosenOption = getChoice();
        switch (chosenOption){
            case "1":
            case "posts":
                PostsView postsView = new PostsView();
                postsView.run();
                break;
            case "2":
            case "settings":
                SettngsView settngsView = new SettngsView();
                settngsView.run();
                break;
            case "3":
            case "suggestions":
                SuggestionsView suggestionsView = new SuggestionsView();
                suggestionsView.run();
                break;
            case "4":
            case "message":

            case "5":
            case "exit":
                return;
            default:
                System.out.println(Dialog.INVALID_CHOICE);
                run();// TODO: 7/21/2022 delete loop in phase 2 
        }
        run();
    }

    @Override
    public void showOptions() {
        System.out.println("Choose an option to continue:");
        System.out.println("1. Posts");
        System.out.println("2. settings");
        System.out.println("3. suggestions");
        System.out.println("4. exit");
    }
}
