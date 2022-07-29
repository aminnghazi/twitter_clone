package models;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public abstract class Media {



    private Date dateCreated;
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
         LocalDate localDate= LocalDate.now();
        // TODO: 7/29/2022 change to right date
        dateCreated = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        this.dateCreated = currentTime.format(formatter);
    }


    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
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

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }
}
