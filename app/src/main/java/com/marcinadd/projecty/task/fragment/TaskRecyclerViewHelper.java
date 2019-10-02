package com.marcinadd.projecty.task.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.marcinadd.projecty.R;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.task.TaskService;
import com.marcinadd.projecty.task.TaskStatus;
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
    static void adjustRecyclerViewItemToTaskStatus(final MyTaskRecyclerViewAdapter.ViewHolder holder, Task task) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        ImageView arrowLeft = holder.mView.findViewById(R.id.icon_task_move_left);
        ImageView arrowRight = holder.mView.findViewById(R.id.icon_task_move_right);
        Context context = holder.mView.getContext();
        switch (task.getStatus()) {
            case TO_DO:
                holder.date.setText(context.getString(R.string.starts_on, dateFormat.format(task.getStartDate())));
                arrowLeft.setVisibility(View.INVISIBLE);
                arrowRight.setOnClickListener(new OnChangeStatusArrowClick(task.getId(), context, IN_PROGRESS));
                break;
            case IN_PROGRESS:
                holder.date.setText(context.getString(R.string.ends_on, dateFormat.format(task.getEndDate())));
                arrowLeft.setOnClickListener(new OnChangeStatusArrowClick(task.getId(), context, TO_DO));
                arrowRight.setOnClickListener(new OnChangeStatusArrowClick(task.getId(), context, DONE));
                break;
            case DONE:
                arrowLeft.setOnClickListener(new OnChangeStatusArrowClick(task.getId(), context, IN_PROGRESS));
                arrowRight.setVisibility(View.INVISIBLE);
        }
        holder.mItem = task;
        holder.taskName.setText(task.getName());
    }

    static class OnChangeStatusArrowClick implements View.OnClickListener {
        private long taskId;
        private TaskStatus newTaskStatus;
        private Context context;

        public OnChangeStatusArrowClick(long taskId, Context context, TaskStatus newTaskStatus) {
            this.newTaskStatus = newTaskStatus;
            this.context = context;
            this.taskId = taskId;
        }

        @Override
        public void onClick(View v) {
            Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
            TaskService taskService = retrofit.create(TaskService.class);
            taskService.changeStatus(taskId, newTaskStatus).enqueue(new Callback<Void>() {
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

}
