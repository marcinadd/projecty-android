package com.marcinadd.projecty.task.manage.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArrayMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.ui.task.manage.ManageTaskViewModel;

import java.util.Map;

public class TaskStatusDialogFragment extends DialogFragment implements RetrofitListener {
    private ManageTaskViewModel model;
    private Task task;

    public TaskStatusDialogFragment(ManageTaskViewModel model) {
        this.model = model;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] statuses = {
                getString(R.string.task_status_to_do),
                getString(R.string.task_status_in_progress),
                getString(R.string.task_status_done)
        };
        final Map<String, String> fields = new ArrayMap<>();
        task = model.getTask().getValue();
        builder.setTitle(R.string.title_edit_task_status_dialog_fragment)
                .setItems(statuses, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TaskStatus newStatus = TaskStatus.TO_DO;
                        switch (which) {
                            case 0:
                                newStatus = TaskStatus.TO_DO;
                                break;
                            case 1:
                                newStatus = TaskStatus.IN_PROGRESS;
                                break;
                            case 2:
                                newStatus = TaskStatus.DONE;
                                break;
                            default:
                        }
                        task.setStatus(newStatus);
                        fields.put("status", String.valueOf(newStatus));
                        TaskService.getInstance(getContext()).editTaskDetails(task.getId(), fields, TaskStatusDialogFragment.this);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        return builder.create();
    }

    @Override
    public void onResponseSuccess() {
        model.getTask().setValue(task);
    }

    @Override
    public void onResponseFailed() {

    }
}
