package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.FalsePositive;
import com.threatconnect.sdk.server.response.entity.data.FalsePositiveResponseData;

public class FalsePositiveResponse extends ApiEntitySingleResponse<FalsePositive, FalsePositiveResponseData>
{
    
    protected void setData(FalsePositiveResponseData data)
    {
        super.setData(data);
    }

}
