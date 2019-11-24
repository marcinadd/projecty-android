package com.marcinadd.projecty.team;

import android.content.Context;

import com.marcinadd.projecty.callback.RetrofitCallback;
import com.marcinadd.projecty.client.AuthorizedNetworkClient;
import com.marcinadd.projecty.listener.RetrofitListener;
import com.marcinadd.projecty.team.model.ManageTeamResponseModel;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;
import java.util.Map;

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

    public void getTeams(RetrofitListener<List<TeamRole>> listener) {
        apiTeam.getTeams().enqueue(new RetrofitCallback<>(listener));
    }

    public void getTeamWithRoles(long teamId, RetrofitListener<ManageTeamResponseModel> listener) {
        apiTeam.getTeamWithRoles(teamId).enqueue(new RetrofitCallback<>(listener));
    }

    public void editTeam(long teamId, Map<String, String> fields, RetrofitListener<Void> listener) {
        apiTeam.editTeam(teamId, fields).enqueue(new RetrofitCallback<>(listener));
    }

    public void updateTeamRole(long teamRoleId, Map<String, String> fields, RetrofitListener<Void> listener) {
        apiTeam.updateTeamRole(teamRoleId, fields).enqueue(new RetrofitCallback<>(listener));
    }

    public void deleteTeamRole(long teamRoleId, RetrofitListener<Void> listener) {
        apiTeam.deleteTeamRole(teamRoleId).enqueue(new RetrofitCallback<>(listener));
    }

    public void addUsers(long teamId, List<String> usernames, RetrofitListener<List<TeamRole>> listener) {
        apiTeam.addUsers(teamId, usernames).enqueue(new RetrofitCallback<>(listener));
    }
}
