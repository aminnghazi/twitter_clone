package controllers;

import enums.Dialog;
import models.DB;
import models.Message;
import views.View;

import java.time.Instant;
import java.util.ArrayList;

public class ChatViewController {
    public Dialog sendMessage(String senderID, String receiverID, String text , String image){
        if (text == null || text.equals(""))
            return Dialog.EMPTY_TEXT;
        if (receiverID.contains("#"))
            return DB.addMessage(receiverID,senderID,image,text);
        String groupID;
        int order = senderID.compareTo(receiverID);
        if (order >= 0){
            groupID = "@" + senderID + "&" + receiverID;
        }
        else {
            groupID = "@" + receiverID + "&" + senderID;
        }
        return DB.addMessage(groupID, senderID,image,text);
    }
    public ArrayList<Message> getMessages(String receiverID, String senderID){
        String groupId;
        if (receiverID.equals("-1"))
            groupId = "@" + View.getLoggedInUser().getUserName()+"&"+"-1";
        else{
            int order = senderID.compareTo(receiverID);
            if (order >= 0){
                groupId = "@" + senderID + "&" + receiverID;
            }
            else {
                groupId = "@" + receiverID + "&" + senderID;
            } 
        }
        // TODO: 8/6/2022 group 
//        else if (senderID == null)
//            validID = receiverID;
        return DB.getMessages(groupId);
    }

    public Dialog addGroup(String name,String image,String ownerID,ArrayList<String> users) {

        if (users.size()<2 )
            return Dialog.MINIMUM_MEMBER;
        if (name == null || name .equals(""))
            return Dialog.EMPTY_GROUP_NAME;
        String id = Instant.now().toEpochMilli() +"#"+ownerID;
        Dialog dialog = DB.createGroup(id,ownerID,name,true,image);
        if (dialog == Dialog.SUCCESS) {
            DB.addMember(id,View.getLoggedInUser().getUserName(),true);
            for (String user : users) {
                DB.addMember(id, user, false);
            }
        }
            return dialog;
    }
}
