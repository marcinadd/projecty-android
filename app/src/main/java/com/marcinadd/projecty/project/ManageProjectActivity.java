package com.marcinadd.projecty.project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClientImpl;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageProjectActivity extends AppCompatActivity {
    private long projectId;
    private TextView projectName;
    private Project project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_project);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        projectName = findViewById(R.id.projectName);

        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            projectId = bundle.getLong("projectId");
        }

        Retrofit retrofit = new AuthorizedNetworkClientImpl(getApplicationContext()).getRetrofitClient();
        ProjectClient projectClient = retrofit.create(ProjectClient.class);

        projectClient.manageProject(projectId).enqueue(new Callback<ManageProject>() {
            @Override
            public void onResponse(Call<ManageProject> call, Response<ManageProject> response) {
                if (response.code() == 200) {
                    ManageProject manageProject = response.body();
                    if (manageProject != null) {
                        project = manageProject.getProject();
                        projectName.setText(project.getName());
                        Bundle bundle1 = new Bundle();
                        bundle1.putSerializable("project", project);
                        ChangeProjectNameFragment fragment = new ChangeProjectNameFragment();
                        fragment.setArguments(bundle1);
                        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.frameLayout2, fragment);
                        transaction.commit();
                    }
                }
            }

            @Override
            public void onFailure(Call<ManageProject> call, Throwable t) {

            }
        });

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
