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
public class TimestampInvalidApiServiceResponse extends ApiServiceResponse
{
    public TimestampInvalidApiServiceResponse()
    {
        super("Invalid timestamp");
    }
}
