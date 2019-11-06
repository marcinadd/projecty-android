package com.marcinadd.projecty.message;


import com.marcinadd.projecty.message.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiMessage {
    @GET("messages/receivedMessages")
    Call<List<Message>> getReceivedMessages();

    @GET("messages/{messageId}")
    Call<Message> getMessage(@Path("messageId") long messageId);
}