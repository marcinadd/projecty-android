package com.marcinadd.projecty.message.listener;

import com.marcinadd.projecty.message.model.Message;

public interface MessageListener {
    void onMessageGetResponse(Message message);
}
