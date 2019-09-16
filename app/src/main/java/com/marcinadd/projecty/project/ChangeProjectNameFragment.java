package com.marcinadd.projecty.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClientImpl;
import com.marcinadd.projecty.project.model.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChangeProjectNameFragment extends Fragment {

    private ChangeProjectNameViewModel mViewModel;
    private Project project;
    private Button button;
    private EditText editText;

    public void setProject(Project project) {
        this.project = project;
    }

    public static ChangeProjectNameFragment newInstance() {
        return new ChangeProjectNameFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.change_project_name_fragment, container, false);
        button = view.findViewById(R.id.changeName);
        editText = view.findViewById(R.id.change_name_edit_text);
        if (getArguments() != null) {
            project = (Project) getArguments().getSerializable("project");
        }
        button.setOnClickListener(onChangeNameClick());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ChangeProjectNameViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public View.OnClickListener onChangeNameClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new AuthorizedNetworkClientImpl(getContext()).getRetrofitClient();
                ProjectClient projectClient = retrofit.create(ProjectClient.class);
                projectClient.changeName(project.getId(), editText.getText().toString()).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 200) {
                            Log.e("Ok", "OK");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        };
    }
}