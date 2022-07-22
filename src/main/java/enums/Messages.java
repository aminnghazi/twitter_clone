package enums;

public enum Messages {
    NO_POSTS_TO_SHOW("Currently there are no posts to show here,You can start by following someone in explorer"),
    NO_COMMENTS_TO_SHOW("There are no comments here yet!"),
    WELCOME("");


    private String message;

    Messages(String dialog){
        this.message = dialog;
    }

    @Override
    public String toString() {
        return this.message;
    }
}
