package com.marcinadd.projecty.helper;

import android.content.Context;

import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ChatMessageProjection;
import com.marcinadd.projecty.chat.ui.model.ChatUser;
import com.marcinadd.projecty.chat.ui.model.Dialog;
import com.marcinadd.projecty.chat.ui.model.Message;

import java.util.ArrayList;

public class DialogHelper {

    public static Dialog createDialogFromChatMessageProjection(ChatMessageProjection chatMessageProjection, Context context) {
        Message lastMessage = createMessageFromChatMessage(chatMessageProjection.getLastMessage(), context);
        ChatUser user = lastMessage.getUser();
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

    public static Message createMessageFromChatMessage(ChatMessage chatMessage, Context context) {
        String currentUserUsername = UserHelper.getCurrentUserUsername(context);
        ChatUser chatUser;
        if (chatMessage.getSender().getUsername().equals(currentUserUsername)) {
            chatUser = new ChatUser(chatMessage.getRecipient());
        } else {
            chatUser = new ChatUser(chatMessage.getSender());
        }
        return new Message(chatMessage.getId().toString(), chatMessage.getText(), chatUser, chatMessage.getSendDate());
    }

}
