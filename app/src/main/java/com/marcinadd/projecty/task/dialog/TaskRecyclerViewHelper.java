package com.marcinadd.projecty.task.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.navigation.Navigation;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.listener.TaskStatusChangedListener;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.ui.task.TaskListFragmentDirections;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.marcinadd.projecty.task.TaskStatus.DONE;
import static com.marcinadd.projecty.task.TaskStatus.IN_PROGRESS;
import static com.marcinadd.projecty.task.TaskStatus.TO_DO;

public class TaskRecyclerViewHelper {
    private static long projectId;

    static void adjustRecyclerViewItemToTaskStatus(final MyTaskRecyclerViewAdapter.ViewHolder holder, Task task, long projectId, TaskStatusChangedListener taskStatusChangedListener) {
        TaskRecyclerViewHelper.projectId = projectId;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ImageView arrowLeftIcon = holder.mView.findViewById(R.id.icon_task_move_left);
        ImageView arrowRightIcon = holder.mView.findViewById(R.id.icon_task_move_right);
        ImageView manageActivityIcon = holder.mView.findViewById(R.id.icon_task_manage);
        Context context = holder.mView.getContext();
        switch (task.getStatus()) {
            case TO_DO:
                holder.date.setText(context.getString(R.string.starts_on, dateFormat.format(task.getStartDate())));
                arrowLeftIcon.setVisibility(View.INVISIBLE);
                arrowRightIcon.setOnClickListener(new OnChangeStatusIconClick(task, IN_PROGRESS, context, taskStatusChangedListener));
                break;
            case IN_PROGRESS:
                holder.date.setText(context.getString(R.string.ends_on, dateFormat.format(task.getEndDate())));
                arrowLeftIcon.setOnClickListener(new OnChangeStatusIconClick(task, TO_DO, context, taskStatusChangedListener));
                arrowRightIcon.setOnClickListener(new OnChangeStatusIconClick(task, DONE, context, taskStatusChangedListener));
                break;
            case DONE:
                arrowLeftIcon.setOnClickListener(new OnChangeStatusIconClick(task, IN_PROGRESS, context, taskStatusChangedListener));
                arrowRightIcon.setVisibility(View.INVISIBLE);
        }
        manageActivityIcon.setOnClickListener(new OnManageTaskIconClick(task.getId(), holder.mView));
        holder.mItem = task;
        holder.taskName.setText(task.getName());
    }


    static class OnChangeStatusIconClick implements View.OnClickListener {
        private Task task;
        private TaskStatus newTaskStatus;
        private Context context;
        private TaskStatusChangedListener listener;

        public OnChangeStatusIconClick(Task task, TaskStatus newTaskStatus, Context context, TaskStatusChangedListener listener) {
            this.task = task;
            this.newTaskStatus = newTaskStatus;
            this.context = context;
            this.listener = listener;
        }

        @Override
        public void onClick(View v) {
            TaskService.getInstance(context).changeStatus(task, newTaskStatus, listener);
        }
    }

    static class OnManageTaskIconClick implements View.OnClickListener {
        private long taskId;
        private View mView;

        OnManageTaskIconClick(long taskId, View view) {
            this.taskId = taskId;
            mView = view;
        }

        @Override
        public void onClick(View v) {
            TaskListFragmentDirections.ActionTaskListActivityToManageTaskFragment action = TaskListFragmentDirections.actionTaskListActivityToManageTaskFragment();
            action.setProjectId(projectId);
            action.setTaskId(taskId);
            Navigation.findNavController(mView).navigate(action);
        }
    }

}
