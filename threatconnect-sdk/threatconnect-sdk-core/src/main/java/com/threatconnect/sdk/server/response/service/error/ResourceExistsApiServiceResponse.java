/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.service.error;

/**
 *
 * @author James
 */
public class ResourceExistsApiServiceResponse extends ErrorMessageApiServiceResponse
{
    public ResourceExistsApiServiceResponse(String entity, String field)
    {
        super("The " + field + " for this " + entity + " is invalid, " + getIndefiniteArticle(entity) + " " + entity + " with this " + field + " already exists");
    }
}
