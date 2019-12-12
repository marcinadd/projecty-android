package com.marcinadd.projecty.ui.project.manage.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.ProjectService;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.ProjectRole;

import java.util.Collections;
import java.util.List;

public class ProjectRoleAddDialogFragment extends DialogFragment implements RetrofitListener<List<ProjectRole>> {
    private static final String PROJECT = "PROJECT";
    private Project project;
    private EditText editText;
    private OnProjectRolesAddedListener onProjectRolesAddedListener;

    public static ProjectRoleAddDialogFragment newInstance(Project project) {
        ProjectRoleAddDialogFragment fragment = new ProjectRoleAddDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(PROJECT, project);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnProjectRolesAddedListener(OnProjectRolesAddedListener onProjectRolesAddedListener) {
        this.onProjectRolesAddedListener = onProjectRolesAddedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_project_role_add, null);
        editText = view.findViewById(R.id.projectrole_username_edit_text);
        builder.setMessage(R.string.add_role)
                .setPositiveButton(R.string.ok, onPositiveButtonClick())
                .setNegativeButton(R.string.cancel, null)
                .setView(view);
        return builder.create();
    }

    DialogInterface.OnClickListener onPositiveButtonClick() {
        return (dialog, which) -> {
            String username = editText.getText().toString();
            List<String> usernames = Collections.singletonList(username);
            ProjectService.getInstance(getContext()).addUsers(project.getId(), usernames, this);
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        editText = null;
        project = null;
    }

    @Override
    public void onResponseSuccess(List<ProjectRole> response, @Nullable String TAG) {
        onProjectRolesAddedListener.onProjectRolesAdded(response);
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnProjectRolesAddedListener {
        void onProjectRolesAdded(List<ProjectRole> projectRoles);
    }
}
