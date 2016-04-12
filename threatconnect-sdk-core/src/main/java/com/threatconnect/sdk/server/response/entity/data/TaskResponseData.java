package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Task;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Created by moweis-ad on 3/15/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TaskResponseData extends ApiEntitySingleResponseData<Task> {

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Task", required = false)
    private Task task;

    public Task getTask() { return task; }

    public void setTask(Task task) { this.task = task; }

    @JsonIgnore
    @Override
    public Task getData() { return getTask(); }

    @Override
    public void setData(Task data) { setTask(data); }
}
