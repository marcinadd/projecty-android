package com.marcinadd.projecty.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.model.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AddProjectDialogFragment extends DialogFragment {
    private LinearLayout linearLayout;
    private EditText projectName;
    private Activity activity;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = Objects.requireNonNull(getActivity()).getLayoutInflater().inflate(R.layout.fragment_dialog_add_project, null);
        builder.setMessage("Add project")
                .setPositiveButton(R.string.ok, new OnPositiveButtonClick())
                .setNegativeButton(R.string.cancel, new OnNegativeButtonClick())
                .setView(view);
        ImageView imageView = view.findViewById(R.id.nav_header_avatar);
        activity = getActivity();
        linearLayout = view.findViewById(R.id.layout_add_project_dialog);
        projectName = view.findViewById(R.id.add_project_edit_text);
        imageView.setOnClickListener(new OnAddEditTextImageViewClick());
        return builder.create();
    }

    public List<String> getUsernamesToList() {
        List<String> usernames = new ArrayList<>();
        int n = linearLayout.getChildCount();
        for (int i = 0; i < n; i++) {
            EditText editText = (EditText) linearLayout.getChildAt(i);
            String username = editText.getText().toString();
            if (!username.isEmpty()) {
                usernames.add(username);
            }
        }
        return usernames;
    }

    class OnAddEditTextImageViewClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (linearLayout.getChildCount() < 5) {
                EditText editText = new EditText(getContext());
                editText.setHint(R.string.enter_username);
                linearLayout.addView(editText);
            }
        }
    }

    class OnPositiveButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            List<String> usernames = getUsernamesToList();
            String name = projectName.getText().toString();
            Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getContext());
            ApiProject apiProject = retrofit.create(ApiProject.class);
            Project project = new Project(name);
            project.setUsernames(usernames);
            apiProject.addProject(project).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    activity.recreate();
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });
        }
    }

    class OnNegativeButtonClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
