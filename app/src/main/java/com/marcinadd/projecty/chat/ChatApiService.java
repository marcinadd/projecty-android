package com.marcinadd.projecty.chat;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.model.Page;

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
        apiChat.getChatHistory().enqueue(new RetrofitCallback<>(listener));
    }

    public void getChatMessagesForSpecifiedUsername(final String username, Integer offset, Integer limit, final RetrofitListener<Page<ChatMessage>> listener) {
        apiChat.getChatMessagesForSpecifiedUsername(username, offset, limit).enqueue(new RetrofitCallback<>(listener));
    }
}
