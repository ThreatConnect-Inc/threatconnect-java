package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.response.entity.TaskResponse;

/**
 * Created by moweis-ad on 3/15/16.
 */
public class TaskWriterAdapter extends AbstractGroupWriterAdapter<Task>{

    protected TaskWriterAdapter(Connection conn) { super(conn, TaskResponse.class); }

    @Override
    public String getUrlType() { return "tasks"; }
}
