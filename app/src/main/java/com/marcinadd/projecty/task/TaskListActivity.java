package com.marcinadd.projecty.task;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.AddTaskListener;
import com.marcinadd.projecty.listener.TaskListResponseListener;
import com.marcinadd.projecty.task.fragment.AddTaskDialogFragment;
import com.marcinadd.projecty.task.fragment.TaskFragment;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;
import com.marcinadd.projecty.task.ui.main.SectionsPagerAdapter;

public class TaskListActivity extends AppCompatActivity implements TaskFragment.OnListFragmentInteractionListener, AddTaskListener, TaskListResponseListener {
    private long projectId;
    private Context context;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        context = getApplicationContext();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            projectId = bundle.getLong("projectId");
            TaskService.getInstance(context).getTaskList(projectId, this);
        }

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddTaskDialogFragment addTaskDialogFragment = new AddTaskDialogFragment(projectId, TaskListActivity.this);
                addTaskDialogFragment.show(getSupportFragmentManager(), "TAG");
            }
        });
    }

    @Override
    public void onListFragmentInteraction(Task item) {

    }

    @Override
    public void onTaskAdded(Task newTask) {
        sectionsPagerAdapter.addTaskToDo(newTask);
    }

    @Override
    public void onAddFailed() {

    }

    @Override
    public void onTaskListResponse(TaskListResponseModel model) {
        if (model != null) {
            sectionsPagerAdapter = new SectionsPagerAdapter(
                    context, getSupportFragmentManager(),
                    model.getToDoTasks(), model.getInProgressTasks(), model.getDoneTasks(), projectId
            );
            ViewPager viewPager = findViewById(R.id.view_pager);
            viewPager.setAdapter(sectionsPagerAdapter);
            TabLayout tabs = findViewById(R.id.tabs);
            tabs.setupWithViewPager(viewPager);
        }
    }
}