package com.marcinadd.projecty.task.manage.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.ArrayMap;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.model.Task;

import java.util.Map;

public class TaskNameDialogFragment extends DialogFragment implements RetrofitListener<Void> {
    private Task task;
    private OnTaskNameChangedListener onTaskNameChangedListener;

    public static TaskNameDialogFragment newInstance(Task task) {
        TaskNameDialogFragment fragment = new TaskNameDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("task", task);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void setOnTaskNameChangedListener(OnTaskNameChangedListener onTaskNameChangedListener) {
        this.onTaskNameChangedListener = onTaskNameChangedListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_change_name, null);
        final Map<String, String> fields = new ArrayMap<>();
        final EditText editText = view.findViewById(R.id.change_name_edit_text);

        if (getArguments() != null) {
            task = (Task) getArguments().getSerializable("task");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.title_edit_task_name_dialog_fragment)
                .setView(view)
                .setPositiveButton(R.string.button_positive_edit_task_name_dialog_fragment, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newName = editText.getText().toString();
                        fields.put("name", newName);
                        task.setName(newName);
                        TaskService.getInstance(getContext()).editTaskDetails(task.getId(), fields, TaskNameDialogFragment.this);
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
    public void onResponseSuccess(Void response, @Nullable String TAG) {
        onTaskNameChangedListener.onTaskNameChanged(task.getName());
    }

    @Override
    public void onResponseFailed(@Nullable String TAG) {

    }

    public interface OnTaskNameChangedListener {
        void onTaskNameChanged(String newName);
    }
}
