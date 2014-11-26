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
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.SecurityLabel;
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
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelListResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
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
public class SecurityLabelWriterAdapter extends AbstractWriterAdapter {

    protected SecurityLabelWriterAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor);
    }

    public WriteListResponse<SecurityLabel> create(List<SecurityLabel> securityLabelList) throws IOException, FailedResponseException {

        WriteListResponse<SecurityLabel> data = createList("v2.securityLabels", SecurityLabelListResponse.class, securityLabelList);

        return data;
    }

    public SecurityLabel create(SecurityLabel securityLabel) throws IOException, FailedResponseException {
        return create(securityLabel, null);
    }

    public SecurityLabel create(SecurityLabel securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        SecurityLabelResponse item = createItem("v2.securityLabels", SecurityLabelResponse.class, ownerName, null, securityLabel);

        return (SecurityLabel) item.getData().getData();
    }

    public WriteListResponse<SecurityLabel> update(List<SecurityLabel> securityLabelList) throws IOException, FailedResponseException {
        return update(securityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> update(List<SecurityLabel> securityLabelList, String ownerName) throws IOException, FailedResponseException {
        List<String> idList = new ArrayList<>();
        for (SecurityLabel it : securityLabelList) {
            idList.add(it.getName());
        }
        WriteListResponse<SecurityLabel> data = updateListWithParam("v2.securityLabels.byId", SecurityLabelListResponse.class, null, null, "id", idList, securityLabelList);

        return data;
    }

    public WriteListResponse<SecurityLabel> delete(List<SecurityLabel> securityLabelList) throws IOException, FailedResponseException {
        return delete(securityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> delete(List<SecurityLabel> securityLabelList, String ownerName) throws IOException, FailedResponseException {
        List<String> idList = new ArrayList<>();
        for (SecurityLabel it : securityLabelList) {
            idList.add(it.getName());
        }
        WriteListResponse<SecurityLabel> data = deleteList("v2.securityLabels.byId", SecurityLabelListResponse.class, ownerName, null, "id", idList);

        return data;
    }

    public SecurityLabel update(SecurityLabel securityLabel) throws IOException, FailedResponseException {
        return update(securityLabel, null);
    }

    public SecurityLabel update(SecurityLabel securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabel.getName());
        SecurityLabelResponse item = updateItem("v2.securityLabels.byId", SecurityLabelResponse.class, ownerName, map, securityLabel);

        return (SecurityLabel) item.getData().getData();
    }

    public void delete(SecurityLabel securityLabel) throws IOException, FailedResponseException {
        delete(securityLabel, null);
    }

    public void delete(SecurityLabel securityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabel.getName());
        deleteItem("v2.securityLabels.byId", SecurityLabelResponse.class, ownerName, map);

    }

    public WriteListResponse<Adversary> associateAdversaries(String securityLabelName, List<Integer> associateAdversaryIdList) throws IOException {
        return associateAdversaries(securityLabelName, associateAdversaryIdList, null);
    }

    public WriteListResponse<Adversary> associateAdversaries(String securityLabelName, List<Integer> associateAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse data = createListWithParam("v2.securityLabels.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associateAdversaryIdList);

        return data;
    }

    public Adversary associateAdversary(String securityLabelName, Integer adversaryId) throws IOException, FailedResponseException {
        return associateAdversary(securityLabelName, adversaryId, null);
    }

    public Adversary associateAdversary(String securityLabelName, Integer adversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", adversaryId);
        AdversaryResponse data = createItem("v2.securityLabels.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return (Adversary) data.getData().getData();
    }

    public WriteListResponse<Email> associateEmails(String securityLabelName, List<Integer> associateEmailIdList) throws IOException {
        return associateEmails(securityLabelName, associateEmailIdList, null);
    }

    public WriteListResponse<Email> associateEmails(String securityLabelName, List<Integer> associateEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse data = createListWithParam("v2.securityLabels.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associateEmailIdList);

        return data;
    }

    public Email associateEmail(String securityLabelName, Integer emailId) throws IOException, FailedResponseException {
        return associateEmail(securityLabelName, emailId, null);
    }

    public Email associateEmail(String securityLabelName, Integer emailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", emailId);
        EmailResponse data = createItem("v2.securityLabels.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return (Email) data.getData().getData();
    }

    public WriteListResponse<Incident> associateIncidents(String securityLabelName, List<Integer> associateIncidentIdList) throws IOException {
        return associateIncidents(securityLabelName, associateIncidentIdList, null);
    }

    public WriteListResponse<Incident> associateIncidents(String securityLabelName, List<Integer> associateIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse data = createListWithParam("v2.securityLabels.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associateIncidentIdList);

        return data;
    }

    public Incident associateIncident(String securityLabelName, Integer incidentId) throws IOException, FailedResponseException {
        return associateIncident(securityLabelName, incidentId, null);
    }

    public Incident associateIncident(String securityLabelName, Integer incidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", incidentId);
        IncidentResponse data = createItem("v2.securityLabels.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return (Incident) data.getData().getData();
    }

    public WriteListResponse<Signature> associateSignatures(String securityLabelName, List<Integer> associateSignatureIdList) throws IOException {
        return associateSignatures(securityLabelName, associateSignatureIdList, null);
    }

    public WriteListResponse<Signature> associateSignatures(String securityLabelName, List<Integer> associateSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse data = createListWithParam("v2.securityLabels.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associateSignatureIdList);

        return data;
    }

    public Signature associateSignature(String securityLabelName, Integer signatureId) throws IOException, FailedResponseException {
        return associateSignature(securityLabelName, signatureId, null);
    }

    public Signature associateSignature(String securityLabelName, Integer signatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", signatureId);
        SignatureResponse data = createItem("v2.securityLabels.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return (Signature) data.getData().getData();
    }

    public WriteListResponse<Threat> associateThreats(String securityLabelName, List<Integer> associateThreatIdList) throws IOException {
        return associateThreats(securityLabelName, associateThreatIdList, null);
    }

    public WriteListResponse<Threat> associateThreats(String securityLabelName, List<Integer> associateThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse data = createListWithParam("v2.securityLabels.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associateThreatIdList);

        return data;
    }

    public Threat associateThreat(String securityLabelName, Integer threatId) throws IOException, FailedResponseException {
        return associateThreat(securityLabelName, threatId, null);
    }

    public Threat associateThreat(String securityLabelName, Integer threatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", threatId);
        ThreatResponse data = createItem("v2.securityLabels.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return (Threat) data.getData().getData();
    }

    public WriteListResponse<Address> associateIndicatorAddresss(String securityLabelName, List<String> associateAddressList) throws IOException {
        return associateIndicatorAddresss(securityLabelName, associateAddressList, null);
    }

    public WriteListResponse<Address> associateIndicatorAddresss(String securityLabelName, List<String> associateAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Address> data = createListWithParam("v2.securityLabels.byId.indicators.addresses.byGroupId", AddressListResponse.class, ownerName, map, "securityLabelName", associateAddressList);

        return data;
    }

    public Address associateIndicatorAddress(String securityLabelName, String associateAddress) throws IOException, FailedResponseException {
        return associateIndicatorAddress(securityLabelName, associateAddress, null);
    }

    public Address associateIndicatorAddress(String securityLabelName, String associateAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associateAddress);
        AddressResponse data = createItem("v2.securityLabels.byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map, null);

        return (Address) data.getData().getData();
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(String securityLabelName, List<String> associateEmailAddressList) throws IOException {
        return associateIndicatorEmailAddresses(securityLabelName, associateEmailAddressList, null);
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(String securityLabelName, List<String> associateEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<EmailAddress> data = createListWithParam("v2.securityLabels.byId.indicators.emailAddresses.byIndicatorId", EmailAddressListResponse.class, ownerName, map, "securityLabelName", associateEmailAddressList);

        return data;
    }

    public EmailAddress associateIndicatorEmailAddress(String securityLabelName, String associateEmailAddress) throws IOException, FailedResponseException {
        return associateIndicatorEmailAddress(securityLabelName, associateEmailAddress, null);
    }

    public EmailAddress associateIndicatorEmailAddress(String securityLabelName, String associateEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associateEmailAddress);
        EmailAddressResponse data = createItem("v2.securityLabels.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<File> associateIndicatorFiles(String securityLabelName, List<String> associateFileList) throws IOException {
        return associateIndicatorFiles(securityLabelName, associateFileList, null);
    }

    public WriteListResponse<File> associateIndicatorFiles(String securityLabelName, List<String> associateFileList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<File> data = createListWithParam("v2.securityLabels.byId.indicators.files.byIndicatorId", FileListResponse.class, ownerName, map, "securityLabelName", associateFileList);

        return data;
    }

    public File associateIndicatorFile(String securityLabelName, String associateFile) throws IOException, FailedResponseException {
        return associateIndicatorFile(securityLabelName, associateFile, null);
    }

    public File associateIndicatorFile(String securityLabelName, String associateFile, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associateFile);
        FileResponse data = createItem("v2.securityLabels.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, null);

        return (File) data.getData().getData();
    }

    public WriteListResponse<Host> associateIndicatorHosts(String securityLabelName, List<String> associateHostList) throws IOException {
        return associateIndicatorHosts(securityLabelName, associateHostList, null);
    }

    public WriteListResponse<Host> associateIndicatorHosts(String securityLabelName, List<String> associateHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Host> data = createListWithParam("v2.securityLabels.byId.indicators.hosts.byIndicatorId", HostListResponse.class, ownerName, map, "securityLabelName", associateHostList);

        return data;
    }

    public Host associateIndicatorHost(String securityLabelName, String associateHost) throws IOException, FailedResponseException {
        return associateIndicatorHost(securityLabelName, associateHost, null);
    }

    public Host associateIndicatorHost(String securityLabelName, String associateHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associateHost);
        HostResponse data = createItem("v2.securityLabels.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, null);

        return (Host) data.getData().getData();
    }

    public WriteListResponse<Url> associateIndicatorUrls(String securityLabelName, List<String> associateUrlList) throws IOException {
        return associateIndicatorUrls(securityLabelName, associateUrlList, null);
    }

    public WriteListResponse<Url> associateIndicatorUrls(String securityLabelName, List<String> associateUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Url> data = createListWithParam("v2.securityLabels.byId.indicators.urls.byIndicatorId", UrlListResponse.class, ownerName, map, "securityLabelName", associateUrlList);

        return data;
    }

    public Url associateIndicatorUrl(String securityLabelName, String associateUrl) throws IOException, FailedResponseException {
        return associateIndicatorUrl(securityLabelName, associateUrl, null);
    }

    public Url associateIndicatorUrl(String securityLabelName, String associateUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associateUrl);
        UrlResponse data = createItem("v2.securityLabels.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, null);

        return (Url) data.getData().getData();
    }

    public WriteListResponse<Integer> deleteAssociationAdversaries(String securityLabelName, List<Integer> associatedAdversaryIdList) throws IOException {
        return deleteAssociationAdversaries(securityLabelName, associatedAdversaryIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationAdversaries(String securityLabelName, List<Integer> associatedAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Integer> data = deleteList("v2.securityLabels.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associatedAdversaryIdList);

        return data;
    }

    public void deleteAssociationAdversary(String securityLabelName, Integer associatedAdversaryId) throws IOException, FailedResponseException {
        deleteAssociationAdversary(securityLabelName, associatedAdversaryId, null);
    }

    public void deleteAssociationAdversary(String securityLabelName, Integer associatedAdversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", associatedAdversaryId);
        deleteItem("v2.securityLabels.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationEmails(String securityLabelName, List<Integer> associatedEmailIdList) throws IOException {
        return deleteAssociationEmails(securityLabelName, associatedEmailIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationEmails(String securityLabelName, List<Integer> associatedEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Integer> data = deleteList("v2.securityLabels.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associatedEmailIdList);

        return data;
    }

    public void deleteAssociationEmail(String securityLabelName, Integer associatedEmailId) throws IOException, FailedResponseException {
        deleteAssociationEmail(securityLabelName, associatedEmailId, null);
    }

    public void deleteAssociationEmail(String securityLabelName, Integer associatedEmailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", associatedEmailId);
        deleteItem("v2.securityLabels.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationIncidents(String securityLabelName, List<Integer> associatedIncidentIdList) throws IOException {
        return deleteAssociationIncidents(securityLabelName, associatedIncidentIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationIncidents(String securityLabelName, List<Integer> associatedIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Integer> data = deleteList("v2.securityLabels.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associatedIncidentIdList);

        return data;
    }

    public void deleteAssociationIncident(String securityLabelName, Integer associatedIncidentId) throws IOException, FailedResponseException {
        deleteAssociationIncident(securityLabelName, associatedIncidentId, null);
    }

    public void deleteAssociationIncident(String securityLabelName, Integer associatedIncidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", associatedIncidentId);
        deleteItem("v2.securityLabels.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationSignatures(String securityLabelName, List<Integer> associatedSignatureIdList) throws IOException {
        return deleteAssociationSignatures(securityLabelName, associatedSignatureIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationSignatures(String securityLabelName, List<Integer> associatedSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Integer> data = deleteList("v2.securityLabels.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associatedSignatureIdList);

        return data;
    }

    public void deleteAssociationSignature(String securityLabelName, Integer associatedSignatureId) throws IOException, FailedResponseException {
        deleteAssociationSignature(securityLabelName, associatedSignatureId, null);
    }

    public void deleteAssociationSignature(String securityLabelName, Integer associatedSignatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", associatedSignatureId);
        deleteItem("v2.securityLabels.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

    }

    public WriteListResponse<Integer> deleteAssociationThreats(String securityLabelName, List<Integer> associatedThreatIdList) throws IOException {
        return deleteAssociationThreats(securityLabelName, associatedThreatIdList, null);
    }

    public WriteListResponse<Integer> deleteAssociationThreats(String securityLabelName, List<Integer> associatedThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<Integer> data = deleteList("v2.securityLabels.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associatedThreatIdList);

        return data;
    }

    public void deleteAssociationThreat(String securityLabelName, Integer associatedThreatId) throws IOException, FailedResponseException {
        deleteAssociationThreat(securityLabelName, associatedThreatId, null);
    }

    public void deleteAssociationThreat(String securityLabelName, Integer associatedThreatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "groupId", associatedThreatId);
        deleteItem("v2.securityLabels.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorAddresses(String securityLabelName, List<String> associatedAddressList) throws IOException {
        return deleteAssociationIndicatorAddresses(securityLabelName, associatedAddressList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorAddresses(String securityLabelName, List<String> associatedAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<String> data = deleteList("v2.securityLabels.byId.indicators.addresses.byGroupId", AddressListResponse.class, ownerName, map, "securityLabelName", associatedAddressList);

        return data;
    }

    public void deleteAssociationIndicatorAddress(String securityLabelName, String associatedAddress) throws IOException, FailedResponseException {
        deleteAssociationIndicatorAddress(securityLabelName, associatedAddress, null);
    }

    public void deleteAssociationIndicatorAddress(String securityLabelName, String associatedAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associatedAddress);
        deleteItem("v2.securityLabels.byId.indicators.addresses.byIndicatorId", AddressResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorEmailAddresses(String securityLabelName, List<String> associatedEmailAddressList) throws IOException {
        return deleteAssociationIndicatorEmailAddresses(securityLabelName, associatedEmailAddressList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorEmailAddresses(String securityLabelName, List<String> associatedEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<String> data = deleteList("v2.securityLabels.byId.indicators.emailAddresses.byGroupId", EmailAddressListResponse.class, ownerName, map, "securityLabelName", associatedEmailAddressList);

        return data;
    }

    public void deleteAssociationIndicatorEmailAddress(String securityLabelName, String associatedEmailAddress) throws IOException, FailedResponseException {
        deleteAssociationIndicatorEmailAddress(securityLabelName, associatedEmailAddress, null);
    }

    public void deleteAssociationIndicatorEmailAddress(String securityLabelName, String associatedEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associatedEmailAddress);
        deleteItem("v2.securityLabels.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorFilees(String securityLabelName, List<String> associatedFileHashList) throws IOException {
        return deleteAssociationIndicatorFilees(securityLabelName, associatedFileHashList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorFilees(String securityLabelName, List<String> associatedFileHashList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<String> data = deleteList("v2.securityLabels.byId.indicators.files.byGroupId", FileListResponse.class, ownerName, map, "securityLabelName", associatedFileHashList);

        return data;
    }

    public void deleteAssociationIndicatorFile(String securityLabelName, String associatedFileHash) throws IOException, FailedResponseException {
        deleteAssociationIndicatorFile(securityLabelName, associatedFileHash, null);
    }

    public void deleteAssociationIndicatorFile(String securityLabelName, String associatedFileHash, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associatedFileHash);
        deleteItem("v2.securityLabels.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorHosts(String securityLabelName, List<String> associatedHostList) throws IOException {
        return deleteAssociationIndicatorHosts(securityLabelName, associatedHostList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorHosts(String securityLabelName, List<String> associatedHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<String> data = deleteList("v2.securityLabels.byId.indicators.hosts.byGroupId", HostListResponse.class, ownerName, map, "securityLabelName", associatedHostList);

        return data;
    }

    public void deleteAssociationIndicatorHost(String securityLabelName, String associatedHost) throws IOException, FailedResponseException {
        deleteAssociationIndicatorHost(securityLabelName, associatedHost, null);
    }

    public void deleteAssociationIndicatorHost(String securityLabelName, String associatedHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associatedHost);
        deleteItem("v2.securityLabels.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map);

    }

    public WriteListResponse<String> deleteAssociationIndicatorUrls(String securityLabelName, List<String> associatedUrlList) throws IOException {
        return deleteAssociationIndicatorUrls(securityLabelName, associatedUrlList, null);
    }

    public WriteListResponse<String> deleteAssociationIndicatorUrls(String securityLabelName, List<String> associatedUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", securityLabelName);
        WriteListResponse<String> data = deleteList("v2.securityLabels.byId.indicators.urls.byGroupId", UrlListResponse.class, ownerName, map, "securityLabelName", associatedUrlList);

        return data;
    }

    public void deleteAssociationIndicatorUrl(String securityLabelName, String associatedUrl) throws IOException, FailedResponseException {
        deleteAssociationIndicatorUrl(securityLabelName, associatedUrl, null);
    }

    public void deleteAssociationIndicatorUrl(String securityLabelName, String associatedUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", securityLabelName, "indicatorId", associatedUrl);
        deleteItem("v2.securityLabels.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map);

    }

}
