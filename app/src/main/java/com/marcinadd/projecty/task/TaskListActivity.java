package com.marcinadd.projecty.task;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.task.fragment.TaskFragment;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;
import com.marcinadd.projecty.task.ui.main.SectionsPagerAdapter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskListActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            long projectId = bundle.getLong("projectId");
            Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getApplicationContext());
            TaskService taskService = retrofit.create(TaskService.class);
            taskService.taskList(projectId).enqueue(new TaskListDataCallback(getApplicationContext()));
        }


        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Task item) {

    }

    class TaskListDataCallback implements Callback<TaskListResponseModel> {
        private Context context;

        public TaskListDataCallback(Context context) {
            this.context = context;
        }

        @Override
        public void onResponse(Call<TaskListResponseModel> call, Response<TaskListResponseModel> response) {
            if (response.isSuccessful()) {
                TaskListResponseModel model = response.body();
                SectionsPagerAdapter sectionsPagerAdapter;
                if (model != null) {
                    sectionsPagerAdapter = new SectionsPagerAdapter(
                            context, getSupportFragmentManager(),
                            model.getToDoTasks(), model.getInProgressTasks(), model.getDoneTasks()
                    );
                    ViewPager viewPager = findViewById(R.id.view_pager);
                    viewPager.setAdapter(sectionsPagerAdapter);
                    TabLayout tabs = findViewById(R.id.tabs);
                    tabs.setupWithViewPager(viewPager);
                }
            }
        }

        @Override
        public void onFailure(Call<TaskListResponseModel> call, Throwable t) {

        }
    }
}