/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.service.error;

import com.threatconnect.sdk.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class BadRequestErrorApiServiceResponse extends ApiServiceResponse
{
    public BadRequestErrorApiServiceResponse()
    {
        super("The request was not properly formed");
    }
}
