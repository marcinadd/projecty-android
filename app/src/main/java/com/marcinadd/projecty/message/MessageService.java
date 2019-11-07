package com.marcinadd.projecty.message;

import android.content.Context;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.message.listener.MessageListListener;
import com.marcinadd.projecty.message.listener.MessageListener;
import com.marcinadd.projecty.message.model.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MessageService {
    private static MessageService messageService;
    private ApiMessage apiMessage;

    private MessageService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiMessage = retrofit.create(ApiMessage.class);
    }

    public static MessageService getInstance(Context context) {
        if (messageService == null) {
            messageService = new MessageService(context);
        }
        return messageService;
    }

    public void getReceivedMessages(final MessageListListener listener) {
        apiMessage.getReceivedMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful())
                    listener.onMessageListResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void getSentMessages(final MessageListListener listener) {
        apiMessage.getSentMessages().enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful())
                    listener.onMessageListResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void getMessage(final long messageId, final MessageListener listener) {
        apiMessage.getMessage(messageId).enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    listener.onMessageGetResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {

            }
        });
    }
}
