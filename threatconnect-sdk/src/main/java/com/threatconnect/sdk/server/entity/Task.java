package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.format.DateTimeSerializer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by moweis-ad on 3/14/16.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Task")
public class Task extends Group {
    @XmlElement(name = "Status", required = false)
    private String status;
    @XmlElement(name = "Escalated", required = false)
    private String escalated;
    @XmlElement(name = "Reminded", required = false)
    private String reminded;
    @XmlElement(name = "Overdue", required = false)
    private String overdue;
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "DueDate", required = false)
    private Date dueDate;
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "ReminderDate", required = false)
    private Date reminderDate;
    @JsonSerialize(using = DateTimeSerializer.class, include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "EscalationDate", required = false)
    private Date escalationDate;
    @XmlElement(name = "Assignees", required = false)
    private List<User> assignee = null;
    @XmlElement(name = "Escalatee", required = false)
    private List<User> escalatee = null;

    public Task()
    {
        super();
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getEscalated()
    {
        return escalated;
    }

    public void setEscalated(String escalated)
    {
        this.escalated = escalated;
    }

    public String getReminded()
    {
        return reminded;
    }

    public void setReminded(String reminded)
    {
        this.reminded = reminded;
    }

    public String getOverdue()
    {
        return overdue;
    }

    public void setOverdue(String overdue)
    {
        this.overdue = overdue;
    }

    public Date getDueDate()
    {
        return dueDate;
    }

    public void setDueDate(Date dueDate)
    {
        this.dueDate = dueDate;
    }

    public Date getReminderDate()
    {
        return reminderDate;
    }

    public void setReminderDate(Date reminderDate)
    {
        this.reminderDate = reminderDate;
    }

    public Date getEscalationDate()
    {
        return escalationDate;
    }

    public void setEscalationDate(Date escalationDate)
    {
        this.escalationDate = escalationDate;
    }

    public List<User> getAssignee()
    {
        return assignee;
    }

    public void setAssignee(List<User> assignee)
    {
        this.assignee = assignee;
    }

    public List<User> getEscalatee()
    {
        return escalatee;
    }

    public void setEscalatee(List<User> escalatee)
    {
        this.escalatee = escalatee;
    }

}
