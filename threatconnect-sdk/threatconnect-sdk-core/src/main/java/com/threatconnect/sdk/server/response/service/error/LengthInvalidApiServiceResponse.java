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
public class LengthInvalidApiServiceResponse extends ErrorMessageApiServiceResponse
{
    public LengthInvalidApiServiceResponse(String entity, String field, int maxLength)
    {
        this(entity, field, 1, maxLength);
    }
    
    public LengthInvalidApiServiceResponse(String entity, String field, int minLength, int maxLength)
    {
        super("The " + field + " for this " + entity + " is invalid, please enter " + ApiServiceResponse.getIndefiniteArticle(field) + " " + field + " between " + minLength + "-" + maxLength + " characters in length");
    }
}
