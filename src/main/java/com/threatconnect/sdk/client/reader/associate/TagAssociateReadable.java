/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader.associate;

import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.entity.Tag;

import java.io.IOException;
import java.util.List;

/**
 *
 * @author dtineo
 */
public interface TagAssociateReadable<P> {

   public List<Tag> getAssociatedTags(P uniqueId) throws IOException, FailedResponseException;

   public List<Tag> getAssociatedTags(P uniqueId, String ownerName) 
            throws IOException, FailedResponseException;

   public Tag getAssociatedTag(P uniqueId, String tagName) throws IOException, FailedResponseException;

   public Tag getAssociatedTag(P uniqueId, String tagName, String ownerName) 
            throws IOException, FailedResponseException;
    
}
