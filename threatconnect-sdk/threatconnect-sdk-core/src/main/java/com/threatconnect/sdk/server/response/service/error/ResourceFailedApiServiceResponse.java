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
public class ResourceFailedApiServiceResponse extends ApiServiceResponse
{
    public ResourceFailedApiServiceResponse(String name, String operation)
    {
        super("The requested " + name + " was not " + operation + " - access was denied");
    }
}
