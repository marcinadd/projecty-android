package com.marcinadd.projecty.project;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProjectClient {
    @GET("project/myProjects")
    Call<UserProject> getProjects(@Header("Authorization") String authToken);
}
