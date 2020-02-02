package com.marcinadd.projecty.chat;

import com.marcinadd.projecty.model.Page;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiChat {
    @GET("chat")
    Call<List<ChatMessageProjection>> getChatHistory();

    @GET("chat/{username}")
    Call<Page<ChatMessage>> getChatMessagesForSpecifiedUsername(
            @Path("username") String username,
            @Query("offset") Integer offset,
            @Query("limit") Integer limit
    );
}
