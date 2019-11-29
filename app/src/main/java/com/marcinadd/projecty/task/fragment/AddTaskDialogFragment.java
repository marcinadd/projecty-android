package com.marcinadd.projecty.task.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.manage.DateType;
import com.marcinadd.projecty.task.model.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class AddTaskDialogFragment extends DialogFragment {

    private long projectId;
    private EditText editTextName;
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private RetrofitListener<Task> addTaskListener;

    public void setAddTaskListener(RetrofitListener<Task> addTaskListener) {
        this.addTaskListener = addTaskListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            projectId = getArguments().getLong("projectId");
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_add_task, null);
        editTextName = view.findViewById(R.id.edit_add_task_name);
        editTextStartDate = view.findViewById(R.id.edit_add_task_start_date);
        editTextStartDate.setOnClickListener(new OnDateClick(DateType.START_DATE));
        editTextEndDate = view.findViewById(R.id.edit_add_task_end_date);
        editTextEndDate.setOnClickListener(new OnDateClick(DateType.END_DATE));
        builder.setTitle(R.string.title_add_task)
                .setView(view)
                .setPositiveButton(R.string.ok, new OnOkClick())
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }

    class OnDateClick implements View.OnClickListener {
        private DateType dateType;

        public OnDateClick(DateType dateType) {
            this.dateType = dateType;
        }

        @Override
        public void onClick(View v) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext());
                datePickerDialog.show();
                if (dateType == DateType.START_DATE) {
                    datePickerDialog.setOnDateSetListener(new OnStartDateSet());
                } else {
                    datePickerDialog.setOnDateSetListener(new OnEndDateSet());
                }
            }
        }
    }

    class OnStartDateSet implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            editTextStartDate.setText(DateHelper.formatDate(calendar.getTime()));
        }
    }

    class OnEndDateSet implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            editTextEndDate.setText(DateHelper.formatDate(calendar.getTime()));
        }
    }

    class OnOkClick implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            String name = editTextName.getText().toString();
            Date startDate = null;
            Date endDate = null;
            try {
                startDate = DateHelper.parseDate(editTextStartDate.getText().toString());
                endDate = DateHelper.parseDate(editTextEndDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Task task = new Task(name, startDate, endDate);
            TaskService.getInstance(getContext())
                    .addTask(projectId, task, addTaskListener);
        }
    }
}
