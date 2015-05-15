/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.service.error;

import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class PaginationParseApiServiceResponse extends ApiServiceResponse
{
    public PaginationParseApiServiceResponse()
    {
        super("The resultStart and resultLimit query parameters must be integers and the maximum value for resultLimit is 500");
    }
}
