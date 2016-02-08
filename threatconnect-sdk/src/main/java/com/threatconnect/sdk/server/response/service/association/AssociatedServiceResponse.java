/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threatconnect.sdk.server.response.service.association;

import com.threatconnect.sdk.server.response.service.ApiServiceResponse;
import com.threatconnect.sdk.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class AssociatedServiceResponse extends ApiServiceResponse
{
    public AssociatedServiceResponse(String type, String name, String modifier)
    {
        super(type + " \"" + name + "\" association added to " + modifier, true);
    }
    
    public AssociatedServiceResponse(String type, String name)
    {
        super(type + " \"" + name + "\" association added", true);
    }
    
    public AssociatedServiceResponse(String type)
    {
        super(type + " association added", true);
    }
}
