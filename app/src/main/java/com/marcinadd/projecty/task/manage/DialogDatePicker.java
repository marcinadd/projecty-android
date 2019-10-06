package com.marcinadd.projecty.task.manage;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.ArrayMap;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.helper.DateHelper;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DialogDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener {
    private Date initDate;
    private DateType dateType;
    private ManageTaskViewModel model;

    public DialogDatePicker(ManageTaskViewModel model, DateType dateType) {
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

        final Task task = model.getTask().getValue();
        fields.put("id", String.valueOf(task.getId()));
        if (dateType == DateType.START_DATE) {
            fields.put("startDate", DateHelper.formatDate(date));
            task.setStartDate(date);
        } else {
            fields.put("endDate", DateHelper.formatDate(date));
            task.setEndDate(date);
        }

        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(getContext());
        TaskService taskService = retrofit.create(TaskService.class);

        taskService.editTaskDetails(fields).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    model.getTask().setValue(task);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
