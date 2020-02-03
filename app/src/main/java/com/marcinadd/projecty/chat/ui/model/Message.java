package com.marcinadd.projecty.chat.ui.model;

import androidx.annotation.Nullable;

import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.MessageContentType;

import java.io.Serializable;
import java.util.Date;

public class Message implements IMessage, MessageContentType.Image, Serializable {
    private String id;
    private String text;
    private ChatUser chatUser;
    private Date date;

    public Message(String id, String text, ChatUser chatUser, Date sendDate) {
        this.id = id;
        this.text = text;
        this.chatUser = chatUser;
        this.date = sendDate;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public ChatUser getUser() {
        return chatUser;
    }

    @Override
    public Date getCreatedAt() {
        return date;
    }

    @Nullable
    @Override
    public String getImageUrl() {
        return null;
    }
}
