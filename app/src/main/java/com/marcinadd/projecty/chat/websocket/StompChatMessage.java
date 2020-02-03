package com.marcinadd.projecty.chat.websocket;

import java.io.Serializable;
import java.util.Date;

public class StompChatMessage implements Serializable {

    private String sender;
    private String recipient;
    private String text;
    private Date sendDate;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    @Override
    public String toString() {
        return "StompChatMessage{" +
                "sender='" + sender + '\'' +
                ", recipient='" + recipient + '\'' +
                ", text='" + text + '\'' +
                ", sendDate=" + sendDate +
                '}';
    }
}
