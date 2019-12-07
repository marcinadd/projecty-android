package com.marcinadd.projecty.ui.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.model.Roles;
import com.marcinadd.projecty.team.model.TeamRole;
import com.marcinadd.projecty.ui.team.TeamListFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyTeamRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamRecyclerViewAdapter.ViewHolder> {

    private final List<TeamRole> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTeamRecyclerViewAdapter(List<TeamRole> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team_list_element, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTeamNameView.setText(mValues.get(position).getTeam().getName());
        holder.mTeamRoleNameView.setText(mValues.get(position).getName().toString());

        if (holder.mItem.getName() != Roles.MANAGER) {
            holder.mManageTeamButton.setEnabled(false);
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
        public final TextView mTeamNameView;
        public final TextView mTeamRoleNameView;
        public TeamRole mItem;
        private Button mManageTeamButton;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamNameView = view.findViewById(R.id.team_list_team_name);
            mTeamRoleNameView = view.findViewById(R.id.team_list_role_name);
            mManageTeamButton = view.findViewById(R.id.team_list_manage_button);
            mManageTeamButton.setOnClickListener(manageTeamListener());
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeamRoleNameView.getText() + "'";
        }

        View.OnClickListener manageTeamListener() {
            return v -> {
                TeamFragmentDirections.ActionNavTeamToManageTeamFragment action = TeamFragmentDirections.actionNavTeamToManageTeamFragment();
                action.setTeamId(mItem.getTeam().getId());
                Navigation.findNavController(mView).navigate(action);
            };
        }
    }
}
