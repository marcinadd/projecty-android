package com.marcinadd.projecty.chat.websocket;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.TokenHelper;
import com.marcinadd.projecty.exception.BlankTokenException;
import com.marcinadd.projecty.helper.ServerHelper;
import com.marcinadd.projecty.service.NotificationService;

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

    public static final String INTENT_FILTER_TAG_RECEIVED = "com.marcinadd.projecty.CHAT_MESSAGE_RECEIVED";
    public static final String INTENT_FILTER_TAG_TO_SEND = "com.marcinadd.projecty.CHAT_MESSAGE_TO_SEND";

    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private StompClient mStompClient;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            initChat();
            initBroadcastReceiver();
        } catch (Exception e) {
//            e.printStackTrace();
            Log.e("Connection Error", "error");
        }
    }

    public void initBroadcastReceiver() {
        intentFilter = new IntentFilter(INTENT_FILTER_TAG_TO_SEND);
        broadcastReceiver = toSendMessageBroadcastReceiver();
    }

    BroadcastReceiver toSendMessageBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                StompChatMessage message = (StompChatMessage) intent.getSerializableExtra("message");
                mStompClient.send("/app/secured/room", new Gson().toJson(message)).subscribe();
            }
        };
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
            StompChatMessage message = parseStompMessage(stompMessage);
            sendNotification(message);
            sendBroadcast(message);

        }, throwable -> Log.e("Error", throwable.getMessage()));

        compositeDisposable.add(topic);
    }

    public void sendNotification(StompChatMessage stompChatMessage) {
        NotificationService.sendNotification(
                stompChatMessage.getSender(),
                stompChatMessage.getText(),
                R.drawable.ic_mail,
                getApplicationContext()
        );
    }

    public void sendBroadcast(StompChatMessage stompChatMessage) {
        Intent intent = new Intent();
        intent.setAction(INTENT_FILTER_TAG_RECEIVED);
        intent.putExtra("message", stompChatMessage);
        sendBroadcast(intent);
    }

    public StompChatMessage parseStompMessage(StompMessage stompMessage) {
        return new Gson().fromJson(stompMessage.getPayload(), StompChatMessage.class);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (broadcastReceiver != null && intentFilter != null)
            getApplicationContext().registerReceiver(broadcastReceiver, intentFilter);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        if (mStompClient != null && mStompClient.isConnected())
            mStompClient.disconnect();
//        if (broadcastReceiver != null && )
//            getApplicationContext().unregisterReceiver(broadcastReceiver);
        broadcastReceiver = null;
        intentFilter = null;
    }
}
