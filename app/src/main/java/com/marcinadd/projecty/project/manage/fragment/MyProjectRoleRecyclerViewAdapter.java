package com.marcinadd.projecty.project.manage.fragment;

import android.view.View;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;

import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.model.MyRoleRecyclerViewAdapter;
import com.marcinadd.projecty.model.Role;
import com.marcinadd.projecty.project.ProjectService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyProjectRoleRecyclerViewAdapter extends MyRoleRecyclerViewAdapter {
    public MyProjectRoleRecyclerViewAdapter(List<Role> items, ProjectRoleFragment.OnListFragmentInteractionListener listener) {
        super(items, listener);
    }

    @Override
    public CompoundButton.OnCheckedChangeListener roleSwitchOnCheckedChange(ViewHolder viewHolder) {
        return (buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                String newRoleName = isChecked ? "ADMIN" : "USER";
                Map<String, String> fields = new LinkedHashMap<>();
                fields.put("name", newRoleName);
                ProjectService.getInstance(getContext()).updateProjectRole(viewHolder.mItem.getId(), fields, roleChangedListener(viewHolder, newRoleName, isChecked));
            }
        };
    }

    @Override
    public View.OnClickListener deleteRoleOnClickListener(ViewHolder viewHolder) {
        return v -> {
            ProjectService.getInstance(getContext()).deleteProjectRole(viewHolder.mItem.getId(), deleteRoleListener(viewHolder));
        };
    }


    private RetrofitListener<Void> roleChangedListener(ViewHolder viewHolder, String newRoleName, boolean isChecked) {
        return new RetrofitListener<Void>() {
            @Override
            public void onResponseSuccess(Void response, @Nullable String TAG) {
                viewHolder.mRoleNameView.setText(newRoleName);
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {
                viewHolder.mManagerSwitch.setChecked(!isChecked);
            }
        };
    }

    private RetrofitListener<Void> deleteRoleListener(ViewHolder viewHolder) {
        return new RetrofitListener<Void>() {
            @Override
            public void onResponseSuccess(Void response, @Nullable String TAG) {
                getmValues().remove(viewHolder.mItem);
                notifyItemRemoved(viewHolder.getAdapterPosition());
                notifyItemRangeChanged(viewHolder.getAdapterPosition(), getmValues().size());
            }

            @Override
            public void onResponseFailed(@Nullable String TAG) {

            }
        };
    }

}
