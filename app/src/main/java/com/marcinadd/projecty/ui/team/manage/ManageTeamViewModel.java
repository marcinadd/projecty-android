package com.marcinadd.projecty.ui.team.manage;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marcinadd.projecty.team.model.Team;
import com.marcinadd.projecty.team.model.TeamRole;

import java.util.List;

public class ManageTeamViewModel extends ViewModel {
    MutableLiveData<Team> mTeam;
    MutableLiveData<List<TeamRole>> mTeamRoles;

    public ManageTeamViewModel() {
        mTeam = new MutableLiveData<>();
        mTeamRoles = new MutableLiveData<>();
    }

    public LiveData<Team> getTeam() {
        return mTeam;
    }

    public void setTeam(Team team) {
        mTeam.setValue(team);
    }

    public LiveData<List<TeamRole>> getTeamRoles() {
        return mTeamRoles;
    }

    public void setTeamRoles(List<TeamRole> teamRoles) {
        mTeamRoles.setValue(teamRoles);
    }
}
