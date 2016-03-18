package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.response.entity.TaskListResponse;
import com.threatconnect.sdk.server.response.entity.TaskResponse;

/**
 * Created by moweis-ad on 3/15/16.
 */
public class TaskReaderAdapter extends AbstractGroupReaderAdapter<Task> {

    public TaskReaderAdapter(Connection conn) { super(conn, TaskResponse.class, Task.class, TaskListResponse.class); }

    @Override
    public String getUrlBasePrefix() { return "v2.tasks";}

    @Override
    public String getUrlType() { return "tasks";}
}
