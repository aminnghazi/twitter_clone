package models;

import enums.Messages;
import javafx.scene.image.Image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public abstract class Media {



    private Timestamp dateCreated;
    private String senderUsername;
    private Image image;
    private String text;
    private String ID;
    private String parentID;

    protected Media(String senderUsername, Image image, String text,String parentID) {
        this.senderUsername = senderUsername;
        this.image = image;
        this.text = text;
        if (parentID != null)
            this.parentID = parentID;
        else
            this.parentID = "-1";


//        LocalDateTime currentTime = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//        this.dateCreated = currentTime.format(formatter);
    }


    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSenderUsername() {
        return senderUsername;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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


    @Override
    public String toString() {
        return "Media{" +
                "dateCreated=" + dateCreated +
                ", senderUsername='" + senderUsername + '\'' +
                ", image='" + image + '\'' +
                ", text='" + text + '\'' +
                ", ID='" + ID + '\'' +
                ", parentID='" + parentID + '\'' +
                '}';
    }
}
