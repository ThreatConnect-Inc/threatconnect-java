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
public class ResourceNotFoundApiServiceResponse extends ApiServiceResponse
{
    public ResourceNotFoundApiServiceResponse()
    {
        super("The requested resource was not found");
    }
}
