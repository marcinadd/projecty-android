package com.marcinadd.projecty.chat;

import android.content.Context;

import com.marcinadd.projecty.chat.ui.model.ChatUser;
import com.marcinadd.projecty.chat.ui.model.Dialog;
import com.marcinadd.projecty.chat.ui.model.Message;
import com.marcinadd.projecty.chat.websocket.StompChatMessage;
import com.marcinadd.projecty.user.UserHelper;

import java.util.ArrayList;

public class ChatHelper {

    public static Dialog createDialogFromChatMessageProjection(ChatMessageProjection chatMessageProjection, Context context) {
        Message lastMessage = createMessageFromChatMessage(chatMessageProjection.getLastMessage());
        ChatUser user = getOtherChatUser(chatMessageProjection.getLastMessage(), context);
        long unreadMessageCount = chatMessageProjection.getUnreadMessageCount();
        ArrayList<ChatUser> chatUsers = new ArrayList<>();
        chatUsers.add(user);
        return new Dialog(
                user.getId(),
                user.getName(),
                chatUsers,
                lastMessage,
                (int) unreadMessageCount);
    }

    public static ChatUser getOtherChatUser(ChatMessage chatMessage, Context context) {
        String currentUserUsername = UserHelper.getCurrentUserUsername(context);
        if (chatMessage.getSender().getUsername().equals(currentUserUsername)) {
            return new ChatUser(chatMessage.getRecipient());
        }
        return new ChatUser(chatMessage.getSender());
    }

    public static Message createMessageFromChatMessage(ChatMessage chatMessage) {
        ChatUser chatUser = new ChatUser(chatMessage.getSender());
        return new Message(chatMessage.getId().toString(), chatMessage.getText(), chatUser, chatMessage.getSendDate());
    }

    public static Message createMessageFromStompChatMessage(StompChatMessage stompChatMessage) {
        ChatUser chatUser = new ChatUser("OTHER", stompChatMessage.getSender());
        return new Message(null, stompChatMessage.getText(), chatUser, stompChatMessage.getSendDate());
    }
}