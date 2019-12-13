package com.marcinadd.projecty.ui.team.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.ui.team.project.TeamProjectFragment.OnListFragmentInteractionListener;

import java.util.List;


public class MyTeamProjectRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamProjectRecyclerViewAdapter.ViewHolder> {

    private final List<Project> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTeamProjectRecyclerViewAdapter(List<Project> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_project_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
//        holder.mNameView.setText(mValues.get(position).id);
//        holder.mContentView.setText(mValues.get(position).content);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
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
        public final TextView mNameView;
        //        public final TextView mContentView;
        public Project mItem;
        private Button mTaskListButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = view.findViewById(R.id.team_project_name);
            mTaskListButton = view.findViewById(R.id.team_project_task_list_button);
            mTaskListButton.setOnClickListener(onTaskListButtonClick());
//            mContentView = (TextView) view.findViewById(R.id.content);
        }


        View.OnClickListener onTaskListButtonClick() {
            return v -> {
                TeamProjectFragmentDirections.ActionTeamProjectFragmentToTaskListActivity action =
                        TeamProjectFragmentDirections.actionTeamProjectFragmentToTaskListActivity();
                action.setProjectId(mItem.getId());
                Navigation.findNavController(mView).navigate(action);
            };
        }
    }
}
