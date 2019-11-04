package com.marcinadd.projecty.ui.message;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.message.MessageService;
import com.marcinadd.projecty.message.listener.MessageListListener;
import com.marcinadd.projecty.message.model.Message;

import java.util.List;

public class MessageListFragment extends Fragment implements MessageListListener {
    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    public MessageListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message_list, container, false);
        MessageService.getInstance(getContext()).getReceivedMessages(this);
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        }
        return view;
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
        recyclerView.setAdapter(new MyMessageRecyclerViewAdapter(messages, mListener));
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Message message);
    }
}
