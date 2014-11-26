/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.cyber2.api.lib.server.response.service.association;

import com.cyber2.api.lib.server.response.service.ApiServiceResponse;

/**
 *
 * @author James
 */
public class DissociatedServiceResponse extends ApiServiceResponse
{
    public DissociatedServiceResponse(String type, String name, String modifier)
    {
        super(type + " \"" + name + "\" association removed from " + modifier, true);
    }
    
    public DissociatedServiceResponse(String type, String name)
    {
        super(type + " \"" + name + "\" association removed", true);
    }
    
    public DissociatedServiceResponse(String type)
    {
        super(type + " association removed", true);
    }
}
