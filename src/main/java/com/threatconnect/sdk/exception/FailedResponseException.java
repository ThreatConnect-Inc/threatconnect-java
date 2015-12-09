/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.exception;

/**
 *
 * @author dtineo
 */
public class FailedResponseException extends RuntimeException {
    public FailedResponseException(String msg) {
        super(msg);
    }
}
