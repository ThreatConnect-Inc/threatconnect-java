/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.response.entity.AddressListResponse;
import com.cyber2.api.lib.server.response.entity.AddressResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressListResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.EmailListResponse;
import com.cyber2.api.lib.server.response.entity.EmailResponse;
import com.cyber2.api.lib.server.response.entity.FileListResponse;
import com.cyber2.api.lib.server.response.entity.FileResponse;
import com.cyber2.api.lib.server.response.entity.HostListResponse;
import com.cyber2.api.lib.server.response.entity.HostResponse;
import com.cyber2.api.lib.server.response.entity.IncidentListResponse;
import com.cyber2.api.lib.server.response.entity.IncidentResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import com.cyber2.api.lib.server.response.entity.TagListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.ThreatListResponse;
import com.cyber2.api.lib.server.response.entity.ThreatResponse;
import com.cyber2.api.lib.server.response.entity.UrlListResponse;
import com.cyber2.api.lib.server.response.entity.UrlResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class TagWriterAdapter extends AbstractWriterAdapter {

    protected TagWriterAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor);
    }

    public WriteListResponse<Tag> create(List<Tag> tagList) throws IOException, FailedResponseException {

        WriteListResponse<Tag> data = createList("v2.tags", TagListResponse.class, tagList);

        return data;
    }

    public Tag create(Tag tag) throws IOException, FailedResponseException {
        return create(tag, null);
    }

    public Tag create(Tag tag, String ownerName)
        throws IOException, FailedResponseException {

        TagResponse item = createItem("v2.tags", TagResponse.class, ownerName, null, tag);

        return item.isSuccess() ? tag : null;
    }

    public WriteListResponse<Tag> update(List<Tag> tagList) throws IOException, FailedResponseException {

        List<String> idList = new ArrayList<>();
        for (Tag it : tagList) {
            idList.add(it.getName());
        }
        WriteListResponse<Tag> data = updateListWithParam("v2.tags.byId", TagListResponse.class, null, null, "id", idList, tagList);

        return data;
    }

    public Tag update(Tag tag) throws IOException, FailedResponseException {
        return update(tag, null);
    }

    public Tag update(Tag tag, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tag.getName());
        TagResponse item = updateItem("v2.tags.byId", TagResponse.class, ownerName, map, tag);

        return item.isSuccess() ? tag : null;
    }

    public WriteListResponse<Tag> delete(List<String> tagNameList) throws IOException, FailedResponseException {
        return delete(tagNameList, null);
    }

    public WriteListResponse<Tag> delete(List<String> tagNameList, String ownerName) throws IOException, FailedResponseException {

        List<String> idList = new ArrayList<>();
        for (String it : tagNameList) {
            idList.add(it);
        }
        WriteListResponse<Tag> data = deleteList("v2.tags.byId", TagListResponse.class, ownerName, null, "id", idList);

        return data;
    }

    public void delete(String tagName) throws IOException, FailedResponseException {
        delete(tagName, null);
    }

    public void delete(String tagName, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName);
        deleteItem("v2.tags.byId", TagResponse.class, ownerName, map);

    }

    public WriteListResponse<Adversary> associateAdversaries(String tagName, List<Integer> associateAdversaryIdList) throws IOException {
        return associateAdversaries(tagName, associateAdversaryIdList, null);
    }

    public WriteListResponse<Adversary> associateAdversaries(String tagName, List<Integer> associateAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = createListWithParam("v2.tags.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associateAdversaryIdList);

        return data;
    }

    public boolean associateAdversary(String tagName, Integer adversaryId) throws IOException, FailedResponseException {
        return associateAdversary(tagName, adversaryId, null);
    }

    public boolean associateAdversary(String tagName, Integer adversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", adversaryId);
        AdversaryResponse data = createItem("v2.tags.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<Email> associateEmails(String tagName, List<Integer> associateEmailIdList) throws IOException {
        return associateEmails(tagName, associateEmailIdList, null);
    }

    public WriteListResponse<Email> associateEmails(String tagName, List<Integer> associateEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = createListWithParam("v2.tags.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associateEmailIdList);

        return data;
    }

    public boolean associateEmail(String tagName, Integer emailId) throws IOException, FailedResponseException {
        return associateEmail(tagName, emailId, null);
    }

    public boolean associateEmail(String tagName, Integer emailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", emailId);
        EmailResponse data = createItem("v2.tags.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<Incident> associateIncidents(String tagName, List<Integer> associateIncidentIdList) throws IOException {
        return associateIncidents(tagName, associateIncidentIdList, null);
    }

    public WriteListResponse<Incident> associateIncidents(String tagName, List<Integer> associateIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = createListWithParam("v2.tags.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associateIncidentIdList);

        return data;
    }

    public boolean associateIncident(String tagName, Integer incidentId) throws IOException, FailedResponseException {
        return associateIncident(tagName, incidentId, null);
    }

    public boolean associateIncident(String tagName, Integer incidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", incidentId);
        IncidentResponse data = createItem("v2.tags.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<Signature> associateSignatures(String tagName, List<Integer> associateSignatureIdList) throws IOException {
        return associateSignatures(tagName, associateSignatureIdList, null);
    }

    public WriteListResponse<Signature> associateSignatures(String tagName, List<Integer> associateSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = createListWithParam("v2.tags.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associateSignatureIdList);

        return data;
    }

    public Signature associateSignature(String tagName, Integer signatureId) throws IOException, FailedResponseException {
        return associateSignature(tagName, signatureId, null);
    }

    public Signature associateSignature(String tagName, Integer signatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", signatureId);
        SignatureResponse data = createItem("v2.tags.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return (Signature) data.getData().getData();
    }

    public WriteListResponse<Threat> associateThreats(String tagName, List<Integer> associateThreatIdList) throws IOException {
        return associateThreats(tagName, associateThreatIdList, null);
    }

    public WriteListResponse<Threat> associateThreats(String tagName, List<Integer> associateThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = createListWithParam("v2.tags.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associateThreatIdList);

        return data;
    }

    public Threat associateThreat(String tagName, Integer threatId) throws IOException, FailedResponseException {
        return associateThreat(tagName, threatId, null);
    }

    public Threat associateThreat(String tagName, Integer threatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", threatId);
        ThreatResponse data = createItem("v2.tags.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return (Threat) data.getData().getData();
    }

    public WriteListResponse<Address> associateIndicatorAddresss(String tagName, List<String> associateAddressList) throws IOException {
        return associateIndicatorAddresss(tagName, associateAddressList, null);
    }

    public WriteListResponse<Address> associateIndicatorAddresss(String tagName, List<String> associateAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<Address> data = createListWithParam("v2.tags.type.byId.indicators.addresses.byGroupId", AddressListResponse.class, ownerName, map, "tagName", associateAddressList);

        return data;
    }

    public Address associateIndicatorAddress(String tagName, String associateAddress) throws IOException, FailedResponseException {
        return associateIndicatorAddress(tagName, associateAddress, null);
    }

    public Address associateIndicatorAddress(String tagName, String associateAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateAddress);
        AddressResponse data = createItem("v2.tags.type.byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map, null);

        return (Address) data.getData().getData();
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(String tagName, List<String> associateEmailAddressList) throws IOException {
        return associateIndicatorEmailAddresses(tagName, associateEmailAddressList, null);
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(String tagName, List<String> associateEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<EmailAddress> data = createListWithParam("v2.tags.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressListResponse.class, ownerName, map, "tagName", associateEmailAddressList);

        return data;
    }

    public EmailAddress associateIndicatorEmailAddress(String tagName, String associateEmailAddress) throws IOException, FailedResponseException {
        return associateIndicatorEmailAddress(tagName, associateEmailAddress, null);
    }

    public EmailAddress associateIndicatorEmailAddress(String tagName, String associateEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateEmailAddress);
        EmailAddressResponse data = createItem("v2.tags.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<File> associateIndicatorFiles(String tagName, List<String> associateFileList) throws IOException {
        return associateIndicatorFiles(tagName, associateFileList, null);
    }

    public WriteListResponse<File> associateIndicatorFiles(String tagName, List<String> associateFileList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<File> data = createListWithParam("v2.tags.type.byId.indicators.files.byIndicatorId", FileListResponse.class, ownerName, map, "tagName", associateFileList);

        return data;
    }

    public File associateIndicatorFile(String tagName, String associateFile) throws IOException, FailedResponseException {
        return associateIndicatorFile(tagName, associateFile, null);
    }

    public File associateIndicatorFile(String tagName, String associateFile, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateFile);
        FileResponse data = createItem("v2.tags.type.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, null);

        return (File) data.getData().getData();
    }

    public WriteListResponse<Host> associateIndicatorHosts(String tagName, List<String> associateHostList) throws IOException {
        return associateIndicatorHosts(tagName, associateHostList, null);
    }

    public WriteListResponse<Host> associateIndicatorHosts(String tagName, List<String> associateHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<Host> data = createListWithParam("v2.tags.type.byId.indicators.hosts.byIndicatorId", HostListResponse.class, ownerName, map, "tagName", associateHostList);

        return data;
    }

    public Host associateIndicatorHost(String tagName, String associateHost) throws IOException, FailedResponseException {
        return associateIndicatorHost(tagName, associateHost, null);
    }

    public Host associateIndicatorHost(String tagName, String associateHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateHost);
        HostResponse data = createItem("v2.tags.type.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, null);

        return (Host) data.getData().getData();
    }

    public WriteListResponse<Url> associateIndicatorUrls(String tagName, List<String> associateUrlList) throws IOException {
        return associateIndicatorUrls(tagName, associateUrlList, null);
    }

    public WriteListResponse<Url> associateIndicatorUrls(String tagName, List<String> associateUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<Url> data = createListWithParam("v2.tags.type.byId.indicators.urls.byIndicatorId", UrlListResponse.class, ownerName, map, "tagName", associateUrlList);

        return data;
    }

    public Url associateIndicatorUrl(String tagName, String associateUrl) throws IOException, FailedResponseException {
        return associateIndicatorUrl(tagName, associateUrl, null);
    }

    public Url associateIndicatorUrl(String tagName, String associateUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateUrl);
        UrlResponse data = createItem("v2.tags.type.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, null);

        return (Url) data.getData().getData();
    }

    public WriteListResponse<Integer> deleteAssociationAdversaries(String tagName, List<Integer> associatedAdversaryIdList) throws IOException {
        return deleteAssociationAdversaries(tagName, associatedAdversaryIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationAdversaries(String tagName, List<Integer> associatedAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = deleteList("v2.tags.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associatedAdversaryIdList);

        return data;
    }

    public void deleteAssociationAdversary(String tagName, Integer adversaryId) throws IOException, FailedResponseException {
        deleteAssociationAdversary(tagName, adversaryId, null);
    }

    public void deleteAssociationAdversary(String tagName, Integer adversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", adversaryId);
        deleteItem("v2.tags.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationEmails(String tagName, List<Integer> associatedEmailIdList) throws IOException {
        return deleteAssociationEmails(tagName, associatedEmailIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationEmails(String tagName, List<Integer> associatedEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = deleteList("v2.tags.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associatedEmailIdList);

        return data;
    }

    public void deleteAssociationEmail(String tagName, Integer emailId) throws IOException, FailedResponseException {
        deleteAssociationEmail(tagName, emailId, null);
    }

    public void deleteAssociationEmail(String tagName, Integer emailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", emailId);
        deleteItem("v2.tags.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationIncidents(String tagName, List<Integer> associatedIncidentIdList) throws IOException {
        return deleteAssociationIncidents(tagName, associatedIncidentIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationIncidents(String tagName, List<Integer> associatedIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = deleteList("v2.tags.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associatedIncidentIdList);

        return data;
    }

    public void deleteAssociationIncident(String tagName, Integer incidentId) throws IOException, FailedResponseException {
        deleteAssociationIncident(tagName, incidentId, null);
    }

    public void deleteAssociationIncident(String tagName, Integer incidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", incidentId);
        deleteItem("v2.tags.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);
    }


    public WriteListResponse<Integer> deleteAssociationSignatures(String tagName, List<Integer> associatedSignatureIdList) throws IOException {
        return deleteAssociationSignatures(tagName, associatedSignatureIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationSignatures(String tagName, List<Integer> associatedSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = deleteList("v2.tags.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associatedSignatureIdList);

        return data;
    }

    public void deleteAssociationSignature(String tagName, Integer signatureId) throws IOException, FailedResponseException {
        deleteAssociationSignature(tagName, signatureId, null);
    }

    public void deleteAssociationSignature(String tagName, Integer signatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", signatureId);
        deleteItem("v2.tags.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationThreats(String tagName, List<Integer> associatedThreatIdList) throws IOException {
        return deleteAssociationThreats(tagName, associatedThreatIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationThreats(String tagName, List<Integer> associatedThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse data = deleteList("v2.tags.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associatedThreatIdList);

        return data;
    }

    public void deleteAssociationThreat(String tagName, Integer threatId) throws IOException, FailedResponseException {
        deleteAssociationThreat(tagName, threatId, null);
    }

    public void deleteAssociationThreat(String tagName, Integer threatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "groupId", threatId);
        deleteItem("v2.tags.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

    }


    public WriteListResponse<String> deleteAssociationIndicatorAddresses(String tagName, List<String> associateAddressList) throws IOException {
        return deleteAssociationIndicatorAddresses(tagName, associateAddressList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorAddresses(String tagName, List<String> associateAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<String> data = createListWithParam("v2.tags.type.byId.indicators.addresses.byIndicatorId", AddressListResponse.class, ownerName, map, "tagName", associateAddressList);

        return data;
    }

    public void deleteAssociationIndicatorAddress(String tagName, String associateAddress) throws IOException, FailedResponseException {
        deleteAssociationIndicatorAddress(tagName, associateAddress, null);
    }

    public void deleteAssociationIndicatorAddress(String tagName, String associateAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateAddress);
        deleteItem("v2.tags.type.byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorEmailAddresses(String tagName, List<String> associateEmailAddressList) throws IOException {
        return deleteAssociationIndicatorEmailAddresses(tagName, associateEmailAddressList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorEmailAddresses(String tagName, List<String> associateEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<String> data = createListWithParam("v2.tags.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressListResponse.class, ownerName, map, "tagName", associateEmailAddressList);

        return data;
    }

    public void deleteAssociationIndicatorEmailAddress(String tagName, String associateEmailAddress) throws IOException, FailedResponseException {
        deleteAssociationIndicatorEmailAddress(tagName, associateEmailAddress, null);
    }

    public void deleteAssociationIndicatorEmailAddress(String tagName, String associateEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateEmailAddress);
        deleteItem("v2.tags.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorFiles(String tagName, List<String> associateFileList) throws IOException {
        return deleteAssociationIndicatorFiles(tagName, associateFileList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorFiles(String tagName, List<String> associateFileList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<String> data = createListWithParam("v2.tags.type.byId.indicators.files.byIndicatorId", FileListResponse.class, ownerName, map, "tagName", associateFileList);

        return data;
    }

    public void deleteAssociationIndicatorFile(String tagName, String associateFile) throws IOException, FailedResponseException {
        deleteAssociationIndicatorFile(tagName, associateFile, null);
    }

    public void deleteAssociationIndicatorFile(String tagName, String associateFile, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateFile);
        deleteItem("v2.tags.type.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorHosts(String tagName, List<String> associateHostList) throws IOException {
        return deleteAssociationIndicatorHosts(tagName, associateHostList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorHosts(String tagName, List<String> associateHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<String> data = createListWithParam("v2.tags.type.byId.indicators.hosts.byIndicatorId", HostListResponse.class, ownerName, map, "tagName", associateHostList);

        return data;
    }

    public void deleteAssociationIndicatorHost(String tagName, String associateHost) throws IOException, FailedResponseException {
        deleteAssociationIndicatorHost(tagName, associateHost, null);
    }

    public void deleteAssociationIndicatorHost(String tagName, String associateHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateHost);
        deleteItem("v2.tags.type.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map);

    }

    
    public WriteListResponse<String> deleteAssociationIndicatorUrls(String tagName, List<String> associateUrlList) throws IOException {
        return deleteAssociationIndicatorUrls(tagName, associateUrlList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorUrls(String tagName, List<String> associateUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", tagName);
        WriteListResponse<String> data = createListWithParam("v2.tags.type.byId.indicators.urls.byIndicatorId", UrlListResponse.class, ownerName, map, "tagName", associateUrlList);

        return data;
    }

    public void deleteAssociationIndicatorUrl(String tagName, String associateUrl) throws IOException, FailedResponseException {
        deleteAssociationIndicatorUrl(tagName, associateUrl, null);
    }

    public void deleteAssociationIndicatorUrl(String tagName, String associateUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", tagName, "indicatorId", associateUrl);
        deleteItem("v2.tags.type.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map);

    }
}
