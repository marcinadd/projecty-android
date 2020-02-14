package com.marcinadd.projecty;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.marcinadd.projecty.chat.ChatHelper;
import com.marcinadd.projecty.chat.ChatMessage;
import com.marcinadd.projecty.chat.ChatMessageProjection;
import com.marcinadd.projecty.chat.ui.model.Dialog;
import com.marcinadd.projecty.project.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class ChatInstrumentedTests {

    private static final long MESSAGE_ID = 416L;
    private static final String MESSAGE_TEXT = "This is sample message text";

    private Context context;
    private ChatMessageProjection chatMessageProjection;
    private ChatMessage chatMessage;

    @Before
    public void init() {
        context = InstrumentationRegistry.getInstrumentation().getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString("username", "user").apply();
        User recipient = new User(34L, "admin");
        User sender = new User(37L, "user");
        chatMessage = new ChatMessage();
        chatMessage.setId(MESSAGE_ID);
        chatMessage.setRecipient(recipient);
        chatMessage.setSender(sender);
        chatMessage.setText(MESSAGE_TEXT);

        chatMessageProjection = new ChatMessageProjection();
        chatMessageProjection.setLastMessage(chatMessage);
        chatMessageProjection.setUnreadMessageCount(78);
    }

    @Test
    public void whenCreateDialogFromChatMessage_shouldReturnDialog() {
        Dialog dialog = ChatHelper.createDialogFromChatMessageProjection(chatMessageProjection, context);
        assertThat(dialog.getDialogName(), equalTo("admin"));
        assertThat(dialog.getLastMessage().getText(), equalTo(chatMessage.getText()));
        assertThat(dialog.getUnreadCount(), equalTo(78));
    }
}
