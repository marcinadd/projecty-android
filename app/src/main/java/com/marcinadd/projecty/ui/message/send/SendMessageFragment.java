package com.marcinadd.projecty.ui.message.send;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.message.MessageService;
import com.marcinadd.projecty.message.model.Message;

import java.util.Objects;

public class SendMessageFragment extends Fragment implements RetrofitListener {

    private TextInputLayout textInputRecipient;
    private TextInputLayout textInputSubject;
    private TextInputLayout textInputContent;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_message, container, false);
        textInputRecipient = view.findViewById(R.id.message_send_recipient);
        textInputSubject = view.findViewById(R.id.message_send_subject);
        textInputContent = view.findViewById(R.id.message_send_content);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_message_send, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.send) {
            sendMessage();
        }
        return true;
    }

    private void sendMessage() {
        Message message = new Message();
        message.setRecipientUsername(getTextFromTextInputLayout(textInputRecipient));
        message.setTitle(getTextFromTextInputLayout(textInputSubject));
        message.setText(getTextFromTextInputLayout(textInputContent));
        MessageService.getInstance(getContext()).sendMessage(message, this);
    }

    private String getTextFromTextInputLayout(TextInputLayout textInputLayout) {
        return Objects.requireNonNull(textInputLayout.getEditText()).getText().toString();
    }

    @Override
    public void onResponseSuccess() {
        Snackbar.make(Objects.requireNonNull(getView()), getString(R.string.message_send), Snackbar.LENGTH_LONG).show();
        SendMessageFragmentDirections.ActionSendMessageFragmentToNavMessageSent action =
                SendMessageFragmentDirections.actionSendMessageFragmentToNavMessageSent();
        Navigation.findNavController(getView()).navigate(action);

    }

    @Override
    public void onResponseFailed() {
        Snackbar.make(Objects.requireNonNull(getView()), getString(R.string.sending_failed), Snackbar.LENGTH_LONG).show();
    }
}
