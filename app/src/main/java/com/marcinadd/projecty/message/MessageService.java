package com.marcinadd.projecty.message;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.message.model.Message;

import java.util.List;

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

    public void getReceivedMessages(final RetrofitListener<List<Message>> listener) {
        apiMessage.getReceivedMessages().enqueue(new RetrofitCallback<>(listener));
    }

    public void getSentMessages(final RetrofitListener<List<Message>> listener) {
        apiMessage.getSentMessages().enqueue(new RetrofitCallback<>(listener));
    }

    public void getMessage(final long messageId, final RetrofitListener<Message> listener) {
        apiMessage.getMessage(messageId).enqueue(new RetrofitCallback<>(listener));
    }

    public void sendMessage(final Message message, final RetrofitListener<Void> listener) {
        apiMessage.sendMessage(message).enqueue(new RetrofitCallback<>(listener));
    }

    public void deleteMessage(final long messageId, final RetrofitListener<Void> listener) {
        apiMessage.deleteMessage(messageId).enqueue(new RetrofitCallback<>(listener));
    }
}
