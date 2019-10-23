package com.marcinadd.projecty.project.manage.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.ApiProject;
import com.marcinadd.projecty.project.model.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeProjectNameDialogFragment extends DialogFragment {
    private Project project;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.fragment_dialog_change_name, null);
        final EditText editText = view.findViewById(R.id.change_name_edit_text);
        final Activity activity = getActivity();
        builder.setMessage(R.string.text_view_change_name)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String newProjectName = editText.getText().toString();
                        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getContext());
                        ApiProject apiProject = retrofit.create(ApiProject.class);
                        apiProject.changeName(project.getId(), newProjectName).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.code() == 200) {
                                    TextView textView = activity.findViewById(R.id.projectName);
                                    textView.setText(newProjectName);
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setView(view);
        return builder.create();
    }
}
