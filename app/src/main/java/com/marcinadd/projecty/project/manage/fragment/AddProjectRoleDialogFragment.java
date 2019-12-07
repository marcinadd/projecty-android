package com.marcinadd.projecty.project.manage.fragment;

import android.app.Activity;
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
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.ApiProject;
import com.marcinadd.projecty.project.model.Project;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddProjectRoleDialogFragment extends DialogFragment {
    private Project project;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_project_role_add, null);
        final EditText editText = view.findViewById(R.id.projectrole_username_edit_text);
        final Activity activity = getActivity();
        builder.setMessage(R.string.add_role)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = editText.getText().toString();
                        List<String> usernames = Collections.singletonList(username);
                        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getContext());
                        ApiProject apiProject = retrofit.create(ApiProject.class);
                        apiProject.addUsers(project.getId(), usernames).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .setView(view);
        return builder.create();
    }
}
