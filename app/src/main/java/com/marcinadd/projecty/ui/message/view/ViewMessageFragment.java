package com.marcinadd.projecty.ui.message.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.message.MessageService;
import com.marcinadd.projecty.message.listener.MessageListener;
import com.marcinadd.projecty.message.model.Message;

import static com.marcinadd.projecty.message.helper.AvatarHelper.setAvatar;

public class ViewMessageFragment extends Fragment implements MessageListener {

    private ViewMessageFragmentViewModel mViewModel;

    private long messageId;

    private TextView messageSubjectTextView;

    private TextView messageSenderTextView;

    private TextView messageAvatarTextView;

    private TextView messageSendDateTextView;

    private TextView messageContentTextView;

    public static ViewMessageFragment newInstance() {
        return new ViewMessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_message, container, false);
        messageId = ViewMessageFragmentArgs.fromBundle(getArguments()).getMessageId();
        messageSubjectTextView = root.findViewById(R.id.msgview_subject);
        messageSenderTextView = root.findViewById(R.id.msgview_sender);
        messageAvatarTextView = root.findViewById(R.id.msgview_avatar);
        messageSendDateTextView = root.findViewById(R.id.msgview_date);
        messageContentTextView = root.findViewById(R.id.msgview_content);
        MessageService.getInstance(getContext()).getMessage(messageId, this);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ViewMessageFragmentViewModel.class);
        final Observer<Message> messageObserver = new MessageObserver();
        mViewModel.getMessage().observe(this, messageObserver);
    }

    @Override
    public void onMessageGetResponse(Message message) {
        mViewModel.getMessage().setValue(message);
    }


    class MessageObserver implements Observer<Message> {

        @Override
        public void onChanged(Message message) {
            messageSubjectTextView.setText(message.getTitle());
            messageSenderTextView.setText(message.getSender().getUsername());
            setAvatar(messageAvatarTextView, message.getSender().getUsername());
            messageSendDateTextView.setText(DateHelper.formatDate(message.getSendDate()));
            messageContentTextView.setText(message.getText());
        }
    }
}
