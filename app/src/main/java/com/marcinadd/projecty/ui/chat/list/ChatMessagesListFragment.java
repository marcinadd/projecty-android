package com.marcinadd.projecty.ui.chat.list;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.chat.ChatApiService;
import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ui.model.Message;
import com.marcinadd.projecty.helper.ChatHelper;
import com.marcinadd.projecty.helper.UserHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.model.Page;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatMessagesListFragment extends Fragment implements RetrofitListener<Page<ChatMessage>> {

    private MessagesList messagesList;
    private MessagesListAdapter<Message> adapter;
    private ChatMessagesListViewModel mViewModel;

    public static ChatMessagesListFragment newInstance() {
        return new ChatMessagesListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_messages_list, container, false);
        messagesList = view.findViewById(R.id.messagesList);
        adapter = new MessagesListAdapter<>(String.valueOf(UserHelper.getCurrentUserId(getContext())), null);
        messagesList.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChatMessagesListViewModel.class);
        String username = ChatMessagesListFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getUsername();
        ChatApiService.getInstance(getContext()).getChatMessagesForSpecifiedUsername(username, this);
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
}
