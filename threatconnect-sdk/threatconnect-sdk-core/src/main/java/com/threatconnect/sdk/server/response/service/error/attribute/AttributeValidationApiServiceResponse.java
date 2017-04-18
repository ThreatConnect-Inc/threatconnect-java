/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.service.error.attribute;

import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class AttributeValidationApiServiceResponse extends ApiServiceResponse
{
    public AttributeValidationApiServiceResponse(String errorMessage)
    {
        super(errorMessage); 
    }

}
