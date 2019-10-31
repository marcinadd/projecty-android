package com.marcinadd.projecty.task.manage.fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.manage.DateType;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.ui.task.manage.ManageTaskViewModel;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class TaskDatePickerDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener, RetrofitListener {
    private Date initDate;
    private DateType dateType;
    private ManageTaskViewModel model;
    private Task task;

    public TaskDatePickerDialogFragment(ManageTaskViewModel model, DateType dateType) {
        this.model = model;
        this.dateType = dateType;
        if (dateType == DateType.START_DATE) {
            initDate = Objects.requireNonNull(model.getTask().getValue()).getStartDate();
        } else {
            initDate = Objects.requireNonNull(model.getTask().getValue()).getEndDate();
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Calendar c = Calendar.getInstance();
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
        task = model.getTask().getValue();
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
    public void onResponseSuccess() {
        model.getTask().setValue(task);
    }

    @Override
    public void onResponseFailed() {

    }
}
