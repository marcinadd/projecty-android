package com.marcinadd.projecty.ui.project.manage;

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
import com.marcinadd.projecty.project.model.ProjectRole;

import java.util.ArrayList;
import java.util.List;

public class ProjectRoleFragment extends Fragment {
    private MyProjectRoleRecyclerViewAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_role_list, container, false);

        List<Role> projectRoles = (List<Role>) getArguments().getSerializable("projectRoles");

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            adapter = new MyProjectRoleRecyclerViewAdapter(projectRoles);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }

    void updateRolesInRecyclerViewAdapter(List<ProjectRole> roles) {
        adapter.updateRoles(new ArrayList<>(roles));
    }


    @Override
    public void onDetach() {
        super.onDetach();
        adapter = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Role item);
    }
}
