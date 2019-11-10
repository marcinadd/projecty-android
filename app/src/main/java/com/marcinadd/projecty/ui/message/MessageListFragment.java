package com.marcinadd.projecty.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.message.MessageService;
import com.marcinadd.projecty.message.MessageTypes;
import com.marcinadd.projecty.message.listener.MessageListListener;
import com.marcinadd.projecty.message.model.Message;

import java.util.List;

public class MessageListFragment extends Fragment implements MessageListListener {

    private OnListFragmentInteractionListener mListener;
    private MessageTypes type;

    private RecyclerView recyclerView;
    private View mView;

    public MessageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_message_list, container, false);
        type = MessageListFragmentArgs.fromBundle(getArguments()).getType();
        if (type == MessageTypes.RECEIVED) {
            MessageService.getInstance(getContext()).getReceivedMessages(this);
        } else {
            MessageService.getInstance(getContext()).getSentMessages(this);
        }
        Context context = mView.getContext();
        recyclerView = mView.findViewById(R.id.list_messages);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        FloatingActionButton fab = mView.findViewById(R.id.fab);
        fab.setOnClickListener(new OnFabClicked());
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onMessageListResponse(List<Message> messages) {
        recyclerView.setAdapter(new MyMessageRecyclerViewAdapter(messages, type, mListener));
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Message message);
    }

    class OnFabClicked implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            NavDirections directions = MessageListFragmentDirections.actionNavMessageListToSendMessageFragment();
            Navigation.findNavController(mView).navigate(directions);
        }
    }
}
