package com.marcinadd.projecty.chat.ui.model;

import com.marcinadd.projecty.project.model.User;
import com.stfalcon.chatkit.commons.models.IUser;

public class ChatUser implements IUser {

    private String id;
    private String name;

    public ChatUser(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ChatUser(User user) {
        this.id = String.valueOf(user.getId());
        this.name = user.getUsername();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return null;
    }
}
