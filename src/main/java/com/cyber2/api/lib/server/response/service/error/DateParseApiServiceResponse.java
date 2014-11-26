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
public class DateParseApiServiceResponse extends ApiServiceResponse
{
    public DateParseApiServiceResponse()
    {
        this("date");
    }
    
    public DateParseApiServiceResponse(String dateDescriptor)
    {
        super("Please enter the " + dateDescriptor + " in number of milliseconds since January 1, 1970, 00:00:00 GMT");
    }
}
