package com.marcinadd.projecty.project.manage.fragment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.ApiProject;
import com.marcinadd.projecty.project.manage.fragment.ProjectRoleFragment.OnListFragmentInteractionListener;
import com.marcinadd.projecty.project.model.ProjectRole;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MyProjectRoleRecyclerViewAdapter extends RecyclerView.Adapter<MyProjectRoleRecyclerViewAdapter.ViewHolder> {

    private final List<ProjectRole> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;
    private ApiProject apiProject;

    public MyProjectRoleRecyclerViewAdapter(List<ProjectRole> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_projectrole, parent, false);
        context = parent.getContext();
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiProject = retrofit.create(ApiProject.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mRoleNameView.setText(mValues.get(position).getName());
        holder.mUsernameView.setText(mValues.get(position).getUser().getUsername());
        holder.mManagerSwitch.setChecked(mValues.get(position).getName().equals("ADMIN"));
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
        public final TextView mRoleNameView;
        public final TextView mUsernameView;
        public final Switch mManagerSwitch;
        public final ImageView mDeleteButtonIcon;
        public ProjectRole mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mRoleNameView = view.findViewById(R.id.role_name);
            mUsernameView = view.findViewById(R.id.username);
            mManagerSwitch = view.findViewById(R.id.manager_switch);
            mDeleteButtonIcon = view.findViewById(R.id.delete_role_icon);
            mManagerSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final boolean isChecked = mManagerSwitch.isChecked();
                    Log.e("Switched", String.valueOf(isChecked));
                    final String newRoleName = isChecked ? "ADMIN" : "USER";
                    apiProject.changeRole(mItem.getProject().getId(), mItem.getId(), newRoleName).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.code() == 200) {
                                mRoleNameView.setText(newRoleName);

                            } else {
                                mManagerSwitch.setChecked(!isChecked);
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            mManagerSwitch.setChecked(!isChecked);
                        }
                    });
                }
            });
            mDeleteButtonIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    apiProject.deleteUser(mItem.getProject().getId(), mItem.getUser().getId()).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                mValues.remove(mItem);
                                notifyItemRemoved(getAdapterPosition());
                                notifyItemRangeChanged(getAdapterPosition(), mValues.size());
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {

                        }
                    });
                }
            });
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mUsernameView.getText() + "'";
        }

    }
}
