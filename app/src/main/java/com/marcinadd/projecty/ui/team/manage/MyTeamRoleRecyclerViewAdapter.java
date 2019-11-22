package com.marcinadd.projecty.ui.team.manage;

import android.view.View;
import android.widget.CompoundButton;

import com.marcinadd.projecty.model.MyRoleRecyclerViewAdapter;
import com.marcinadd.projecty.model.Role;
import com.marcinadd.projecty.model.Roles;
import com.marcinadd.projecty.team.TeamService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyTeamRoleRecyclerViewAdapter extends MyRoleRecyclerViewAdapter {
    public MyTeamRoleRecyclerViewAdapter(List<Role> items) {
        super(items);
    }

    @Override
    public CompoundButton.OnCheckedChangeListener roleSwitchOnCheckedChange(ViewHolder viewHolder) {
        return (buttonView, isChecked) -> {
            if (buttonView.isPressed()) {
                String newRoleName = String.valueOf(isChecked ? Roles.MANAGER : Roles.MEMBER);
                Map<String, String> fields = new LinkedHashMap<>();
                fields.put("name", newRoleName);
                TeamService.getInstance(getContext()).updateTeamRole(viewHolder.mItem.getId(), fields, roleChangedListener(viewHolder, newRoleName, isChecked));
            }
        };
    }

    @Override
    public View.OnClickListener deleteRoleOnClickListener(ViewHolder viewHolder) {
        return v -> {
            TeamService.getInstance(getContext()).deleteTeamRole(viewHolder.mItem.getId(), deleteRoleListener(viewHolder));
        };
    }
}
