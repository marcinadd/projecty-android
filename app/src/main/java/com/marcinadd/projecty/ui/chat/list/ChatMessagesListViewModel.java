package com.marcinadd.projecty.ui.chat.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.chat.ui.model.Message;

import java.util.List;

public class ChatMessagesListViewModel extends ViewModel {
    MutableLiveData<List<Message>> mMessages;

    public ChatMessagesListViewModel() {
        mMessages = new MutableLiveData<>();
    }

    public LiveData<List<Message>> getMessages() {
        return mMessages;
    }

    public void setMessages(List<Message> messages) {
        mMessages.setValue(messages);
    }


}
