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
public class InvalidAssociationServiceResponse extends ApiServiceResponse
{
    public InvalidAssociationServiceResponse(String type1, String type2)
    {
        super("Invalid association, " + getIndefiniteArticle(type1) + " " + type1 + " cannot be associated with " + getIndefiniteArticle(type2) +" " + type2, false);
    }
    
    public InvalidAssociationServiceResponse()
    {
        super("Invalid association", false);
    }
}
