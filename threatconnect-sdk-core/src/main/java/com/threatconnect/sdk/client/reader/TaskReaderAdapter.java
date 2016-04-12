package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Task;
import com.threatconnect.sdk.server.entity.User;
import com.threatconnect.sdk.server.response.entity.TaskListResponse;
import com.threatconnect.sdk.server.response.entity.TaskResponse;
import com.threatconnect.sdk.server.response.entity.UserListResponse;
import com.threatconnect.sdk.server.response.entity.UserResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by moweis-ad on 3/15/16.
 */
public class TaskReaderAdapter extends AbstractGroupReaderAdapter<Task> {

    public TaskReaderAdapter(Connection conn) { super(conn, TaskResponse.class, Task.class, TaskListResponse.class); }

    public IterableResponse<User> getAssignees(final Integer uniqueId) throws IOException{
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {   put("id", uniqueId);  }
        };
        return getItems(getUrlBasePrefix() + ".assignees", UserListResponse.class, User.class, null, paramMap);
    }

    public IterableResponse<User> getEscalatees(final Integer uniqueId) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {   put("id", uniqueId);  }
        };
        return getItems(getUrlBasePrefix() + ".escalatees", UserListResponse.class, User.class, null, paramMap);
    }

    public UserResponse getAssignee(final Integer uniqueId, final String userName) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", userName);
            }
        };
        return getItem(getUrlBasePrefix() + ".assignees.byUserName", UserResponse.class, null, paramMap);
    }

    public UserResponse getEscalatee(final Integer uniqueId, final String userName) throws IOException {
        Map<String, Object> paramMap = new HashMap<String, Object>() {
            {
                put("id", uniqueId);
                put("userName", userName);
            }
        };
        return getItem(getUrlBasePrefix() + ".escalatees.byUserName", UserResponse.class, null, paramMap);
    }

    @Override
    public String getUrlBasePrefix() { return "v2.tasks";}

    @Override
    public String getUrlType() { return "tasks";}

}
