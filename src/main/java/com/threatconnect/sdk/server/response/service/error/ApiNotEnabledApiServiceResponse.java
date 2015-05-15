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
public class ApiNotEnabledApiServiceResponse extends ApiServiceResponse
{
    public ApiNotEnabledApiServiceResponse()
    {
        super("API access has not been licensed for this system, please contact sales@threatconnect.com if you wish to enable it");
    }
}
