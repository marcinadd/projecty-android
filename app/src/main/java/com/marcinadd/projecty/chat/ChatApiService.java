package com.marcinadd.projecty.chat;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;

import java.util.List;

import retrofit2.Retrofit;

public class ChatApiService {
    private static ChatApiService chatApiService;
    private ApiChat apiChat;

    private ChatApiService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiChat = retrofit.create(ApiChat.class);
    }

    public static ChatApiService getInstance(Context context) {
        if (chatApiService == null) {
            chatApiService = new ChatApiService(context);
        }
        return chatApiService;
    }

    public void getChatMessageHistory(final RetrofitListener<List<ChatMessageProjection>> listener) {
        apiChat.getChatMessages().enqueue(new RetrofitCallback<>(listener));
    }
}
