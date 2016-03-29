package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * Created by moweis-ad on 3/15/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskListResponseData extends ApiEntityListResponseData<Task> {

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Task", required = false)
    private List<Task> task;

    public List<Task> getTask() { return task; }

    public void setTask(List<Task> tasks) { this.task = tasks; }

    @JsonIgnore
    @Override
    public List<Task> getData() { return getTask(); }

    @Override
    public void setData(List<Task> data) { setTask(data); }
}
