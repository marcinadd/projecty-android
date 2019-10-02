package com.marcinadd.projecty.task;

import com.marcinadd.projecty.task.model.TaskListResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface TaskService {
    @GET("project/task/taskList")
    Call<TaskListResponseModel> taskList(@Query("projectId") long projectId);

    @POST("project/task/changeStatus")
    Call<Void> changeStatus(@Query("taskId") long taskId, @Query("status") TaskStatus taskStatus);
}
