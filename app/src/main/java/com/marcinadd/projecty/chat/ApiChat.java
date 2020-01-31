package com.marcinadd.projecty.chat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiChat {
    @GET("chat")
    Call<List<ChatMessageProjection>> getChatMessages();
}
