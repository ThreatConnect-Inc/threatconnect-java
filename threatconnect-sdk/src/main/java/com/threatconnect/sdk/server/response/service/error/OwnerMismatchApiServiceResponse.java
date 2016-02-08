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
public class OwnerMismatchApiServiceResponse extends ApiServiceResponse
{
    public OwnerMismatchApiServiceResponse()
    {
        super("The owners of the requested objects were different");
    }
}
