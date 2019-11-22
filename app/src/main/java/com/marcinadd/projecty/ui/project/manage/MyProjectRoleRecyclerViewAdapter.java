package com.marcinadd.projecty.ui.project.manage;

import android.view.View;
import android.widget.CompoundButton;

import com.marcinadd.projecty.model.MyRoleRecyclerViewAdapter;
import com.marcinadd.projecty.model.Role;
import com.marcinadd.projecty.project.ProjectService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyProjectRoleRecyclerViewAdapter extends MyRoleRecyclerViewAdapter {
    public MyProjectRoleRecyclerViewAdapter(List<Role> items) {
        super(items);
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
}
