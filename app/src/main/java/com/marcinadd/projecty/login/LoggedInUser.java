package com.marcinadd.projecty.login;

import com.google.gson.annotations.SerializedName;
import com.marcinadd.projecty.client.Token;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    @SerializedName("id")
    private String userId;

    @SerializedName("username")
    private String displayName;

    private Token token;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
}
