package com.marcinadd.projecty.message.model;

import com.marcinadd.projecty.project.model.User;

import java.util.Date;

public class Message {

    private Long id;

    private User sender;

    private User recipient;

    private Date sendDate;

    private Date seenDate;

    private String title;

    private String text;

    private String recipientUsername;

    public Long getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public Date getSeenDate() {
        return seenDate;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }
}
