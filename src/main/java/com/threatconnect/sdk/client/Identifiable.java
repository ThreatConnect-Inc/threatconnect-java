/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client;

/**
 *
 * @author dtineo
 * @param <T>
 */
public interface Identifiable<T,P> {
    public P getId(T item);
}
