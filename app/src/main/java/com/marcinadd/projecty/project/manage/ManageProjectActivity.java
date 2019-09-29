package com.marcinadd.projecty.project.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.project.MyProjectsActivity;
import com.marcinadd.projecty.project.ProjectClient;
import com.marcinadd.projecty.project.manage.fragment.AddProjectRoleDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.ChangeProjectNameDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.DeleteProjectDialogFragment;
import com.marcinadd.projecty.project.manage.fragment.ProjectRoleFragment;
import com.marcinadd.projecty.project.model.ManageProject;
import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.ProjectRole;

import java.io.Serializable;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageProjectActivity extends AppCompatActivity implements ProjectRoleFragment.OnListFragmentInteractionListener {
    private long projectId;
    private TextView projectName;
    private Project project;
    private ManageProject manageProject;

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

        ImageView changeNameButton = findViewById(R.id.change_name_button);
        changeNameButton.setOnClickListener(new ChangeNameButtonClick());

        ImageView addRoleButton = findViewById(R.id.add_role_button);
        addRoleButton.setOnClickListener(new AddRoleButtonClick());

        ImageView deleteProjectImageView = findViewById(R.id.delete_project_image_view);
        deleteProjectImageView.setOnClickListener(new DeleteProjectButtonClick());

        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getApplicationContext());
        ProjectClient projectClient = retrofit.create(ProjectClient.class);

        projectClient.manageProject(projectId).enqueue(new Callback<ManageProject>() {
            @Override
            public void onResponse(Call<ManageProject> call, Response<ManageProject> response) {
                if (response.code() == 200) {
                    manageProject = response.body();
                    if (manageProject != null) {
                        project = manageProject.getProject();
                        projectName.setText(project.getName());
                        loadDefaultFragment();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MyProjectsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }

    void loadDefaultFragment() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("projectRoles", (Serializable) manageProject.getProjectRoles());
        ProjectRoleFragment projectRoleFragment = new ProjectRoleFragment();
        projectRoleFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout2, projectRoleFragment);
        transaction.commit();
    }

    @Override
    public void onListFragmentInteraction(ProjectRole item) {

    }

    class ChangeNameButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", project);
            ChangeProjectNameDialogFragment fragment = new ChangeProjectNameDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }

    class AddRoleButtonClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", project);
            AddProjectRoleDialogFragment fragment = new AddProjectRoleDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }

    class DeleteProjectButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Bundle bundle1 = new Bundle();
            bundle1.putSerializable("project", project);
            DeleteProjectDialogFragment fragment = new DeleteProjectDialogFragment();
            fragment.setArguments(bundle1);
            fragment.show(getSupportFragmentManager(), "TAG");

        }
    }
}