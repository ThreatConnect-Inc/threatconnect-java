/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.threatconnect.sdk.server.response.service;

/**
 *
 * @author James
 */
public class DeleteServiceResponse extends ApiServiceResponse
{
    public DeleteServiceResponse(String type, String name)
    {
        super(type + " \"" + name + "\" deleted", true);
    }
    
    public DeleteServiceResponse(String type)
    {
        super(type + " deleted", true);
    }
}
