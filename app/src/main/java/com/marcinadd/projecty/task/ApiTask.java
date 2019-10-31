package com.marcinadd.projecty.task;

import com.marcinadd.projecty.request.ChangeTaskStatusRequest;
import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.Task;
import com.marcinadd.projecty.task.model.TaskListResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiTask {
    @GET("tasks/project/{projectId}")
    Call<TaskListResponseModel> taskList(@Path("projectId") long projectId);

    @PATCH("tasks/{taskId}")
    Call<Void> changeStatus(@Path("taskId") long taskId, @Body ChangeTaskStatusRequest status);

    @GET("tasks/{taskId}")
    Call<ManageTaskResponseModel> manageTask(@Path("taskId") long taskId);

    @PATCH("tasks/{taskId}")
    Call<Void> editTaskDetails(@Path("taskId") long taskId, @Body Map<String, String> fields);

    @POST("tasks/project/{projectId}")
    Call<Task> addTask(@Path("projectId") long projectId,
                       @Body Task task);
}
