package com.marcinadd.projecty.task.manage;

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
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ManageTaskActivity extends AppCompatActivity {
    private long taskId;
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

        model = ViewModelProviders.of(this).get(ManageTaskViewModel.class);
        final Observer<Task> taskObserver = new TaskObserver();
        model.getTask().observe(this, taskObserver);

        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getApplicationContext());
        TaskService taskService = retrofit.create(TaskService.class);
        taskService.manageTask(taskId).enqueue(new ManageTaskCallback());

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
                DialogDatePicker dialogDatePicker = new DialogDatePicker(model, DateType.START_DATE);
                dialogDatePicker.show(getSupportFragmentManager(), "TAG");
            }
        });

        taskEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDatePicker dialogDatePicker = new DialogDatePicker(model, DateType.END_DATE);
                dialogDatePicker.show(getSupportFragmentManager(), "TAG");
            }
        });

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
        }
    }
}
