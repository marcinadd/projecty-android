package com.marcinadd.projecty.team.model;

import com.marcinadd.projecty.project.model.User;

public class TeamRole {
    private Long id;
    private TeamRoles name;
    private User user;
    private Team team;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamRoles getName() {
        return name;
    }

    public void setName(TeamRoles name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    @Override
    public String toString() {
        return "TeamRole{" +
                "id=" + id +
                ", name=" + name +
                ", user=" + user +
                '}';
    }
}
