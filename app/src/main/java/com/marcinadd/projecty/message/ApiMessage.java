package com.marcinadd.projecty.message;


import com.marcinadd.projecty.message.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiMessage {
    @GET("messages/receivedMessages")
    Call<List<Message>> getReceivedMessages();

    @GET("messages/sentMessages")
    Call<List<Message>> getSentMessages();

    @GET("messages/{messageId}")
    Call<Message> getMessage(@Path("messageId") long messageId);

    @POST("messages/sendMessage")
    Call<Void> sendMessage(@Body Message message);
}
