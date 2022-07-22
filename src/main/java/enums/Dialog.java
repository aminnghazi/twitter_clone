package enums;

public enum Dialog {
    INVALID_CHOICE("Invalid choice"),
    SUCCESS("Operation done successfully"),
    WRONG_CREDENTIALS("Wrong credentials"),
    MISMATCH_PASSWORD("Password and repeated password are not the same"),
    SHORT_PASSWORD("Password must be at least 8 characters long"),
    LONG_PASSWORD("Password must be at most 15 characters long"),
    USER_EXIST("UserName already exists"),
    USER_CREATION_FAILED("User creation failed with unknown error"),
    USER_CREATED("User created successfully"),
    USER_DOES_NOT_EXIST("User doesn not exist"),
    WRONG_ANSWER("wrong answer to security question"),
    OPERATION_FAILED("operation failed with unknown error"),
    EMPTY_TEXT("Posts text can not be empty"),
    MESSAGE_LIKED("you liked this message"),
    MESSAGE_LIKE_REMOVED("You removed your like"),
    COMMENT_ADDED_SUCCESSFULLY("Comment added successfully");
    private String dialog;

    Dialog(String dialog){
        this.dialog = dialog;
    }

    @Override
    public String toString() {
        return this.dialog;
    }
}
