package com.marcinadd.projecty.task.fragment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.task.ApiTask;
import com.marcinadd.projecty.task.TaskStatus;
import com.marcinadd.projecty.task.manage.ManageTaskActivity;
import com.marcinadd.projecty.task.model.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.marcinadd.projecty.task.TaskStatus.DONE;
import static com.marcinadd.projecty.task.TaskStatus.IN_PROGRESS;
import static com.marcinadd.projecty.task.TaskStatus.TO_DO;

public class TaskRecyclerViewHelper {
    private static long projectId;

    static void adjustRecyclerViewItemToTaskStatus(final MyTaskRecyclerViewAdapter.ViewHolder holder, Task task, long projectId) {
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
                arrowRightIcon.setOnClickListener(new OnChangeStatusIconClick(task.getId(), context, IN_PROGRESS));
                break;
            case IN_PROGRESS:
                holder.date.setText(context.getString(R.string.ends_on, dateFormat.format(task.getEndDate())));
                arrowLeftIcon.setOnClickListener(new OnChangeStatusIconClick(task.getId(), context, TO_DO));
                arrowRightIcon.setOnClickListener(new OnChangeStatusIconClick(task.getId(), context, DONE));
                break;
            case DONE:
                arrowLeftIcon.setOnClickListener(new OnChangeStatusIconClick(task.getId(), context, IN_PROGRESS));
                arrowRightIcon.setVisibility(View.INVISIBLE);
        }
        manageActivityIcon.setOnClickListener(new OnManageTaskIconClick(context, task.getId()));
        holder.mItem = task;
        holder.taskName.setText(task.getName());
    }

    static class OnChangeStatusIconClick implements View.OnClickListener {
        private long taskId;
        private TaskStatus newTaskStatus;
        private Context context;

        public OnChangeStatusIconClick(long taskId, Context context, TaskStatus newTaskStatus) {
            this.newTaskStatus = newTaskStatus;
            this.context = context;
            this.taskId = taskId;
        }

        @Override
        public void onClick(View v) {
            Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
            ApiTask apiTask = retrofit.create(ApiTask.class);
            apiTask.changeStatus(taskId, newTaskStatus).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        // TODO Refresh layout here
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {

                }
            });

        }
    }

    static class OnManageTaskIconClick implements View.OnClickListener {
        private Context context;
        private long taskId;

        OnManageTaskIconClick(Context context, long taskId) {
            this.context = context;
            this.taskId = taskId;
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ManageTaskActivity.class);
            intent.putExtra("taskId", taskId);
            intent.putExtra("projectId", projectId);
            context.startActivity(intent);
        }
    }

}
