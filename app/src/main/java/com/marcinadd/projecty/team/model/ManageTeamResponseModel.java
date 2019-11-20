package com.marcinadd.projecty.team.model;

import java.util.List;

public class ManageTeamResponseModel {
    private Team team;
    private List<TeamRole> teamRoles;

    public Team getTeam() {
        return team;
    }

    public List<TeamRole> getTeamRoles() {
        return teamRoles;
    }
}
