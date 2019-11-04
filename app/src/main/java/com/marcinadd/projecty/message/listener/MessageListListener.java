package com.marcinadd.projecty.message.listener;

import com.marcinadd.projecty.message.model.Message;

import java.util.List;

public interface MessageListListener {
    void onMessageListResponse(List<Message> messages);
}
