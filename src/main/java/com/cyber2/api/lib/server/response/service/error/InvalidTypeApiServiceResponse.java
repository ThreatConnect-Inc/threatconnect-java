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
public class InvalidTypeApiServiceResponse extends ApiServiceResponse
{
    public InvalidTypeApiServiceResponse(String entityType, String type)
    {
        super("This " + entityType + " is not " + getIndefiniteArticle(type) + " " + type);
    }
}
