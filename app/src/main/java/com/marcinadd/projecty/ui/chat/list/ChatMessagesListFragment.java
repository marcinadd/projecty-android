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
import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ui.model.ChatUser;
import com.marcinadd.projecty.chat.ui.model.Message;
import com.marcinadd.projecty.chat.websocket.ChatService;
import com.marcinadd.projecty.chat.websocket.StompChatMessage;
import com.marcinadd.projecty.helper.ChatHelper;
import com.marcinadd.projecty.helper.UserHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.model.Page;
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

    private String username;
    private String senderId;

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

        adapter = new MessagesListAdapter<>(senderId, null);
        adapter.setLoadMoreListener(this);

        messagesList.setAdapter(adapter);

        intentFilter = new IntentFilter(ChatService.INTENT_FILTER_TAG);
        broadcastReceiver = broadcastReceiver();


        getContext().registerReceiver(broadcastReceiver, intentFilter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = ViewModelProviders.of(this).get(ChatMessagesListViewModel.class);
        username = ChatMessagesListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getUsername();
        ChatApiService.getInstance(getContext()).getChatMessagesForSpecifiedUsername(username, null, null, this);
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
        ChatApiService.getInstance(getContext()).getChatMessagesForSpecifiedUsername(username, page, null, this);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        adapter.addToStart(new Message(null, input.toString(), new ChatUser(senderId, null), new Date()), true);
        return true;
    }

    BroadcastReceiver broadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                StompChatMessage cstompChatMessage = (StompChatMessage) intent.getSerializableExtra("message");
                Message message = ChatHelper.createMessageFromStompChatMessage(cstompChatMessage);
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
        username = null;
        senderId = null;
        broadcastReceiver = null;
        intentFilter = null;
    }
}
