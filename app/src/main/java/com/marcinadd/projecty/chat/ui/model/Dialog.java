package com.marcinadd.projecty.chat.ui.model;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;
import java.util.List;

public class Dialog implements IDialog<Message> {
    private String id;
    private String dialogName;
    private Message lastMessage;
    private ArrayList<ChatUser> chatUsers;

    private int unreadCount;

    public Dialog(String id, String dialogName, ArrayList<ChatUser> chatUsers, Message lastMessage, int unreadCount) {
        this.id = id;
        this.dialogName = dialogName;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
        this.chatUsers = chatUsers;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogName;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public List<? extends IUser> getUsers() {
        return chatUsers;
    }

    @Override
    public Message getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(Message message) {

    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}
