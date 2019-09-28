package com.marcinadd.projecty.project;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.project.model.ProjectRole;

public class MyProjectsActivity extends AppCompatActivity implements ProjectFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_projects);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new OnFloatingAcctionButtonClick());
    }
    @Override
    public void onListFragmentInteraction(ProjectRole item) {

    }

    class OnFloatingAcctionButtonClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            AddProjectDialogFragment fragment = new AddProjectDialogFragment();
            fragment.show(getSupportFragmentManager(), "TAG");
        }
    }
}
