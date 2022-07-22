package models;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Media {

    private final String dateCreated;
    private String senderUsername;
    private String image;
    private String text;
    private String ID;
    private String parentID;

    protected Media(String senderUsername, String image, String text,String parentID) {
        this.senderUsername = senderUsername;
        this.image = image;
        this.text = text;
        this.ID = senderUsername+ Long.toString(Instant.now().toEpochMilli());
        if (parentID != null)
            this.parentID = parentID;
        else
            this.parentID = "-1";
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        this.dateCreated = currentTime.format(formatter);
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public String getImage() {
        return image;
    }

    public String getText() {
        return text;
    }

    public String getID() {
        return ID;
    }

    public String getParentID() {
        return parentID;
    }
}
