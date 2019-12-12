package com.marcinadd.projecty.ui.project.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.ProjectService;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.ProjectRole;
import com.marcinadd.projecty.ui.project.manage.dialog.ChangeProjectNameDialogFragment;
import com.marcinadd.projecty.ui.project.manage.dialog.DeleteProjectDialogFragment;
import com.marcinadd.projecty.ui.project.manage.dialog.ProjectRoleAddDialogFragment;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class ManageProjectFragment extends Fragment
        implements RetrofitListener<ManageProject>,
        DeleteProjectDialogFragment.OnProjectDeletedListener, ProjectRoleAddDialogFragment.OnProjectRolesAddedListener {
    private TextView projectName;
    private ManageProjectViewModel mViewModel;
    private ProjectRoleFragment projectRoleFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_project_manage, container, false);
        projectName = view.findViewById(R.id.projectName);
        long projectId = ManageProjectFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getProjectId();
        ProjectService.getInstance(getContext()).manageProject(projectId, this);
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ManageProjectViewModel.class);
        mViewModel.getProject().observe(this, projectObserver());
        mViewModel.getProjectRoles().observe(this, projectRolesObserver());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_project_manage, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.name:
                changeName();
                break;
            case R.id.role_add:
                addRole();
                break;
            case R.id.delete:
                delete();
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResponseSuccess(ManageProject response, @Nullable String TAG) {
        mViewModel.setProject(response.getProject());
        mViewModel.setProjectRoles(response.getProjectRoles());
        loadProjectRoleManageFragment();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    private Observer<Project> projectObserver() {
        return project -> projectName.setText(project.getName());
    }

    private Observer<List<ProjectRole>> projectRolesObserver() {
        return projectRoles -> {
            if (projectRoleFragment != null) {
                projectRoleFragment.updateRolesInRecyclerViewAdapter(projectRoles);
            }
        };
    }

    private void loadProjectRoleManageFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("projectRoles", (Serializable) mViewModel.getProjectRoles().getValue());
        projectRoleFragment = new ProjectRoleFragment();
        projectRoleFragment.setArguments(bundle);
        FragmentTransaction transaction = Objects.requireNonNull(getFragmentManager()).beginTransaction();
        transaction.replace(R.id.frameLayout2, projectRoleFragment);
        transaction.commit();
    }

    private void changeName() {
        Bundle bundle1 = new Bundle();
        bundle1.putSerializable("project", mViewModel.getProject().getValue());
        ChangeProjectNameDialogFragment fragment = new ChangeProjectNameDialogFragment();
        fragment.setArguments(bundle1);
        fragment.show(Objects.requireNonNull(getChildFragmentManager()), "TAG");
    }


    private void addRole() {
            Bundle bundle1 = new Bundle();
        bundle1.putSerializable("project", mViewModel.getProject().getValue());
        ProjectRoleAddDialogFragment fragment = new ProjectRoleAddDialogFragment();
        fragment.setOnProjectRolesAddedListener(this);
            fragment.setArguments(bundle1);
        fragment.show(Objects.requireNonNull(getChildFragmentManager()), "TAG");
    }

    private void delete() {
        DeleteProjectDialogFragment deleteFragment =
                DeleteProjectDialogFragment.newInstance(mViewModel.getProject().getValue());
        deleteFragment.setOnProjectDeletedListener(this);
        deleteFragment.show(Objects.requireNonNull(getChildFragmentManager()), "TAG");
    }

    @Override
    public void onProjectDeleted() {
        Navigation.findNavController(Objects.requireNonNull(getView())).navigateUp();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getChildFragmentManager().getFragments().forEach(fragment -> {
                if (fragment instanceof DeleteProjectDialogFragment) {
                    ((DeleteProjectDialogFragment) fragment).setOnProjectDeletedListener(this);
                } else if (fragment instanceof ProjectRoleAddDialogFragment)
                    ((ProjectRoleAddDialogFragment) fragment).setOnProjectRolesAddedListener(this);
            });
        }
    }

    @Override
    public void onProjectRolesAdded(List<ProjectRole> projectRoles) {
        List<ProjectRole> roleList = mViewModel.getProjectRoles().getValue();
        Objects.requireNonNull(roleList).addAll(projectRoles);
        mViewModel.setProjectRoles(roleList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        projectRoleFragment = null;
    }
}
