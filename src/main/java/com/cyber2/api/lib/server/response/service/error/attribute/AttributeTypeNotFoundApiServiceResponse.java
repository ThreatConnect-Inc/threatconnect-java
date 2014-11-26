/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service.error.attribute;

import com.cyber2.api.lib.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class AttributeTypeNotFoundApiServiceResponse extends ApiServiceResponse
{
    public AttributeTypeNotFoundApiServiceResponse()
    {
        super("The requested attribute type was not found");
    }
}
