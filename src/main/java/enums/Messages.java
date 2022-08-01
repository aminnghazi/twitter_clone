package enums;

public enum Messages {
    NO_POSTS_TO_SHOW("Currently there are no posts to show here,You can start by following someone in explorer"),
    NO_COMMENTS_TO_SHOW("There are no comments here yet!"),
    WELCOME(""),
    FOLLOW("Unfollow"),
    UNFOLLOW("follow"),
    TEACHER("what is the name of your first grade teacher?"),
    PET("what is name of your childhood pet?"),
    FRIEND("who is your best friend");


    private String message;

    Messages(String dialog){
        this.message = dialog;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
