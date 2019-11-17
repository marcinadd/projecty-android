package com.marcinadd.projecty.task.model;

import com.marcinadd.projecty.project.model.Project;
import com.marcinadd.projecty.project.model.User;
import com.marcinadd.projecty.task.TaskStatus;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Task implements Serializable {

    public Task() {
    }

    public Task(String name, Date startDate, Date endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private TaskStatus status;

    private List<User> assignedUsers;

    private Project project;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
