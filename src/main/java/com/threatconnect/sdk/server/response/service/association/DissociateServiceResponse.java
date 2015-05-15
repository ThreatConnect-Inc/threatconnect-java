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
public class DissociateServiceResponse extends ApiServiceResponse
{
    public DissociateServiceResponse(String type, String name, String modifier)
    {
        super(type + " \"" + name + "\" association removed from " + modifier, true);
    }
    
    public DissociateServiceResponse(String type, String name)
    {
        super(type + " \"" + name + "\" association removed", true);
    }
    
    public DissociateServiceResponse(String type)
    {
        super(type + " association removed", true);
    }
}
