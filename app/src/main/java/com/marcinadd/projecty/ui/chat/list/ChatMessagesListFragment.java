package com.marcinadd.projecty.ui.chat.list;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.chat.ChatApiService;
import com.marcinadd.projecty.chat.ChatHelper;
import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ui.model.ChatUser;
import com.marcinadd.projecty.chat.ui.model.Message;
import com.marcinadd.projecty.chat.utils.MyAvatarLoader;
import com.marcinadd.projecty.chat.websocket.ChatService;
import com.marcinadd.projecty.chat.websocket.StompChatMessage;
import com.marcinadd.projecty.client.MyOkHttpClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.model.Page;
import com.marcinadd.projecty.user.UserHelper;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ChatMessagesListFragment extends Fragment
        implements RetrofitListener<Page<ChatMessage>>,
        MessagesListAdapter.OnLoadMoreListener,
        MessageInput.InputListener {

    private MessagesListAdapter<Message> adapter;
    //    private ChatMessagesListViewModel mViewModel;
    private BroadcastReceiver broadcastReceiver;
    private IntentFilter intentFilter;

    private String recipientUsername;

    private String senderId;
    private String senderUsername;

    public static ChatMessagesListFragment newInstance() {
        return new ChatMessagesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_messages_list, container, false);

        MessagesList messagesList = view.findViewById(R.id.messagesList);
        MessageInput messageInput = view.findViewById(R.id.input);
        messageInput.setInputListener(this);

        senderId = String.valueOf(UserHelper.getCurrentUserId(getContext()));
        senderUsername = UserHelper.getCurrentUserUsername(getContext());

        adapter = new MessagesListAdapter<>(senderId, new MyAvatarLoader(getContext(), MyOkHttpClient.getInstance(getContext()).getClient()));
        adapter.setLoadMoreListener(this);

        messagesList.setAdapter(adapter);

        intentFilter = new IntentFilter(ChatService.INTENT_FILTER_TAG_RECEIVED);
        broadcastReceiver = broadcastReceiver();

        getContext().registerReceiver(broadcastReceiver, intentFilter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ChatMessagesListViewModel.class);
        recipientUsername = ChatMessagesListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getUsername();
        ChatApiService.getInstance(getContext()).getChatMessagesForSpecifiedUsername(recipientUsername, null, null, this);
    }

    @Override
    public void onResponseSuccess(Page<ChatMessage> response, @Nullable String TAG) {
        List<Message> messages = new ArrayList<>();
        for (ChatMessage chatMessage : response.getContent()
        ) {
            messages.add(ChatHelper.createMessageFromChatMessage(chatMessage));
        }
        adapter.addToEnd(messages, false);
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    public void onLoadMore(int page, int totalItemsCount) {
        ChatApiService.getInstance(getContext()).getChatMessagesForSpecifiedUsername(recipientUsername, page, null, this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        Message message = new Message(null, input.toString(), new ChatUser(senderId, null), new Date());
        adapter.addToStart(message, true);

        StompChatMessage stompChatMessage =
                new StompChatMessage(senderUsername, recipientUsername, input.toString());

        Intent intent = new Intent();
        intent.setAction(ChatService.INTENT_FILTER_TAG_TO_SEND);
        intent.putExtra("message", stompChatMessage);
        getContext().sendBroadcast(intent);
        return true;
    }

    BroadcastReceiver broadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                StompChatMessage stompChatMessage = (StompChatMessage) intent.getSerializableExtra("message");
                Message message = ChatHelper.createMessageFromStompChatMessage(stompChatMessage);
                adapter.addToStart(message, true);
            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(broadcastReceiver);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapter = null;
        recipientUsername = null;
        senderId = null;
        broadcastReceiver = null;
        intentFilter = null;
    }
}