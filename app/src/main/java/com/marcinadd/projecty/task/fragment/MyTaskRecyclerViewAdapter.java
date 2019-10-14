package com.marcinadd.projecty.task.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.task.fragment.TaskFragment.OnListFragmentInteractionListener;
import com.marcinadd.projecty.task.model.Task;

import java.util.List;

public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {
    private final List<Task> mValues;
    private final long projectId;
    private final OnListFragmentInteractionListener mListener;

    public MyTaskRecyclerViewAdapter(List<Task> tasks, long projectId, OnListFragmentInteractionListener listener) {
        mValues = tasks;
        this.projectId = projectId;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TaskRecyclerViewHelper.adjustRecyclerViewItemToTaskStatus(holder, mValues.get(position), projectId);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    public void addItem(Task task) {
        mValues.add(task);
        notifyItemInserted(mValues.size() - 1);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView date;
        public final TextView taskName;
        public Task mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            date = view.findViewById(R.id.date);
            taskName = view.findViewById(R.id.task_name);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + taskName.getText() + "'";
        }
    }
}
