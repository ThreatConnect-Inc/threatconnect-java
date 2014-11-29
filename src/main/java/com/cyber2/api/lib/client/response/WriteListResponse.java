/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.response;

import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dtineo
 * @param <ApiSingleEntityResponse>
 * @param <T>
 */
public class WriteListResponse<ApiSingleEntityResponse> {
    private List<ApiEntitySingleResponse> successList = new ArrayList<>();
    private List<ApiEntitySingleResponse> failureList = new ArrayList<>();

    /**
     * @return the successList
     */
    public List<ApiEntitySingleResponse> getSuccessList() {
        return successList;
    }

    /**
     * @return the failureList
     */
    public List<ApiEntitySingleResponse> getFailureList() {
        return failureList;
    }


}