package com.marcinadd.projecty.project;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.project.ProjectFragment.OnListFragmentInteractionListener;
import com.marcinadd.projecty.project.manage.ManageProjectActivity;
import com.marcinadd.projecty.project.model.ProjectRole;
import com.marcinadd.projecty.task.TaskListActivity;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyProjectRecyclerViewAdapter extends RecyclerView.Adapter<MyProjectRecyclerViewAdapter.ViewHolder> {

    private final List<ProjectRole> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyProjectRecyclerViewAdapter(List<ProjectRole> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        //holder.mRoleNameView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).getProject().getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final Button taskList;
        public final Button manageProject;
        public final Context mContext;
        public ProjectRole mItem;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContext = view.getContext();
            mIdView = view.findViewById(R.id.role_name);
            mContentView = view.findViewById(R.id.username);
            taskList = view.findViewById(R.id.task_list);
            manageProject = view.findViewById(R.id.manage_project);
            setButtons();
        }

        private void setButtons() {
            taskList.setOnClickListener(taskListListener());
            manageProject.setOnClickListener(manageProjectListener());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        View.OnClickListener taskListListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, TaskListActivity.class);
                    intent.putExtra("projectId", mItem.getProject().getId());
                    mContext.startActivity(intent);
                }
            };
        }

        View.OnClickListener manageProjectListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, ManageProjectActivity.class);
                    intent.putExtra("projectId", mItem.getProject().getId());
                    mContext.startActivity(intent);
                }
            };
        }
    }
}
