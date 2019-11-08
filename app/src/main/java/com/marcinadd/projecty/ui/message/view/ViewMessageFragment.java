package com.marcinadd.projecty.ui.message.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.message.MessageService;
import com.marcinadd.projecty.message.MessageTypes;
import com.marcinadd.projecty.message.listener.MessageListener;
import com.marcinadd.projecty.message.model.Message;

import static com.marcinadd.projecty.message.helper.AvatarHelper.setAvatar;

public class ViewMessageFragment extends Fragment implements MessageListener {

    private ViewMessageFragmentViewModel mViewModel;

    private long messageId;

    private MessageTypes messageType;

    private TextView textViewSubject;
    private TextView textViewUser1;
    private TextView textViewUser2;
    private TextView textViewAvatar;
    private TextView textViewSendDate;
    private TextView textViewContent;

    private ImageView imageViewDirection;

    public static ViewMessageFragment newInstance() {
        return new ViewMessageFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_view_message, container, false);
        messageId = ViewMessageFragmentArgs.fromBundle(getArguments()).getMessageId();
        messageType = ViewMessageFragmentArgs.fromBundle(getArguments()).getType();
        textViewSubject = root.findViewById(R.id.msgview_subject);
        textViewUser1 = root.findViewById(R.id.msgview_user_1);
        textViewUser2 = root.findViewById(R.id.msgview_user_2);
        textViewAvatar = root.findViewById(R.id.msgview_avatar);
        textViewSendDate = root.findViewById(R.id.msgview_date);
        textViewContent = root.findViewById(R.id.msgview_content);
        imageViewDirection = root.findViewById(R.id.msgview_direction);
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
            String user1;
            String user2;
            if (messageType == MessageTypes.RECEIVED) {
                user1 = message.getSender().getUsername();
                user2 = message.getRecipient().getUsername();
            } else {
                user1 = message.getRecipient().getUsername();
                user2 = message.getSender().getUsername();
                imageViewDirection.setImageResource(R.drawable.ic_arrow_back);
            }
            textViewSubject.setText(message.getTitle());
            textViewUser1.setText(user1);
            textViewUser2.setText(user2);
            setAvatar(textViewAvatar, message.getSender().getUsername());
            textViewSendDate.setText(DateHelper.formatDate(message.getSendDate()));
            textViewContent.setText(message.getText());
        }
    }
}
