package com.marcinadd.projecty.ui.team.manage;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.model.Role;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.ArrayList;
import java.util.List;

public class TeamRoleFragment extends Fragment {
    private MyTeamRoleRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role_list, container, false);

        List<Role> teamRoles = (List<Role>) getArguments().getSerializable("teamRoles");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MyTeamRoleRecyclerViewAdapter(teamRoles);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    void addRoleToRecyclerViewAdapater(Role role) {
        adapter.addRole(role);
    }

    void updateRolesInRecyclerViewAdapter(List<TeamRole> roles) {
        adapter.updateRoles(new ArrayList<>(roles));
    }
}
