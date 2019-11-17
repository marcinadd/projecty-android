package com.marcinadd.projecty.team;

import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiTeam {
    @GET("/teams")
    Call<List<TeamRole>> getTeams();
}
