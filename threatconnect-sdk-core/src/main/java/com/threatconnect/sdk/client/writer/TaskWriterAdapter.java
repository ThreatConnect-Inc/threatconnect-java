package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.TaskResponse;
import com.threatconnect.sdk.server.response.entity.UserResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moweis-ad on 3/15/16.
 */
public class TaskWriterAdapter extends AbstractGroupWriterAdapter<Task>{

    protected TaskWriterAdapter(Connection conn) { super(conn, TaskResponse.class); }

    public UserResponse createAssignee(final Integer uniqueId, final User assignee) throws IOException{
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", assignee.getUserName());
            }
        };
        return createItem(getUrlBasePrefix() + ".assignees.byUserName", UserResponse.class, null, paramMap, assignee);
    }

    public UserResponse createEscalatee(final Integer uniqueId, final User escalatee) throws IOException{
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", escalatee.getUserName());
            }
        };
        return createItem(getUrlBasePrefix() + ".escalatees.byUserName", UserResponse.class, null, paramMap, escalatee);
    }

    public UserResponse deleteAssignee(final Integer uniqueId, final String userName) throws IOException{
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", userName);
            }
        };
        return deleteItem(getUrlBasePrefix() + ".assignees.byUserName", UserResponse.class, null, paramMap);
    }

    public UserResponse deleteEscalatee(final Integer uniqueId, final String userName) throws IOException{
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", userName);
            }
        };
        return deleteItem(getUrlBasePrefix() + ".escalatees.byUserName", UserResponse.class, null, paramMap);
    }



    @Override
    public String getUrlType() { return "tasks"; }

    @Override
    public String getUrlBasePrefix() { return "v2.tasks"; }
}
