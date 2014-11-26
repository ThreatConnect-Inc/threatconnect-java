/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service.error;

/**
 *
 * @author James
 */
public class TypeInvalidApiServiceResponse extends ErrorMessageApiServiceResponse
{
    public TypeInvalidApiServiceResponse(String entity, String field)
    {
        this(entity, field, null);
    }
    
    public TypeInvalidApiServiceResponse(String entity, String field, String validValues)
    {
        super("The " + field + " for this " + entity + " is invalid" + (validValues == null ? "" : " , please enter one of the following values: " + validValues));
    }
}
