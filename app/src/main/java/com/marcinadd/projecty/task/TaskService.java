package com.marcinadd.projecty.task;

import android.content.Context;

import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.AddTaskListener;
import com.marcinadd.projecty.listener.ManageTaskResponseListener;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.listener.TaskListResponseListener;
import com.marcinadd.projecty.listener.TaskStatusChangedListener;
import com.marcinadd.projecty.request.ChangeTaskStatusRequest;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TaskService extends AuthorizedNetworkClient {
    private static TaskService taskService;
    private ApiTask apiTask;

    private TaskService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiTask = retrofit.create(ApiTask.class);
    }

    public static TaskService getInstance(Context context) {
        if (taskService == null) {
            taskService = new TaskService(context);
        }
        return taskService;
    }

    public void editTaskDetails(long taskId, Map<String, String> fields, final RetrofitListener retrofitListener) {
        apiTask.editTaskDetails(taskId, fields).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    retrofitListener.onResponseSuccess();
                }
                retrofitListener.onResponseFailed();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                retrofitListener.onResponseFailed();
            }
        });
    }

    public void addTask(long projectId, Task task,
                        final AddTaskListener addTaskListener) {
        apiTask.addTask(projectId, task).enqueue(new Callback<Task>() {
            @Override
            public void onResponse(Call<Task> call, Response<Task> response) {
                if (response.isSuccessful()) {
                    addTaskListener.onTaskAdded(response.body());
                }
            }

            @Override
            public void onFailure(Call<Task> call, Throwable t) {
                addTaskListener.onAddFailed();
            }
        });
    }

    public void changeStatus(final Task task, final TaskStatus newTaskStatus, final TaskStatusChangedListener listener) {
        ChangeTaskStatusRequest changeTaskStatusRequest = new ChangeTaskStatusRequest(newTaskStatus);
        apiTask.changeStatus(task.getId(), changeTaskStatusRequest).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onTaskStatusChanged(task, newTaskStatus);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    public void getTaskList(final long projectId, final TaskListResponseListener listener) {
        apiTask.taskList(projectId).enqueue(new Callback<TaskListResponseModel>() {
            @Override
            public void onResponse(Call<TaskListResponseModel> call, Response<TaskListResponseModel> response) {
                if (response.isSuccessful()) {
                    listener.onTaskListResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<TaskListResponseModel> call, Throwable t) {

            }
        });
    }

    public void manageTask(final long taskId, final ManageTaskResponseListener listener) {
        apiTask.manageTask(taskId).enqueue(new Callback<ManageTaskResponseModel>() {
            @Override
            public void onResponse(Call<ManageTaskResponseModel> call, Response<ManageTaskResponseModel> response) {
                if (response.isSuccessful()) {
                    listener.onManageTaskResponse(response.body());
                }
            }

            @Override
            public void onFailure(Call<ManageTaskResponseModel> call, Throwable t) {

            }
        });
    }
}
