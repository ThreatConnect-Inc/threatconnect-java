/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service.error;

import com.cyber2.api.lib.server.response.service.ApiServiceResponse;

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
