package com.marcinadd.projecty;

import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.service.ChatService;

import org.junit.Test;

import ua.naiksoftware.stomp.dto.StompMessage;

import static org.junit.Assert.assertEquals;

public class ChatServiceUnitTests {
    private static final String ADMIN = "admin";
    private static final String USER = "user";
    private static final String TEXT = "text with spaces";

    @Test
    public void whenParseStompMessage_shouldReturnChatMessage() {
        ChatService chatService = new ChatService();
        StompMessage stompMessage = new StompMessage(null, null,
                "{\"sender\":\"" + ADMIN + "\",\"recipient\":\"" + USER + "\",\"text\":\"" + TEXT + "\",\"sendDate\":\"2020-01-30T16:27:56.531+0000\"}");
        ChatMessage chatMessage = chatService.parseStompMessage(stompMessage);
        assertEquals(chatMessage.getRecipient(), "user");
        assertEquals(chatMessage.getSender(), "admin");
        assertEquals(chatMessage.getText(), TEXT);
    }
}
