package com.marcinadd.projecty.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.client.TokenHelper;
import com.marcinadd.projecty.exception.BlankTokenException;
import com.marcinadd.projecty.helper.ServerHelper;

import java.util.LinkedHashMap;
import java.util.Map;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.StompMessage;

public class ChatService extends Service {
    private final String TAG = "StompChatClient";
    private final String SUBSCRIBE_URL = "/user/queue/specific-user";

    public static final String INTENT_FILTER_TAG = "com.marcinadd.projecty.CHAT_MESSAGE";

    private StompClient mStompClient;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initChat();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initChat() throws BlankTokenException {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        Context context = getApplicationContext();
        String url = ServerHelper.getServerURL(context);
        url = url.replace("https", "ws");
        url = url.replace("http", "ws");
        Map<String, String> connectHttpHeaders = new LinkedHashMap<>();
        connectHttpHeaders.put("Authorization", "Bearer " + TokenHelper.getAccessToken(context));
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP
                , url + "/secured/room/websocket", connectHttpHeaders);
        Disposable lifecycler = mStompClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
                    Log.i(TAG, "Stomp Connection Opened");
                    break;
                case ERROR:
                    Log.e(TAG, "Error ", lifecycleEvent.getException());
                    break;
                case CLOSED:
                    Log.i(TAG, "Stomp Connection Closed");
                    break;
                case FAILED_SERVER_HEARTBEAT:
                    Log.e(TAG, "Failed Server Heartbeat");
                    break;
            }
        });
        if (!mStompClient.isConnected()) {
            mStompClient.connect();
        }

        compositeDisposable.add(lifecycler);

        Disposable topic = mStompClient.topic(SUBSCRIBE_URL).subscribe(stompMessage -> {
            ChatMessage message = parseStompMessage(stompMessage);
            sendNotification(message);
            sendBroadcast(message);

        }, throwable -> Log.e("Error", throwable.getMessage()));

        compositeDisposable.add(topic);
    }

    public void sendNotification(ChatMessage chatMessage) {
        NotificationService.sendNotification(
                chatMessage.getSender().getUsername(),
                chatMessage.getText(),
                R.drawable.ic_mail,
                getApplicationContext()
        );
    }

    public void sendBroadcast(ChatMessage chatMessage) {
        Intent intent = new Intent();
        intent.setAction(INTENT_FILTER_TAG);
        intent.putExtra("message", chatMessage);
        sendBroadcast(intent);
    }

    public ChatMessage parseStompMessage(StompMessage stompMessage) {
        Gson gson = new Gson();
        return gson.fromJson(stompMessage.getPayload(), ChatMessage.class);
    }


    @Override
    public void onDestroy() {
        if (mStompClient != null && mStompClient.isConnected())
            mStompClient.disconnect();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
