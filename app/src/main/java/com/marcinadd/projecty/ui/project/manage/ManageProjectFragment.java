package com.marcinadd.projecty.ui.project.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.ManageProjectResponseListener;
import com.marcinadd.projecty.project.ProjectService;
import com.marcinadd.projecty.project.manage.fragment.AddProjectRoleDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.ChangeProjectNameDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.DeleteProjectDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.ProjectRoleFragment;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;

import java.io.Serializable;
import java.util.Objects;

public class ManageProjectFragment extends Fragment implements ManageProjectResponseListener {
    private TextView projectName;
    private ManageProjectViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_manage_project, container, false);
        projectName = view.findViewById(R.id.projectName);
        long projectId = ManageProjectFragmentArgs.fromBundle(getArguments()).getProjectId();

        model = ViewModelProviders.of(this).get(ManageProjectViewModel.class);
        model.getProject().observe(this, projectObserver());
        ProjectService.getInstance(getContext()).manageProject(projectId, this);

        ImageView changeNameButton = view.findViewById(R.id.change_name_button);
        changeNameButton.setOnClickListener(new ChangeNameButtonClick());

        ImageView addRoleButton = view.findViewById(R.id.add_role_button);
        addRoleButton.setOnClickListener(new AddRoleButtonClick());

        ImageView deleteProjectImageView = view.findViewById(R.id.delete_project_image_view);
        deleteProjectImageView.setOnClickListener(new DeleteProjectButtonClick());

        return view;
    }

    @Override
    public void onManageProjectResponse(ManageProject manageProject) {
        model.setProject(manageProject.getProject());
        model.setProjectRoles(manageProject.getProjectRoles());
        loadProjectRoleManageFragment();
    }

    private Observer<Project> projectObserver() {
        return new Observer<Project>() {
            @Override
            public void onChanged(Project project) {
                projectName.setText(project.getName());
            }
        };
    }

    void loadProjectRoleManageFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("projectRoles", (Serializable) model.getProjectRoles().getValue());
        ProjectRoleFragment projectRoleFragment = new ProjectRoleFragment();
        projectRoleFragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        transaction.replace(R.id.frameLayout2, projectRoleFragment);
        transaction.commit();
    }

    class ChangeNameButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", model.getProject().getValue());
            ChangeProjectNameDialogFragment fragment = new ChangeProjectNameDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(Objects.requireNonNull(getFragmentManager()), "TAG");
        }
    }

    class AddRoleButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", model.getProject().getValue());
            AddProjectRoleDialogFragment fragment = new AddProjectRoleDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(Objects.requireNonNull(getFragmentManager()), "TAG");
        }
    }

    class DeleteProjectButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", model.getProject().getValue());
            DeleteProjectDialogFragment fragment = new DeleteProjectDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(Objects.requireNonNull(getFragmentManager()), "TAG");

        }
    }
}
