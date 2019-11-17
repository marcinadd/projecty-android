package com.marcinadd.projecty.project.manage.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.Navigation;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.ApiProject;
import com.marcinadd.projecty.project.model.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteProjectDialogFragment extends DialogFragment {

    private Project project;

    private final View mViev;

    public DeleteProjectDialogFragment(View view) {
        this.mViev = view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.delete_project_confirm, project.getName()))
                .setPositiveButton(R.string.ok, new OnOkButtonClick())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    class OnOkButtonClick implements Dialog.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getContext());
            ApiProject apiProject = retrofit.create(ApiProject.class);
            apiProject.deleteProject(project.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Navigation.findNavController(mViev).navigateUp();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
}
