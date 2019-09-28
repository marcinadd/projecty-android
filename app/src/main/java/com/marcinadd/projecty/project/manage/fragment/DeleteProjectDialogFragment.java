package com.marcinadd.projecty.project.manage.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.MyProjectsActivity;
import com.marcinadd.projecty.project.ProjectClient;
import com.marcinadd.projecty.project.model.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DeleteProjectDialogFragment extends DialogFragment {
    private Project project;
    private Activity activity;
    private Context context;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        activity = getActivity();
        context = getContext();
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
            ProjectClient projectClient = retrofit.create(ProjectClient.class);
            projectClient.deleteProject(project.getId()).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    activity.startActivity(new Intent(context, MyProjectsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                    activity.finish();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }
}
