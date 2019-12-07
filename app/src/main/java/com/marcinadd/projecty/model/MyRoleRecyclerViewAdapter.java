package com.marcinadd.projecty.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;

import java.util.List;

public abstract class MyRoleRecyclerViewAdapter extends RecyclerView.Adapter<MyRoleRecyclerViewAdapter.ViewHolder> {

    private List<Role> mValues;
    private Context context;
    private String currentUserUsername;

    public MyRoleRecyclerViewAdapter(List<Role> items) {
        mValues = items;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_role_list_element, parent, false);
        context = parent.getContext();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        currentUserUsername = sharedPreferences.getString("username", "");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mRoleNameView.setText(mValues.get(position).getName().toString());
        holder.mUsernameView.setText(mValues.get(position).getUser().getUsername());
        Roles name = mValues.get(position).getName();
        holder.mManagerSwitch.setChecked(name == Roles.MANAGER || name == Roles.ADMIN);
        if (holder.mItem.getUser().getUsername().equals(currentUserUsername)) {
            holder.mManagerSwitch.setEnabled(false);
            holder.mDeleteButtonIcon.setEnabled(false);
        }
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

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addRole(Role role) {
        notifyItemInserted(mValues.size() - 1);
    }

    public void updateRoles(List<Role> roles) {
        mValues = roles;
        notifyDataSetChanged();
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

    public RetrofitListener<Void> roleChangedListener(ViewHolder viewHolder, String newRoleName, boolean isChecked) {
        return new RetrofitListener<Void>() {
            @Override
            public void onResponseSuccess(Void response, @Nullable String TAG) {
                viewHolder.mRoleNameView.setText(newRoleName);
                viewHolder.mItem.setName(Roles.valueOf(newRoleName));
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {
                viewHolder.mManagerSwitch.setChecked(!isChecked);
            }
        };
    }

    public RetrofitListener<Void> deleteRoleListener(ViewHolder viewHolder) {
        return new RetrofitListener<Void>() {
            @Override
            public void onResponseSuccess(Void response, @Nullable String TAG) {
                int index = mValues.indexOf(viewHolder.mItem);
                mValues.remove(viewHolder.mItem);
                notifyItemRemoved(index);
                notifyItemRangeChanged(index, mValues.size());
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }
}
