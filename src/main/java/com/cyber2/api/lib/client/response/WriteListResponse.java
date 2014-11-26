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
 * @param <T>
 */
public class WriteListResponse<T> {
    private List<T> successList = new ArrayList<>();
    private List<T> failureList = new ArrayList<>();
    private List<String> failureMessageList = new ArrayList<>();

    /**
     * @return the successList
     */
    public List<T> getSuccessList() {
        return successList;
    }

    /**
     * @param successList the successList to set
     */
    public void setSuccessList(List<T> successList) {
        this.successList = successList;
    }

    /**
     * @return the failureList
     */
    public List<T> getFailureList() {
        return failureList;
    }

    /**
     * @param failureList the failureList to set
     */
    public void setFailureList(List<T> failureList) {
        this.failureList = failureList;
    }

    /**
     * @return the failureMessageList
     */
    public List<String> getFailureMessageList() {
        return failureMessageList;
    }

    /**
     * @param failureMessageList the failureMessageList to set
     */
    public void setFailureMessageList(List<String> failureMessageList) {
        this.failureMessageList = failureMessageList;
    }
}
