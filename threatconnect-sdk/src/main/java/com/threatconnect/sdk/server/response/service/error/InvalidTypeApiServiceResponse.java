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
public class InvalidTypeApiServiceResponse extends ApiServiceResponse
{
    public InvalidTypeApiServiceResponse(String entityType, String type)
    {
        super("This " + entityType + " is not " + getIndefiniteArticle(type) + " " + type);
    }
}
