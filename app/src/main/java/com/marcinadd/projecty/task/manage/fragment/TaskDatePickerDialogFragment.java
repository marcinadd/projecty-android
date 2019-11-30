package com.marcinadd.projecty.task.manage.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.DatePicker;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.manage.DateType;
import com.marcinadd.projecty.task.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class TaskDatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, RetrofitListener<Void> {
    private DateType dateType;
    private Task task;
    private OnTaskDateChangedListener onTaskDateChangedListener;

    public static TaskDatePickerDialogFragment newInstance(Task task, DateType dateType) {
        TaskDatePickerDialogFragment fragment = new TaskDatePickerDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        bundle.putSerializable("dateType", dateType);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTaskDateChangedListener(OnTaskDateChangedListener onTaskDateChangedListener) {
        this.onTaskDateChangedListener = onTaskDateChangedListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
        if (getArguments() != null) {
            dateType = (DateType) getArguments().getSerializable("dateType");
            task = (Task) getArguments().getSerializable("task");
        }

        Date initDate;
        if (dateType == DateType.START_DATE) {
            initDate = Objects.requireNonNull(task).getStartDate();
        } else {
            initDate = Objects.requireNonNull(task).getEndDate();
        }

        c.setTime(initDate);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, dayOfMonth);
        Date date = c.getTime();
        Map<String, String> fields = new ArrayMap<>();
        if (dateType == DateType.START_DATE) {
            fields.put("startDate", DateHelper.formatDate(date));
            task.setStartDate(date);
        } else {
            fields.put("endDate", DateHelper.formatDate(date));
            task.setEndDate(date);
        }

        TaskService.getInstance(getContext()).editTaskDetails(task.getId(), fields, this);
    }

    @Override
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        onTaskDateChangedListener.onTaskDateChanged(task);
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnTaskDateChangedListener {
        void onTaskDateChanged(Task task);
    }

}
