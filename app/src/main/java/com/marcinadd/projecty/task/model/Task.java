package com.marcinadd.projecty.task.model;

import com.marcinadd.projecty.task.TaskStatus;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {

    private long id;

    private String name;

    private Date startDate;

    private Date endDate;

    private TaskStatus status;

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
}
