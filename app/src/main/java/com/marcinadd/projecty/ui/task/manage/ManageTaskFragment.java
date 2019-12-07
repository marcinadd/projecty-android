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
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.manage.DateType;
import com.marcinadd.projecty.task.manage.fragment.TaskDatePickerDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskNameDialogFragment;
import com.marcinadd.projecty.task.manage.fragment.TaskStatusDialogFragment;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;

import java.util.Objects;

public class ManageTaskFragment extends Fragment implements RetrofitListener<ManageTaskResponseModel>, TaskDatePickerDialogFragment.OnTaskDateChangedListener, TaskNameDialogFragment.OnTaskNameChangedListener, TaskStatusDialogFragment.OnTaskStatusChangedListener {
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
        View root = inflater.inflate(R.layout.fragment_task_manage, container, false);
        long taskId = ManageTaskFragmentArgs.fromBundle(Objects.requireNonNull(getArguments())).getTaskId();
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

    private void setOnClickListeners() {
        taskStartDateEditText.setOnClickListener(v -> {
            TaskDatePickerDialogFragment taskDatePickerDialogFragment =
                    TaskDatePickerDialogFragment.newInstance(
                            model.getTask().getValue(), DateType.START_DATE);
            taskDatePickerDialogFragment.setOnTaskDateChangedListener(this);
            taskDatePickerDialogFragment.show(getChildFragmentManager(), "START_DATE");
        });

        taskEndDateEditText.setOnClickListener(v -> {
            TaskDatePickerDialogFragment taskDatePickerDialogFragment =
                    TaskDatePickerDialogFragment.newInstance(
                            model.getTask().getValue(), DateType.END_DATE);
            taskDatePickerDialogFragment.setOnTaskDateChangedListener(this);
            taskDatePickerDialogFragment.show(getChildFragmentManager(), "END_DATE");
        });

        taskNameEditText.setOnClickListener(v -> {
            TaskNameDialogFragment taskNameDialogFragment =
                    TaskNameDialogFragment.newInstance(model.getTask().getValue());
            taskNameDialogFragment.setOnTaskNameChangedListener(this);
            taskNameDialogFragment.show(getChildFragmentManager(), "TAG");
        });

        taskStatusEditText.setOnClickListener(v -> {
            TaskStatusDialogFragment taskStatusDialogFragment =
                    TaskStatusDialogFragment.newInstance(model.getTask().getValue());
            taskStatusDialogFragment.setOnTaskStatusChangedListener(this);
                taskStatusDialogFragment.show(getChildFragmentManager(), "TAG");
        });
    }

    @Override
    public void onResponseSuccess(ManageTaskResponseModel response, @Nullable String TAG) {
        model.getTask().setValue(response.getTask());
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    @Override
    public void onTaskDateChanged(Task task) {
        model.getTask().setValue(task);
    }

    @Override
    public void onTaskNameChanged(String newName) {
        Task task = model.getTask().getValue();
        Objects.requireNonNull(task).setName(newName);
        model.getTask().setValue(task);
    }

    @Override
    public void onTaskStatusChanged(TaskStatus newTaskStatus) {
        Task task = model.getTask().getValue();
        Objects.requireNonNull(task).setStatus(newTaskStatus);
        model.getTask().setValue(task);
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

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getChildFragmentManager().getFragments().forEach(fragment -> {
                if (fragment instanceof TaskDatePickerDialogFragment) {
                    ((TaskDatePickerDialogFragment) fragment).setOnTaskDateChangedListener(this);
                } else if (fragment instanceof TaskNameDialogFragment) {
                    ((TaskNameDialogFragment) fragment).setOnTaskNameChangedListener(this);
                } else if (fragment instanceof TaskStatusDialogFragment)
                    ((TaskStatusDialogFragment) fragment).setOnTaskStatusChangedListener(this);
            });
        }
    }
}
