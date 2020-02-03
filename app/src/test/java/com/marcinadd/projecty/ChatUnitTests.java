package com.marcinadd.projecty;

import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ChatMessageProjection;
import com.marcinadd.projecty.chat.ui.model.Message;
import com.marcinadd.projecty.chat.websocket.StompChatMessage;
import com.marcinadd.projecty.helper.ChatHelper;
import com.marcinadd.projecty.project.model.User;

import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ChatUnitTests {
    private static final long MESSAGE_ID = 416L;
    private static final String MESSAGE_TEXT = "This is sample message text";

    private ChatMessageProjection chatMessageProjection;

    @Before
    public void init() {
        User recipient = new User(34L, "admin");
        User sender = new User(37L, "user");
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setId(MESSAGE_ID);
        chatMessage.setRecipient(recipient);
        chatMessage.setSender(sender);
        chatMessage.setText(MESSAGE_TEXT);

        chatMessageProjection = new ChatMessageProjection();
        chatMessageProjection.setLastMessage(chatMessage);
        chatMessageProjection.setUnreadMessageCount(78);
    }


    @Test
    public void whenCreateMessageFromStompChatMessage_shouldReturnMessage() {
        Date date = new Date();
        StompChatMessage stompChatMessage = new StompChatMessage();
        stompChatMessage.setText(MESSAGE_TEXT);
        stompChatMessage.setSendDate(date);
        stompChatMessage.setSender("user");
        Message message = ChatHelper.createMessageFromStompChatMessage(stompChatMessage);
        assertThat(message.getText(), equalTo(MESSAGE_TEXT));
        assertThat(message.getCreatedAt(), equalTo(date));
        assertThat(message.getUser().getName(), equalTo("user"));
    }

    @Test
    public void whenCreateMessageFromChatMessage_shouldReturnChatMessage() {
        Message message = ChatHelper.createMessageFromChatMessage(chatMessageProjection.getLastMessage());
        assertThat(message.getId(), equalTo(String.valueOf(MESSAGE_ID)));
        assertThat(message.getUser().getName(), equalTo("user"));
        assertThat(message.getText(), equalTo(MESSAGE_TEXT));
    }

}
