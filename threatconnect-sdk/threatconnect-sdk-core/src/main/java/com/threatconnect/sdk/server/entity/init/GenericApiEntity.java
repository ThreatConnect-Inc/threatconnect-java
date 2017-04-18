/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.entity.init;

/**
 *
 * @author James
 */
public class GenericApiEntity extends ApiEntityInit
{
    public GenericApiEntity(String systemUrl)
    {
        super(false, true, systemUrl);
    }
}
