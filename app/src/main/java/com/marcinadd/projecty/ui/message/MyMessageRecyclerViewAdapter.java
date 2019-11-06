package com.marcinadd.projecty.ui.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.message.model.Message;
import com.marcinadd.projecty.ui.message.MessageListFragment.OnListFragmentInteractionListener;

import java.util.List;

import static com.marcinadd.projecty.message.helper.AvatarHelper.setAvatar;

public class MyMessageRecyclerViewAdapter extends RecyclerView.Adapter<MyMessageRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<Message> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyMessageRecyclerViewAdapter(List<Message> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mSenderView.setText(mValues.get(position).getSender().getUsername());
        holder.mDateView.setText(DateHelper.formatDate(mValues.get(position).getSendDate()));
        holder.mSubjectView.setText(String.valueOf(mValues.get(position).getTitle()));
        holder.mContentView.setText(mValues.get(position).getText());
        setAvatar(holder.mAvatarView, mValues.get(position).getSender().getUsername());
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mSenderView;
        public final TextView mDateView;
        public final TextView mSubjectView;
        public final TextView mContentView;
        //TODO Replace this with Avatar
        public final TextView mAvatarView;
        public Message mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mSenderView = view.findViewById(R.id.msglist_sender);
            mDateView = view.findViewById(R.id.msglist_date);
            mSubjectView = view.findViewById(R.id.msglist_subject);
            mContentView = view.findViewById(R.id.msglist_content);
            mAvatarView = view.findViewById(R.id.msglist_avatar);
            mView.setOnClickListener(itemClickListener());
        }

        View.OnClickListener itemClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MessageListFragmentDirections.ActionNavMessageToViewMessage action = MessageListFragmentDirections.actionNavMessageToViewMessage();
                    action.setMessageId(mItem.getId());
                    Navigation.findNavController(mView).navigate(action);
                }
            };
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
