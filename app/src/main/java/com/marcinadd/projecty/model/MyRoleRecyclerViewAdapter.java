package com.marcinadd.projecty.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.project.manage.fragment.ProjectRoleFragment.OnListFragmentInteractionListener;

import java.util.List;

public abstract class MyRoleRecyclerViewAdapter extends RecyclerView.Adapter<MyRoleRecyclerViewAdapter.ViewHolder> {

    private final List<Role> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;

    public MyRoleRecyclerViewAdapter(List<Role> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_projectrole, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mRoleNameView.setText(mValues.get(position).getName().toString());
        holder.mUsernameView.setText(mValues.get(position).getUser().getUsername());
        Roles name = mValues.get(position).getName();
        holder.mManagerSwitch.setChecked(name == Roles.MANAGER || name == Roles.ADMIN);
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public CompoundButton.OnCheckedChangeListener roleSwitchOnCheckedChange(ViewHolder viewHolder) {
        return (buttonView, isChecked) -> {

        };
    }

    public View.OnClickListener deleteRoleOnClickListener(ViewHolder viewHolder) {
        return v -> {

        };
    }

    public Context getContext() {
        return context;
    }

    public List<Role> getmValues() {
        return mValues;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mRoleNameView;
        public final TextView mUsernameView;
        public final Switch mManagerSwitch;
        public final ImageView mDeleteButtonIcon;
        public Role mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRoleNameView = view.findViewById(R.id.role_name);
            mUsernameView = view.findViewById(R.id.username);
            mManagerSwitch = view.findViewById(R.id.manager_switch);
            mDeleteButtonIcon = view.findViewById(R.id.delete_role_icon);
            mManagerSwitch.setOnCheckedChangeListener(roleSwitchOnCheckedChange(this));
            mDeleteButtonIcon.setOnClickListener(deleteRoleOnClickListener(this));
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUsernameView.getText() + "'";
        }
    }
}
