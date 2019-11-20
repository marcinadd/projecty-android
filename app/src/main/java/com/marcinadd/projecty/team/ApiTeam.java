package com.marcinadd.projecty.team;

import com.marcinadd.projecty.team.model.ManageTeamResponseModel;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiTeam {
    @GET("/teams")
    Call<List<TeamRole>> getTeams();

    @GET("/teams/{teamId}?roles=true")
    Call<ManageTeamResponseModel> getTeamWithRoles(@Path("teamId") long teamId);

    @PATCH("/teams/{teamId}")
    Call<Void> editTeam(@Path("teamId") long teamId, @Body Map<String, String> fields);
}
