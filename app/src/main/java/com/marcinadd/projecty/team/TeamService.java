package com.marcinadd.projecty.team;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;

import retrofit2.Retrofit;

public class TeamService {
    private static TeamService teamService;
    private ApiTeam apiTeam;

    private TeamService(Context context) {
        Retrofit retrofit = AuthorizedNetworkClient.getRetrofitClient(context);
        apiTeam = retrofit.create(ApiTeam.class);
    }

    public static TeamService getInstance(Context context) {
        if (teamService == null) {
            teamService = new TeamService(context);
        }
        return teamService;
    }

    public void getTeams(final RetrofitListener<List<TeamRole>> listener) {
        apiTeam.getTeams().enqueue(new RetrofitCallback<>(listener));
    }
}
