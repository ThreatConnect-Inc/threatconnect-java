/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service.error;

/**
 *
 * @author James
 */
public class LengthInvalidApiServiceResponse extends ErrorMessageApiServiceResponse
{
    public LengthInvalidApiServiceResponse(String entity, String field, int maxLength)
    {
        this(entity, field, 1, maxLength);
    }
    
    public LengthInvalidApiServiceResponse(String entity, String field, int minLength, int maxLength)
    {
        super("The " + field + " for this " + entity + " is invalid, please enter " + getIndefiniteArticle(field) + " " + field + " between " + minLength + "-" + maxLength + " characters in length");
    }
}
