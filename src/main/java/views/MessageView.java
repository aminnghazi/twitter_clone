package views;

import enums.Dialog;

public class MessageView extends View{

    @Override
    public void run() {
        showOptions();
        String chosenOption= getChoice();
        switch (chosenOption){
            case "1":
            case "see posts":
                this.seeChats();// TODO: 7/21/2022 replace all chats with only ones they follow
                break;
            case "2":
            case "add new chat":
                this.addNewChat();
                break;
            case"3":
            case"create group":
                this.createGroup();
                break;
            case "4":
            case "exit":
                return;
            default:
                System.out.println(Dialog.INVALID_CHOICE);
        }
        run();//loop

    }

    private void createGroup() {

    }

    private void addNewChat() {
    }

    private void seeChats() {
    }

    @Override
    public void showOptions() {
        System.out.println("choose an option to continue:");
        System.out.println("1. see chats");
        System.out.println("2. add new chat");
        System.out.println("3. create group");
        System.out.println("4. exit");
    }
}
