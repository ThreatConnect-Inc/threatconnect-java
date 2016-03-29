package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Document;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.response.entity.data.TaskResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created by moweis-ad on 3/15/16.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "taskResponse")
@XmlSeeAlso(Task.class)
public class TaskResponse extends ApiEntitySingleResponse<Task, TaskResponseData> {
    public void setData(TaskResponseData data) { super.setData(data); }
}
