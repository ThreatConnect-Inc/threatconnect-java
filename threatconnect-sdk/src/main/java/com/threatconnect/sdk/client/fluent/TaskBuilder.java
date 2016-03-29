package com.threatconnect.sdk.client.fluent;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Owner;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.entity.format.DateTimeSerializer;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by moweis-ad on 3/21/16.
 */
public class TaskBuilder {
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;
    private String status;
    private boolean escalated;
    private boolean reminded;
    private boolean overdue;
    private Date dueDate;
    private Date reminderDate;
    private Date escalationDate;
    private List<User> assignee = new ArrayList<User>();
    private List<User> escalatee = new ArrayList<User>();

    public TaskBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public TaskBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public TaskBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public TaskBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public TaskBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public TaskBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public TaskBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }


    public TaskBuilder withStatus(String status)
    {
        this.status = status;
        return this;
    }

    public TaskBuilder withEscalated(boolean escalated)
    {
        this.escalated = escalated;
        return this;
    }

    public TaskBuilder withReminded(boolean reminded) {
        this.reminded = reminded;
        return this;
    }

    public TaskBuilder withOverdue(boolean overdue) {
        this.overdue = overdue;
        return this;
    }

    public TaskBuilder withDueDate(Date dueDate) {
        this.dueDate = dueDate;
        return this;
    }
    public TaskBuilder withReminderDate(Date reminderDate) {
        this.reminderDate = reminderDate;
        return this;
    }
    public TaskBuilder withEscalationDate(Date escalationDate) {
        this.escalationDate = escalationDate;
        return this;
    }

    public TaskBuilder withAssignee(List<User> assignee) {
        this.assignee = assignee;
        return this;
    }

    public TaskBuilder withEscalatee(List<User> escalatee) {
        this.escalatee = escalatee;
        return this;
    }

    public Task createTask() {
        return new Task(id, name, type, owner, ownerName, dateAdded, webLink,
                status, escalated, reminded, overdue, dueDate, reminderDate, escalationDate,
                assignee, escalatee);
    }

    /*public Task createTask() {
        return new Task(status, escalated, overdue, dueDate, reminderDate, escalationDate,
                assignee, escalatee);
    }*/
}
