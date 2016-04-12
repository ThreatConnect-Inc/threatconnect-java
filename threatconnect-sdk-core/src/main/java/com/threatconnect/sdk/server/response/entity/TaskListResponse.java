package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.response.entity.data.TaskListResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by moweis-ad on 3/15/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "tasksResponse")
@XmlSeeAlso(Task.class)
public class TaskListResponse extends ApiEntityListResponse<Task, TaskListResponseData> {
    public void setData(TaskListResponseData data) { super.setData(data); }
}
