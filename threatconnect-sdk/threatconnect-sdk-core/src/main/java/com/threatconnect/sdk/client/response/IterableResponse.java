package com.threatconnect.sdk.client.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.response.entity.ApiEntityListResponse;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
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
    private Integer resultCount;
    private V nextItem;
    private String filters;
    private boolean orParams = false;

    public IterableResponse(AbstractRequestExecutor executor, Class responseType, Class<V> responseItem, String path, int resultLimit, String filters, boolean orParams)
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
        this.filters = filters;
        this.orParams = orParams;
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

        prepareResultCount();

        return prepareNext();
    }

    private boolean prepareNext()
    {
        boolean hasNext = currentResponse != null && index%resultLimit < resultCount;
        if ( hasNext )
        {
            int nextIndex = index%resultLimit;
            List dataList = currentResponse.getData().getData();
            if ( dataList == null || nextIndex >= dataList.size() )
            {
                hasNext = false;
            }
            else
            {
                nextItem = responseItem.cast(dataList.get(index % resultLimit));
            }
        }

        return hasNext;
    }

    private void prepareResultCount()
    {
        if ( resultCount == null )
        {
            resultCount = currentResponse.getData().getResultCount();
            if (resultCount == null)
            {
                resultCount = currentResponse.getData().getData().size();
            }
        }
    }

    @Override
    public V next()
    {

        return nextItem;
    }

    @Override
    public void remove()
    {
        throw new UnsupportedOperationException();
    }

    private ApiEntityListResponse getNextResponse()
    {

        String url = String.format("%s&resultStart=%d&resultLimit=%d", this.path, this.index, this.resultLimit);
        if (this.filters != null)
        {
            try
            {
                url = String.format("%s&filters=%s", url, URLEncoder.encode(this.filters, "UTF-8"));

                if (this.orParams)
                {
                    url = String.format("%s&orParams=true", url);
                }
            } catch (UnsupportedEncodingException e)
            {
                logger.warning("Unknown encoding escaping filter string.");
            }
        }

        try
        {
            String content = executor.execute(AbstractRequestExecutor.HttpMethod.GET, url).getEntityAsString();
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

    public Integer getResultCount()
    {
        return resultCount;
    }
}
