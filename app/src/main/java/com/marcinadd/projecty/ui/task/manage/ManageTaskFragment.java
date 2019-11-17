package com.marcinadd.projecty.ui.task.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.manage.DateType;
import com.marcinadd.projecty.task.manage.fragment.TaskDatePickerDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskNameDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskStatusDialogFragment;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;

public class ManageTaskFragment extends Fragment implements RetrofitListener<ManageTaskResponseModel> {
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_manage_task, container, false);
        projectId = ManageTaskFragmentArgs.fromBundle(getArguments()).getProjectId();
        taskId = ManageTaskFragmentArgs.fromBundle(getArguments()).getTaskId();
        taskNameTextView = root.findViewById(R.id.manage_task_name_text_view);
        taskStartDateTextView = root.findViewById(R.id.manage_task_data_start_text_view);
        taskEndDateTextView = root.findViewById(R.id.manage_task_data_end_text_view);
        taskStatusTextView = root.findViewById(R.id.manage_task_task_status_text_view);

        taskNameEditText = root.findViewById(R.id.manage_task_name_edit_text);
        taskStartDateEditText = root.findViewById(R.id.manage_task_start_date_edit_text);
        taskEndDateEditText = root.findViewById(R.id.manage_task_end_date_edit_text);
        taskStatusEditText = root.findViewById(R.id.manage_task_status_edit_text);

        model = ViewModelProviders.of(this).get(ManageTaskViewModel.class);
        final Observer<Task> taskObserver = new TaskObserver();
        model.getTask().observe(this, taskObserver);

        TaskService.getInstance(getContext()).manageTask(taskId, this);
        setOnClickListeners();
        return root;
    }

    void setOnClickListeners() {
        taskStartDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDatePickerDialogFragment taskDatePickerDialogFragment = new TaskDatePickerDialogFragment(model, DateType.START_DATE);
                taskDatePickerDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });

        taskEndDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskDatePickerDialogFragment taskDatePickerDialogFragment = new TaskDatePickerDialogFragment(model, DateType.END_DATE);
                taskDatePickerDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });

        taskNameEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskNameDialogFragment taskNameDialogFragment = new TaskNameDialogFragment(model);
                taskNameDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });

        taskStatusEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskStatusDialogFragment taskStatusDialogFragment = new TaskStatusDialogFragment(model);
                taskStatusDialogFragment.show(getChildFragmentManager(), "TAG");
            }
        });
    }

    @Override
    public void onResponseSuccess(ManageTaskResponseModel response, @Nullable String TAG) {
        model.getTask().setValue(response.getTask());
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

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
