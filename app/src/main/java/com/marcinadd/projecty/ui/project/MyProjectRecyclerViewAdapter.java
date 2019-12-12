package com.marcinadd.projecty.ui.project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.model.Roles;
import com.marcinadd.projecty.project.model.ProjectRole;
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.ui.project.ProjectListFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.Map;

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
                .inflate(R.layout.fragment_project_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Context context = holder.mView.getContext();
        holder.mItem = mValues.get(position);
        //holder.mRoleNameView.setText(mValues.get(position).id);
        holder.mContentView.setText(mValues.get(position).getProject().getName());
        Map<TaskStatus, Long> summary = mValues.get(position).getProject().getTaskSummary();
        holder.mTodoView.setText(context.getString(R.string.to_do_number, summary.get(TaskStatus.TO_DO)));
        holder.mInProgressView.setText(context.getString(R.string.in_progress_number, summary.get(TaskStatus.IN_PROGRESS)));
        holder.mDoneView.setText(context.getString(R.string.done_number, summary.get(TaskStatus.DONE)));
        if (holder.mItem.getName() != Roles.ADMIN) {
            holder.manageProject.setEnabled(false);
        }

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
        final TextView mTodoView;
        final TextView mInProgressView;
        final TextView mDoneView;
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
            mTodoView = view.findViewById(R.id.project_list_to_do);
            mInProgressView = view.findViewById(R.id.project_list_in_progress);
            mDoneView = view.findViewById(R.id.project_list_done);
            taskList = view.findViewById(R.id.team_project_task_list_button);
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
            return v -> {
                ProjectFragmentDirections.ActionNavProjectToTaskListActivity action = ProjectFragmentDirections.actionNavProjectToTaskListActivity();
                action.setProjectId(mItem.getProject().getId());
                Navigation.findNavController(mView).navigate(action);
            };
        }

        View.OnClickListener manageProjectListener() {
            return v -> {
                ProjectFragmentDirections.ActionNavProjectToManageProjectFragment action = ProjectFragmentDirections.actionNavProjectToManageProjectFragment();
                action.setProjectId(mItem.getProject().getId());
                Navigation.findNavController(mView).navigate(action);
            };
        }
    }
}
