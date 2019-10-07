package com.marcinadd.projecty.task;

import com.marcinadd.projecty.task.model.ManageTaskResponseModel;
import com.marcinadd.projecty.task.model.TaskListResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiTask {
    @GET("project/task/taskList")
    Call<TaskListResponseModel> taskList(@Query("projectId") long projectId);

    @POST("project/task/changeStatus")
    Call<Void> changeStatus(@Query("taskId") long taskId, @Query("status") TaskStatus taskStatus);

    @GET("project/task/manageTask")
    Call<ManageTaskResponseModel> manageTask(@Query("taskId") long taskId);

    @POST("project/task/editTaskDetails")
    Call<Void> editTaskDetails(@QueryMap Map<String, String> fields);
}
