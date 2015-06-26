package com.threatconnect.sdk.client.reader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dtineo on 6/14/15.
 */
public class IterableResponse<V> implements Iterator<V>, Iterable<V>
{

    protected final Logger logger = Logger.getLogger(getClass().getSimpleName());

    private AbstractRequestExecutor executor;
    private String path;
    private Class responseType;
    private Class<V> responseItem;
    private int resultLimit = 500;
    private int index = 0;
    private ApiEntityListResponse currentResponse = null;
    private final ObjectMapper mapper = new ObjectMapper();

    public IterableResponse(AbstractRequestExecutor executor, Class responseType, Class<V> responseItem, String path, int resultLimit)
    {
        this.executor = executor;
        this.responseType = responseType;
        this.responseItem = responseItem;
        this.path = path;
        if ( resultLimit <= 0 )
        {
            throw new IllegalArgumentException("Result Limit must be > 0");
        }
        this.resultLimit = resultLimit;
        this.index = -1;
    }

    @Override
    public Iterator<V> iterator()
    {
        return this;
    }

    @Override
    public boolean hasNext()
    {
        index++;
        if (index%resultLimit == 0 )
        {
            currentResponse = getNextResponse();
        }

        Integer resultCount = currentResponse.getData().getResultCount();
        if ( resultCount == null )
        {
            resultCount = currentResponse.getData().getData().size();
        }
        return currentResponse != null && index%resultLimit < resultCount;
    }

    @Override
    public V next()
    {

        return responseItem.cast(currentResponse.getData().getData().get(index%resultLimit));
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    public ApiEntityListResponse getNextResponse()
    {

        String url = String.format("%s&resultStart=%d&resultLimit=%d", this.path, this.index, this.resultLimit);
        try
        {
            String content = executor.execute(AbstractRequestExecutor.HttpMethod.GET, url);
            logger.log(Level.FINEST, "returning content=" + content);
            ApiEntityListResponse result = (ApiEntityListResponse) mapper.readValue(content, this.responseType);
            if (!result.isSuccess()) {
                throw new FailedResponseException(result.getMessage());
            }

            return result;

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }

    }

}
