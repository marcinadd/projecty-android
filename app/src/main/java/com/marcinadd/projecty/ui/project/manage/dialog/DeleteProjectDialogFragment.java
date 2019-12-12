package com.marcinadd.projecty.ui.project.manage.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.project.ProjectService;
import com.marcinadd.projecty.project.model.Project;


public class DeleteProjectDialogFragment extends DialogFragment implements RetrofitListener<Void> {
    private Project project;
    private OnProjectDeletedListener callback;

    public static DeleteProjectDialogFragment newInstance(Project project) {
        DeleteProjectDialogFragment fragment = new DeleteProjectDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("project", project);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnProjectDeletedListener(OnProjectDeletedListener callback) {
        this.callback = callback;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_project_confirm, project.getName()))
                .setPositiveButton(R.string.ok, onPositivebuttonClick())
                .setNegativeButton(R.string.cancel, null);
        return builder.create();
    }

    Dialog.OnClickListener onPositivebuttonClick() {
        return (dialog, which) -> {
            ProjectService.getInstance(getContext()).deleteProject(project.getId(), this);
        };
    }

    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        callback.onProjectDeleted();
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnProjectDeletedListener {
        void onProjectDeleted();
    }
}
