package com.marcinadd.projecty.message;


import com.marcinadd.projecty.message.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiMessage {
    @GET("messages/receivedMessages")
    Call<List<Message>> getReceivedMessages();
}
