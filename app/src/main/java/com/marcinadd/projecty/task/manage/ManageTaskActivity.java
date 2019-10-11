package com.marcinadd.projecty.task.manage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.task.ApiTask;
import com.marcinadd.projecty.task.TaskListActivity;
import com.marcinadd.projecty.task.manage.fragment.TaskDatePickerDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskNameDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskStatusDialogFragment;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageTaskActivity extends AppCompatActivity {
    private long taskId;
    private long projectId;
    private TextView taskNameTextView;
    private TextView taskStartDateTextView;
    private TextView taskEndDateTextView;
    private TextView taskStatusTextView;

    private TextView taskNameEditText;
    private TextView taskStartDateEditText;
    private TextView taskEndDateEditText;
    private TextView taskStatusEditText;

    private ManageTaskViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            taskId = bundle.getLong("taskId");
            projectId = bundle.getLong("projectId");
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_task);

        taskNameTextView = findViewById(R.id.manage_task_name_text_view);
        taskStartDateTextView = findViewById(R.id.manage_task_data_start_text_view);
        taskEndDateTextView = findViewById(R.id.manage_task_data_end_text_view);
        taskStatusTextView = findViewById(R.id.manage_task_task_status_text_view);

        taskNameEditText = findViewById(R.id.manage_task_name_edit_text);
        taskStartDateEditText = findViewById(R.id.manage_task_start_date_edit_text);
        taskEndDateEditText = findViewById(R.id.manage_task_end_date_edit_text);
        taskStatusEditText = findViewById(R.id.manage_task_status_edit_text);

        model = ViewModelProviders.of(this).get(ManageTaskViewModel.class);
        final Observer<Task> taskObserver = new TaskObserver();
        model.getTask().observe(this, taskObserver);

        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getApplicationContext());
        ApiTask apiTask = retrofit.create(ApiTask.class);
        apiTask.manageTask(taskId).enqueue(new ManageTaskCallback());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        setOnClickListeners();
    }

    void setOnClickListeners() {
        taskStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDatePickerDialogFragment taskDatePickerDialogFragment = new TaskDatePickerDialogFragment(model, DateType.START_DATE);
                taskDatePickerDialogFragment.show(getSupportFragmentManager(), "TAG");
            }
        });

        taskEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDatePickerDialogFragment taskDatePickerDialogFragment = new TaskDatePickerDialogFragment(model, DateType.END_DATE);
                taskDatePickerDialogFragment.show(getSupportFragmentManager(), "TAG");
            }
        });

        taskNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskNameDialogFragment taskNameDialogFragment = new TaskNameDialogFragment(model);
                taskNameDialogFragment.show(getSupportFragmentManager(), "TAG");
            }
        });

        taskStatusEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskStatusDialogFragment taskStatusDialogFragment = new TaskStatusDialogFragment(model);
                taskStatusDialogFragment.show(getSupportFragmentManager(), "TAG");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, TaskListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("projectId", projectId);
        startActivity(intent);
    }

    class ManageTaskCallback implements Callback<ManageTaskResponseModel> {
        @Override
        public void onResponse(Call<ManageTaskResponseModel> call, Response<ManageTaskResponseModel> response) {
            if (response.isSuccessful() && response.body() != null) {
                model.getTask().setValue(response.body().getTask());
            }
        }

        @Override
        public void onFailure(Call<ManageTaskResponseModel> call, Throwable t) {

        }
    }

    class TaskObserver implements Observer<Task> {

        @Override
        public void onChanged(Task task) {
            taskNameTextView.setText(task.getName());
            taskStartDateTextView.setText(DateHelper.formatDate(task.getStartDate()));
            taskEndDateTextView.setText(DateHelper.formatDate(task.getEndDate()));
            taskStatusTextView.setText(task.getStatus().toString());
            taskNameEditText.setText(task.getName());
            taskStartDateEditText.setText(DateHelper.formatDate(task.getStartDate()));
            taskEndDateEditText.setText(DateHelper.formatDate(task.getEndDate()));
            taskStatusEditText.setText(String.valueOf(task.getStatus()));
        }
    }
}
