/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.server.response.service;

/**
 *
 * @author James
 */
public class ApiDataServiceResponse<T> extends ApiServiceResponse
{
    private final T selectedItem;
    
    public ApiDataServiceResponse(T selectedItem)
    {
        super();
        this.selectedItem = selectedItem;
    }

    public T getSelectedItem()
    {
        return selectedItem;
    }
}
