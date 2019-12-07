package com.marcinadd.projecty.task.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.TaskStatusChangedListener;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.ui.task.TaskFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyTaskRecyclerViewAdapter extends RecyclerView.Adapter<MyTaskRecyclerViewAdapter.ViewHolder> {
    private final List<Task> mValues;
    private final long projectId;
    private final OnListFragmentInteractionListener mListener;
    private TaskStatusChangedListener taskStatusChangedListener;

    public MyTaskRecyclerViewAdapter(List<Task> tasks, long projectId, OnListFragmentInteractionListener listener, TaskStatusChangedListener taskStatusChangedListener) {
        mValues = tasks;
        this.projectId = projectId;
        mListener = listener;
        this.taskStatusChangedListener = taskStatusChangedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_task_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        TaskRecyclerViewHelper.adjustRecyclerViewItemToTaskStatus(holder, mValues.get(position), projectId, taskStatusChangedListener);
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

    public void removeItem(Task task) {
        int positon = mValues.indexOf(task);
        mValues.remove(task);
        notifyItemRemoved(positon);
        notifyItemRangeChanged(positon, mValues.size());
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
