package com.marcinadd.projecty.chat;

import com.marcinadd.projecty.project.model.User;

import java.io.Serializable;
import java.util.Date;

public class ChatMessage implements Serializable {
    private Long id;
    private User sender;
    private User recipient;
    private Date sendDate;
    private Date seenDate;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public String getText() {
        return text;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getSeenDate() {
        return seenDate;
    }

    public void setSeenDate(Date seenDate) {
        this.seenDate = seenDate;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "id=" + id +
                ", sender=" + sender +
                ", recipient=" + recipient +
                ", sendDate=" + sendDate +
                ", seenDate=" + seenDate +
                ", text='" + text + '\'' +
                '}';
    }
}
