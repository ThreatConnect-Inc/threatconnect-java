package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.entity.File;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.entity.VictimPhone;
import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.entity.VictimWebSite;
import com.cyber2.api.lib.server.response.entity.AddressListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.AttributeListResponse;
import com.cyber2.api.lib.server.response.entity.AttributeResponse;
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
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import com.cyber2.api.lib.server.response.entity.ThreatListResponse;
import com.cyber2.api.lib.server.response.entity.ThreatResponse;
import com.cyber2.api.lib.server.response.entity.UrlListResponse;
import com.cyber2.api.lib.server.response.entity.UrlResponse;
import com.cyber2.api.lib.server.response.entity.VictimEmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.VictimNetworkAccountResponse;
import com.cyber2.api.lib.server.response.entity.VictimPhoneResponse;
import com.cyber2.api.lib.server.response.entity.VictimResponse;
import com.cyber2.api.lib.server.response.entity.VictimSocialNetworkResponse;
import com.cyber2.api.lib.server.response.entity.VictimWebSiteResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AbstractGroupWriterAdapter is the primary client adapter for all T group
 level objects. It uses the {@link Connection} object to execute requests
 * against the {@link RequestExecutor} object. The responsibility of this class
 * is to encapsulate all the low level ThreatConnect API calls specifically
 * targeted at data under the T group type.
 *
 *
 * @author dtineo
 * @param <T>   Group type for which this adapter writes to
 */
public abstract class AbstractGroupWriterAdapter<T extends Group> extends AbstractWriterAdapter implements UrlTypeable {

    private final Class<? extends ApiEntitySingleResponse> singleType;

    /**
     * Package level constructor. Use the {@link ClientWriterAdapterFactory} to
     * access this object.
     *
     * @param conn Primary connection object to the ThreatConnect API
     * @param executor Executor handling low level HTTPS calls to the
     * ThreatConnect API
     * @param singleType ApiEntitySingleResponse subclass that will wrap single entity responses
     *
     * @see ClientWriterAdapterFactory
     */
    protected AbstractGroupWriterAdapter(Connection conn, RequestExecutor executor, Class<? extends ApiEntitySingleResponse> singleType) {
        super(conn, executor);

        this.singleType = singleType;
    }

    /**
     * API call to create T
     *
     * @param groups  Collection of groups with type T
     * @return Collection {@link java.util.List} with type T objects
     * @throws IOException When the HTTPS API request fails due to IO issues
     */
    public WriteListResponse<T> create(List<T> groups) throws IOException {
        return createList("v2.groups.type.list", singleType, groups);
    }

    public WriteListResponse<T> update(List<T> groups) throws IOException {
        return update(groups, null);
    }

    public WriteListResponse<T> update(List<T> groups, String ownerName) throws IOException {
        List<Integer> idList = new ArrayList<>();
        for(T it : groups)    idList.add( it.getId() );
        WriteListResponse<T> data = updateListWithParam("v2.groups.type.byId", singleType, ownerName, null, "id", idList, groups);

        return data;

    }

    public WriteListResponse<Integer> delete(List<Integer> groupIds) throws IOException {
        return delete(groupIds, null);
    }

    public WriteListResponse<Integer> delete(List<Integer> groupIds, String ownerName) throws IOException {
        List<Integer> idList = new ArrayList<>();
        for(Integer it : groupIds)    idList.add( it );
        WriteListResponse<Integer> data = deleteList("v2.groups.type.byId", singleType, null, null, "id", idList);

        return data;
    }

    /**
     *
     * API call to create single T.
     * <p>
     * Per the ThreatConnect User Documentation:
     * </p>
     * <div style="margin-left: 2em; border-left: solid 2px gray; padding-left: 2em;"><i>
     * By default, all requests that do not include an Owner are assumed to be
     * for the API User’s Organization.
     * </i></div>
     *
     * @param group The type T object to save
     * @return T object when it exists in ThreatConnect
     * @throws IOException When the HTTPS API request fails due to IO issues
     * @throws com.cyber2.api.lib.exception.FailedResponseException When the API
     * responds with an error and the request is unsuccessful
     */
    public T create(T group) throws IOException, FailedResponseException {
        return create(group, null);
    }

    public T create(T group, String ownerName)
        throws IOException, FailedResponseException {

        Object item = createItem("v2.groups.type.list", singleType, ownerName, null, group);

        return (T) singleType.cast(item).getData().getData();
    }

    public T update(T group) throws IOException, FailedResponseException {
        return update(group, null);
    }

    public T update(T group, String ownerName)
        throws IOException, FailedResponseException {

        Object item = updateItem("v2.groups.type.list", singleType, ownerName, null, group);

        return (T) singleType.cast(item).getData().getData();
    }

    // delete on non-existent groupId returns 404
   public void delete(Integer groupId) throws IOException, FailedResponseException {
        delete(groupId, null);
    }

    public void delete(Integer groupId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId);
        deleteItem("v2.groups.type.byId", singleType, ownerName, map);

    }

    public WriteListResponse<Attribute> createAttributes(Integer groupId, List<Attribute> attributes)
        throws IOException {
        return createAttributes(groupId, attributes, null);
    }

    public WriteListResponse<Attribute> createAttributes(Integer groupId, List<Attribute> attributes, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse data = createList("v2.groups.type.byId.attributes", AttributeListResponse.class, ownerName, map, attributes);

        return data;
    }

    public Attribute createAttribute(Integer groupId, Attribute attribute) throws IOException, FailedResponseException {
        return createAttribute(groupId, attribute, null);
    }

    public Attribute createAttribute(Integer groupId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId);
        AttributeResponse item = createItem("v2.groups.type.byId.attributes", AttributeResponse.class, ownerName, map, attribute);

        return (Attribute) item.getData().getData();
    }

    public WriteListResponse<Attribute> updateAttributes(Integer groupId, List<Attribute> attributes)
        throws IOException {
        return updateAttributes(groupId, attributes, null);
    }

    public WriteListResponse<Attribute> updateAttributes(Integer groupId, List<Attribute> attributes, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        List<Integer> idList = new ArrayList<>();
        for(Attribute it : attributes)    idList.add( it.getId() );
        WriteListResponse data = updateListWithParam("v2.groups.type.byId.attributes.byId", AttributeListResponse.class, ownerName, map, "attributeId", idList, attributes);

        return data;
    }

    public Attribute updateAttribute(Integer groupId, Attribute attribute) throws IOException, FailedResponseException {
        return updateAttribute(groupId, attribute, null);
    }

    public Attribute updateAttribute(Integer groupId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId);
        AttributeResponse item = updateItem("v2.groups.type.byId.attributes.byId", AttributeResponse.class, ownerName, map, attribute);

        return (Attribute) item.getData().getData();
    }

    public WriteListResponse<SecurityLabel> associateAttributeSecurityLabels(Integer groupId, Integer attributeId, List<String> securityLabelList) throws IOException, FailedResponseException {
        return associateAttributeSecurityLabels(groupId, attributeId, securityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> associateAttributeSecurityLabels(Integer groupId, Integer attributeId, List<String> securityLabelList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId, "attributeId", attributeId);
        WriteListResponse<SecurityLabel> data = createListWithParam("v2.groups.type.byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabelName", securityLabelList);

        return data;
    }

    public SecurityLabel associateAttributeSecurityLabel(Integer groupId, Integer attributeId, String securityLabelName) throws IOException, FailedResponseException {
        return associateAttributeSecurityLabel(groupId, attributeId, securityLabelName, null);
    }

    public SecurityLabel associateAttributeSecurityLabel(Integer groupId, Integer attributeId, String securityLabelName, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "attributeId", attributeId, "securityLabelName", securityLabelName);
        SecurityLabelResponse data = createItem("v2.groups.type.byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return (SecurityLabel) data.getData().getData();
    }

    public WriteListResponse<Adversary> associateGroupAdversaries(Integer groupId, List<Integer> associateGroupAdversaryIdList) throws IOException {
        return associateGroupAdversaries(groupId, associateGroupAdversaryIdList, null);
    }

    public WriteListResponse<Adversary> associateGroupAdversaries(Integer groupId, List<Integer> associateGroupAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Adversary> data = createListWithParam("v2.groups.type.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associateGroupAdversaryIdList);

        return data;
    }

    public Adversary associateGroupAdversary(Integer groupId, Integer associateGroupAdversaryId) throws IOException, FailedResponseException {
        return associateGroupAdversary(groupId, associateGroupAdversaryId, null);
    }

    public Adversary associateGroupAdversary(Integer groupId, Integer associateGroupAdversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associateGroupAdversaryId);
        AdversaryResponse data = createItem("v2.groups.type.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return (Adversary) data.getData().getData();
    }

    public WriteListResponse<Email> associateGroupEmails(Integer groupId, List<Integer> associateGroupEmailIdList) throws IOException {
        return associateGroupEmails(groupId, associateGroupEmailIdList, null);
    }

    public WriteListResponse<Email> associateGroupEmails(Integer groupId, List<Integer> associateGroupEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Email> data = createListWithParam("v2.groups.type.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associateGroupEmailIdList);

        return data;
    }

    public Email associateGroupEmail(Integer groupId, Integer associateGroupEmailId) throws IOException, FailedResponseException {
        return associateGroupEmail(groupId, associateGroupEmailId, null);
    }

    public Email associateGroupEmail(Integer groupId, Integer associateGroupEmailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associateGroupEmailId);
        EmailResponse data = createItem("v2.groups.type.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return (Email) data.getData().getData();
    }

    public WriteListResponse<Incident> associateIncidents(Integer groupId, List<Integer> associateIncidentIdList) throws IOException {
        return associateIncidents(groupId, associateIncidentIdList, null);
    }

    public WriteListResponse<Incident> associateIncidents(Integer groupId, List<Integer> associateIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Incident> data = createListWithParam("v2.groups.type.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associateIncidentIdList);

        return data;
    }

    public Incident associateIncident(Integer groupId, Integer associateIncidentId) throws IOException, FailedResponseException {
        return associateIncident(groupId, associateIncidentId, null);
    }

    public Incident associateIncident(Integer groupId, Integer associateIncidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associateIncidentId);
        IncidentResponse data = createItem("v2.groups.type.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return (Incident) data.getData().getData();
    }

    public WriteListResponse<Signature> associateSignatures(Integer groupId, List<Integer> associateSignatureIdList) throws IOException {
        return associateSignatures(groupId, associateSignatureIdList, null);
    }

    public WriteListResponse<Signature> associateSignatures(Integer groupId, List<Integer> associateSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Signature> data = createListWithParam("v2.groups.type.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associateSignatureIdList);

        return data;
    }

    public Signature associateSignature(Integer groupId, Integer associateSignatureId) throws IOException, FailedResponseException {
        return associateSignature(groupId, associateSignatureId, null);
    }

    public Signature associateSignature(Integer groupId, Integer associateSignatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associateSignatureId);
        SignatureResponse data = createItem("v2.groups.type.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return (Signature) data.getData().getData();
    }

    public WriteListResponse<Threat> associateGroupThreats(Integer groupId, List<Integer> associateGroupThreatIdList) throws IOException {
        return associateGroupThreats(groupId, associateGroupThreatIdList, null);
    }

    public WriteListResponse<Threat> associateGroupThreats(Integer groupId, List<Integer> associateGroupThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Threat> data = createListWithParam("v2.groups.type.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associateGroupThreatIdList);

        return data;
    }

    public Threat associateGroupThreat(Integer groupId, Integer associateGroupThreatId) throws IOException, FailedResponseException {
        return associateGroupThreat(groupId, associateGroupThreatId, null);
    }

    public Threat associateGroupThreat(Integer groupId, Integer associateGroupThreatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associateGroupThreatId);
        ThreatResponse data = createItem("v2.groups.type.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return (Threat) data.getData().getData();
    }

    public WriteListResponse<Address> associateIndicatorIpAddresses(Integer groupId, List<String> associateIpAddressList) throws IOException {
        return associateIndicatorIpAddresses(groupId, associateIpAddressList, null);
    }

    public WriteListResponse<Address> associateIndicatorIpAddresses(Integer groupId, List<String> associateIpAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Address> data = createListWithParam("v2.groups.type.byId.groups.emails.byGroupId", AddressListResponse.class, ownerName, map, "groupId", associateIpAddressList);

        return data;
    }

    public EmailAddress associateIndicatorIpAddress(Integer groupId, String associateIpAddress) throws IOException, FailedResponseException {
        return associateIndicatorIpAddress(groupId, associateIpAddress, null);
    }

    public EmailAddress associateIndicatorIpAddress(Integer groupId, String associateIpAddressId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associateIpAddressId);
        EmailAddressResponse data = createItem("v2.groups.type.byId.indicators.addresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(Integer groupId, List<String> associateEmailAddressList) throws IOException {
        return associateIndicatorEmailAddresses(groupId, associateEmailAddressList, null);
    }

    public WriteListResponse<EmailAddress> associateIndicatorEmailAddresses(Integer groupId, List<String> associateEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<EmailAddress> data = createListWithParam("v2.groups.type.byId.indicators.emailAddresses.byGroupId", EmailAddressListResponse.class, ownerName, map, "groupId", associateEmailAddressList);

        return data;
    }

    public EmailAddress associateIndicatorEmailAddress(Integer groupId, String associateEmailAddress) throws IOException, FailedResponseException {
        return associateIndicatorEmailAddress(groupId, associateEmailAddress, null);
    }

    public EmailAddress associateIndicatorEmailAddress(Integer groupId, String associateEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associateEmailAddress);
        EmailAddressResponse data = createItem("v2.groups.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map, null);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<File> associateIndicatorFiles(Integer groupId, List<String> associateFileHashList) throws IOException {
        return associateIndicatorFiles(groupId, associateFileHashList, null);
    }

    public WriteListResponse<File> associateIndicatorFiles(Integer groupId, List<String> associateFileHashList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<File> data = createListWithParam("v2.groups.type.byId.indicators.files.byGroupId", FileListResponse.class, ownerName, map, "groupId", associateFileHashList);

        return data;
    }

    public File associateIndicatorFile(Integer groupId, String associateFileHash) throws IOException, FailedResponseException {
        return associateIndicatorFile(groupId, associateFileHash, null);
    }

    public File associateIndicatorFile(Integer groupId, String associateFileHash, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associateFileHash);
        FileResponse data = createItem("v2.groups.type.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map, null);

        return (File) data.getData().getData();
    }

    public WriteListResponse<Host> associateIndicatorHosts(Integer groupId, List<String> associateHostList) throws IOException {
        return associateIndicatorHosts(groupId, associateHostList, null);
    }

    public WriteListResponse<Host> associateIndicatorHosts(Integer groupId, List<String> associateHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Host> data = createListWithParam("v2.groups.type.byId.indicators.hosts.byGroupId", HostListResponse.class, ownerName, map, "groupId", associateHostList);

        return data;
    }

    public Host associateIndicatorHost(Integer groupId, String associateHost) throws IOException, FailedResponseException {
        return associateIndicatorHost(groupId, associateHost, null);
    }

    public Host associateIndicatorHost(Integer groupId, String associateHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associateHost);
        HostResponse data = createItem("v2.groups.type.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map, null);

        return (Host) data.getData().getData();
    }

    public WriteListResponse<Url> associateIndicatorUrls(Integer groupId, List<String> associateUrlList) throws IOException {
        return associateIndicatorUrls(groupId, associateUrlList, null);
    }

    public WriteListResponse<Url> associateIndicatorUrls(Integer groupId, List<String> associateUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Url> data = createListWithParam("v2.groups.type.byId.indicators.urls.byGroupId", UrlListResponse.class, ownerName, map, "groupId", associateUrlList);

        return data;
    }

    public Url associateIndicatorUrl(Integer groupId, String associateUrl) throws IOException, FailedResponseException {
        return associateIndicatorUrl(groupId, associateUrl, null);
    }

    public Url associateIndicatorUrl(Integer groupId, String associateUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associateUrl);
        UrlResponse data = createItem("v2.groups.type.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map, null);

        return (Url) data.getData().getData();
    }

    public WriteListResponse<SecurityLabel> associateSecurityLabels(Integer groupId, List<String> associateSecurityLabelList) throws IOException {
        return associateSecurityLabels(groupId, associateSecurityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> associateSecurityLabels(Integer groupId, List<String> associateSecurityLabelList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<SecurityLabel> data = createListWithParam("v2.groups.type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabelName", associateSecurityLabelList);

        return data;
    }

    public SecurityLabel associateSecurityLabel(Integer groupId, String associateSecurityLabel) throws IOException, FailedResponseException {
        return associateSecurityLabel(groupId, associateSecurityLabel, null);
    }

    public SecurityLabel associateSecurityLabel(Integer groupId, String associateSecurityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "securityLabelName", associateSecurityLabel);
        SecurityLabelResponse data = createItem("v2.groups.type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, null);

        return (SecurityLabel) data.getData().getData();
    }


    public WriteListResponse<Tag> associateTags(Integer groupId, List<String> associateTagList) throws IOException {
        return associateTags(groupId, associateTagList, null);
    }

    public WriteListResponse<Tag> associateTags(Integer groupId, List<String> associateTagList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Tag> data = createListWithParam("v2.groups.type.byId.tags.byName", TagResponse.class, ownerName, map, "tagName", associateTagList);

        return data;
    }

    public Tag associateTag(Integer groupId, String associateTag) throws IOException, FailedResponseException {
        return associateTag(groupId, associateTag, null);
    }

    public Tag associateTag(Integer groupId, String associateTag, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "tagName", associateTag);
        TagResponse data = createItem("v2.groups.type.byId.tags.byName", TagResponse.class, ownerName, map, null);

        return (Tag) data.getData().getData();
    }

    public WriteListResponse<VictimEmailAddress> associateVictimEmailAddresses(Integer groupId, List<Integer> associateVictimAssetList) throws IOException {
        return associateVictimEmailAddresses(groupId, associateVictimAssetList, null);
    }

    public WriteListResponse<VictimEmailAddress> associateVictimEmailAddresses(Integer groupId, List<Integer> associateVictimEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimEmailAddress> data = createListWithParam("v2.groups.type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", associateVictimEmailAddressList);

        return data;
    }

    public VictimEmailAddress associateVictimEmailAddress(Integer groupId, Integer associateVictimEmailAddressId) throws IOException, FailedResponseException {
        return associateVictimEmailAddress(groupId, associateVictimEmailAddressId, null);
    }

    public VictimEmailAddress associateVictimEmailAddress(Integer groupId, Integer associateVictimEmailAddressId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associateVictimEmailAddressId);
        VictimEmailAddressResponse data = createItem("v2.groups.type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, null);

        return (VictimEmailAddress) data.getData().getData();
    }

    public WriteListResponse<VictimNetworkAccount> associateVictimNetworkAccounts(Integer groupId, List<Integer> associateVictimNetworkAccountIdList) throws IOException {
        return associateVictimNetworkAccounts(groupId, associateVictimNetworkAccountIdList, null);
    }

    public WriteListResponse<VictimNetworkAccount> associateVictimNetworkAccounts(Integer groupId, List<Integer> associateVictimNetworkAccountIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimNetworkAccount> data = createListWithParam("v2.groups.type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", associateVictimNetworkAccountIdList);

        return data;
    }

    public VictimNetworkAccount associateVictimNetworkAccount(Integer groupId, Integer associateVictimNetworkAccountId) throws IOException, FailedResponseException {
        return associateVictimNetworkAccount(groupId, associateVictimNetworkAccountId, null);
    }

    public VictimNetworkAccount associateVictimNetworkAccount(Integer groupId, Integer associateVictimNetworkAccountId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associateVictimNetworkAccountId);
        VictimNetworkAccountResponse data = createItem("v2.groups.type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, null);

        return (VictimNetworkAccount) data.getData().getData();
    }

    public WriteListResponse<VictimPhone> associateVictimPhones(Integer groupId, List<Integer> associateVictimPhoneIdList) throws IOException {
        return associateVictimPhones(groupId, associateVictimPhoneIdList, null);
    }

    public WriteListResponse<VictimPhone> associateVictimPhones(Integer groupId, List<Integer> associateVictimPhoneIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimPhone> data = createListWithParam("v2.groups.type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", associateVictimPhoneIdList);

        return data;
    }

    public VictimPhone associateVictimPhone(Integer groupId, Integer associateVictimPhoneId) throws IOException, FailedResponseException {
        return associateVictimPhone(groupId, associateVictimPhoneId, null);
    }

    public VictimPhone associateVictimPhone(Integer groupId, Integer associateVictimPhoneId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associateVictimPhoneId);
        VictimPhoneResponse data = createItem("v2.groups.type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, null);

        return (VictimPhone) data.getData().getData();
    }

    public WriteListResponse<VictimSocialNetwork> associateVictimSocialNetworks(Integer groupId, List<Integer> associateSocialNetworkIdList) throws IOException {
        return associateVictimSocialNetworks(groupId, associateSocialNetworkIdList, null);
    }

    public WriteListResponse<VictimSocialNetwork> associateVictimSocialNetworks(Integer groupId, List<Integer> associateVictimSocialNetworkIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimSocialNetwork> data = createListWithParam("v2.groups.type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, "assetId", associateVictimSocialNetworkIdList);

        return data;
    }

    public VictimSocialNetwork associateVictimSocialNetwork(Integer groupId, Integer associateVictimSocialNetworkId) throws IOException, FailedResponseException {
        return associateVictimSocialNetwork(groupId, associateVictimSocialNetworkId, null);
    }

    public VictimSocialNetwork associateVictimSocialNetwork(Integer groupId, Integer associateVictimSocialNetworkId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associateVictimSocialNetworkId);
        VictimSocialNetworkResponse data = createItem("v2.groups.type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, null);

        return (VictimSocialNetwork) data.getData().getData();
    }

    public WriteListResponse<VictimWebSite> associateVictimWebSites(Integer groupId, List<Integer> associateWebsiteIdList) throws IOException {
        return associateVictimWebSites(groupId, associateWebsiteIdList, null);
    }

    public WriteListResponse<VictimWebSite> associateVictimWebSites(Integer groupId, List<Integer> associateVictimWebsiteIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimWebSite> data = createListWithParam("v2.groups.type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", associateVictimWebsiteIdList);

        return data;
    }

    public VictimWebSite associateVictimWebSite(Integer groupId, Integer associateVictimWebsiteId) throws IOException, FailedResponseException {
        return associateVictimWebSite(groupId, associateVictimWebsiteId, null);
    }

    public VictimWebSite associateVictimWebSite(Integer groupId, Integer associateVictimWebsiteId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associateVictimWebsiteId);
        VictimWebSiteResponse data = createItem("v2.groups.type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, null);

        return (VictimWebSite) data.getData().getData();
    }

        public WriteListResponse<Victim> associateVictims(Integer groupId, List<Integer> associateVictimIdList) throws IOException {
        return associateVictims(groupId, associateVictimIdList, null);
    }

    public WriteListResponse<Victim> associateVictims(Integer groupId, List<Integer> associateVictimIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Victim> data = createListWithParam("v2.groups.type.byId.victims.byVictimId", VictimResponse.class, ownerName, map, "victimId", associateVictimIdList);

        return data;
    }

    public Victim associateVictim(Integer groupId, Integer associateVictimId) throws IOException, FailedResponseException {
        return associateVictim(groupId, associateVictimId, null);
    }

    public Victim associateVictim(Integer groupId, Integer associateVictimId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "victimId", associateVictimId);
        VictimResponse data = createItem("v2.groups.type.byId.victims.byVictimId", VictimResponse.class, ownerName, map, null);

        return (Victim) data.getData().getData();
    }

    public WriteListResponse<SecurityLabel> deleteAssociationAttributeSecurityLabels(Integer groupId, Integer attributeId, List<String> securityLabelList) throws IOException, FailedResponseException {
        return deleteAssociationAttributeSecurityLabels(groupId, attributeId, securityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> deleteAssociationAttributeSecurityLabels(Integer groupId, Integer attributeId, List<String> securityLabelList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId, "attributeId", attributeId);
        WriteListResponse<SecurityLabel> data = deleteList("v2.groups.type.byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabelName", securityLabelList);

        return data;
    }

    public SecurityLabel deleteAssociationAttributeSecurityLabel(Integer groupId, Integer attributeId, String securityLabelName) throws IOException, FailedResponseException {
        return deleteAssociationAttributeSecurityLabel(groupId, attributeId, securityLabelName, null);
    }

    public SecurityLabel deleteAssociationAttributeSecurityLabel(Integer groupId, Integer attributeId, String securityLabelName, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "attributeId", attributeId, "securityLabelName", securityLabelName);
        SecurityLabelResponse data = deleteItem("v2.groups.type.byId.attributes.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return (SecurityLabel) data.getData().getData();
    }

    public WriteListResponse<Adversary> deleteAssociationGroupAdversaries(Integer groupId, List<Integer> associatedGroupAdversaryIdList) throws IOException {
        return deleteAssociationGroupAdversaries(groupId, associatedGroupAdversaryIdList, null);
    }

    public WriteListResponse<Adversary> deleteAssociationGroupAdversaries(Integer groupId, List<Integer> associatedGroupAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Adversary> data = deleteList("v2.groups.type.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", associatedGroupAdversaryIdList);

        return data;
    }

    public Adversary deleteAssociationGroupAdversary(Integer groupId, Integer associatedGroupAdversaryId) throws IOException, FailedResponseException {
        return deleteAssociationGroupAdversary(groupId, associatedGroupAdversaryId, null);
    }

    public Adversary deleteAssociationGroupAdversary(Integer groupId, Integer associatedGroupAdversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associatedGroupAdversaryId);
        AdversaryResponse data = deleteItem("v2.groups.type.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map);

        return (Adversary) data.getData().getData();
    }

    public WriteListResponse<Email> deleteAssociationGroupEmails(Integer groupId, List<Integer> associatedGroupEmailIdList) throws IOException {
        return deleteAssociationGroupEmails(groupId, associatedGroupEmailIdList, null);
    }

    public WriteListResponse<Email> deleteAssociationGroupEmails(Integer groupId, List<Integer> associatedGroupEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Email> data = deleteList("v2.groups.type.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", associatedGroupEmailIdList);

        return data;
    }

    public Email deleteAssociationGroupEmail(Integer groupId, Integer associatedGroupEmailId) throws IOException, FailedResponseException {
        return deleteAssociationGroupEmail(groupId, associatedGroupEmailId, null);
    }

    public Email deleteAssociationGroupEmail(Integer groupId, Integer associatedGroupEmailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associatedGroupEmailId);
        EmailResponse data = deleteItem("v2.groups.type.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map);

        return (Email) data.getData().getData();
    }

    public WriteListResponse<Incident> deleteAssociationIncidents(Integer groupId, List<Integer> associatedIncidentIdList) throws IOException {
        return deleteAssociationIncidents(groupId, associatedIncidentIdList, null);
    }

    public WriteListResponse<Incident> deleteAssociationIncidents(Integer groupId, List<Integer> associatedIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Incident> data = deleteList("v2.groups.type.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", associatedIncidentIdList);

        return data;
    }

    public Incident deleteAssociationIncident(Integer groupId, Integer associatedIncidentId) throws IOException, FailedResponseException {
        return deleteAssociationIncident(groupId, associatedIncidentId, null);
    }

    public Incident deleteAssociationIncident(Integer groupId, Integer associatedIncidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associatedIncidentId);
        IncidentResponse data = deleteItem("v2.groups.type.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map);

        return (Incident) data.getData().getData();
    }

    public WriteListResponse<Signature> deleteAssociationSignatures(Integer groupId, List<Integer> associatedSignatureIdList) throws IOException {
        return deleteAssociationSignatures(groupId, associatedSignatureIdList, null);
    }

    public WriteListResponse<Signature> deleteAssociationSignatures(Integer groupId, List<Integer> associatedSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Signature> data = deleteList("v2.groups.type.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", associatedSignatureIdList);

        return data;
    }

    public Signature deleteAssociationSignature(Integer groupId, Integer associatedSignatureId) throws IOException, FailedResponseException {
        return deleteAssociationSignature(groupId, associatedSignatureId, null);
    }

    public Signature deleteAssociationSignature(Integer groupId, Integer associatedSignatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associatedSignatureId);
        SignatureResponse data = deleteItem("v2.groups.type.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map);

        return (Signature) data.getData().getData();
    }

    public WriteListResponse<Threat> deleteAssociationGroupThreats(Integer groupId, List<Integer> associatedGroupThreatIdList) throws IOException {
        return deleteAssociationGroupThreats(groupId, associatedGroupThreatIdList, null);
    }

    public WriteListResponse<Threat> deleteAssociationGroupThreats(Integer groupId, List<Integer> associatedGroupThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Threat> data = deleteList("v2.groups.type.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", associatedGroupThreatIdList);

        return data;
    }

    public Threat deleteAssociationGroupThreat(Integer groupId, Integer associatedGroupThreatId) throws IOException, FailedResponseException {
        return deleteAssociationGroupThreat(groupId, associatedGroupThreatId, null);
    }

    public Threat deleteAssociationGroupThreat(Integer groupId, Integer associatedGroupThreatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "groupId", associatedGroupThreatId);
        ThreatResponse data = deleteItem("v2.groups.type.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map);

        return (Threat) data.getData().getData();
    }

    public WriteListResponse<Address> deleteAssociationIndicatorIpAddresses(Integer groupId, List<String> associatedIpAddressList) throws IOException {
        return deleteAssociationIndicatorIpAddresses(groupId, associatedIpAddressList, null);
    }

    public WriteListResponse<Address> deleteAssociationIndicatorIpAddresses(Integer groupId, List<String> associatedIpAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Address> data = deleteList("v2.groups.type.byId.groups.emails.byGroupId", AddressListResponse.class, ownerName, map, "groupId", associatedIpAddressList);

        return data;
    }

    public EmailAddress deleteAssociationIndicatorIpAddress(Integer groupId, String associatedIpAddress) throws IOException, FailedResponseException {
        return deleteAssociationIndicatorIpAddress(groupId, associatedIpAddress, null);
    }

    public EmailAddress deleteAssociationIndicatorIpAddress(Integer groupId, String associatedIpAddressId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associatedIpAddressId);
        EmailAddressResponse data = deleteItem("v2.groups.type.byId.indicators.addresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<EmailAddress> deleteAssociationIndicatorEmailAddresses(Integer groupId, List<String> associatedEmailAddressList) throws IOException {
        return deleteAssociationIndicatorEmailAddresses(groupId, associatedEmailAddressList, null);
    }

    public WriteListResponse<EmailAddress> deleteAssociationIndicatorEmailAddresses(Integer groupId, List<String> associatedEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<EmailAddress> data = deleteList("v2.groups.type.byId.indicators.emailAddresses.byGroupId", EmailAddressListResponse.class, ownerName, map, "groupId", associatedEmailAddressList);

        return data;
    }

    public EmailAddress deleteAssociationIndicatorEmailAddress(Integer groupId, String associatedEmailAddress) throws IOException, FailedResponseException {
        return deleteAssociationIndicatorEmailAddress(groupId, associatedEmailAddress, null);
    }

    public EmailAddress deleteAssociationIndicatorEmailAddress(Integer groupId, String associatedEmailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associatedEmailAddress);
        EmailAddressResponse data = deleteItem("v2.groups.type.byId.indicators.emailAddresses.byIndicatorId", EmailAddressResponse.class, ownerName, map);

        return (EmailAddress) data.getData().getData();
    }

    public WriteListResponse<File> deleteAssociationIndicatorFiles(Integer groupId, List<String> associatedFileHashList) throws IOException {
        return deleteAssociationIndicatorFiles(groupId, associatedFileHashList, null);
    }

    public WriteListResponse<File> deleteAssociationIndicatorFiles(Integer groupId, List<String> associatedFileHashList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<File> data = deleteList("v2.groups.type.byId.indicators.files.byGroupId", FileListResponse.class, ownerName, map, "groupId", associatedFileHashList);

        return data;
    }

    public File deleteAssociationIndicatorFile(Integer groupId, String associatedFileHash) throws IOException, FailedResponseException {
        return deleteAssociationIndicatorFile(groupId, associatedFileHash, null);
    }

    public File deleteAssociationIndicatorFile(Integer groupId, String associatedFileHash, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associatedFileHash);
        FileResponse data = deleteItem("v2.groups.type.byId.indicators.files.byIndicatorId", FileResponse.class, ownerName, map);

        return (File) data.getData().getData();
    }

    public WriteListResponse<Host> deleteAssociationIndicatorHosts(Integer groupId, List<String> associatedHostList) throws IOException {
        return deleteAssociationIndicatorHosts(groupId, associatedHostList, null);
    }

    public WriteListResponse<Host> deleteAssociationIndicatorHosts(Integer groupId, List<String> associatedHostList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Host> data = deleteList("v2.groups.type.byId.indicators.hosts.byGroupId", HostListResponse.class, ownerName, map, "groupId", associatedHostList);

        return data;
    }

    public Host deleteAssociationIndicatorHost(Integer groupId, String associatedHost) throws IOException, FailedResponseException {
        return deleteAssociationIndicatorHost(groupId, associatedHost, null);
    }

    public Host deleteAssociationIndicatorHost(Integer groupId, String associatedHost, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associatedHost);
        HostResponse data = deleteItem("v2.groups.type.byId.indicators.hosts.byIndicatorId", HostResponse.class, ownerName, map);

        return (Host) data.getData().getData();
    }

    public WriteListResponse<Url> deleteAssociationIndicatorUrls(Integer groupId, List<String> associatedUrlList) throws IOException {
        return deleteAssociationIndicatorUrls(groupId, associatedUrlList, null);
    }

    public WriteListResponse<Url> deleteAssociationIndicatorUrls(Integer groupId, List<String> associatedUrlList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Url> data = deleteList("v2.groups.type.byId.indicators.urls.byGroupId", UrlListResponse.class, ownerName, map, "groupId", associatedUrlList);

        return data;
    }

    public Url deleteAssociationIndicatorUrl(Integer groupId, String associatedUrl) throws IOException, FailedResponseException {
        return deleteAssociationIndicatorUrl(groupId, associatedUrl, null);
    }

    public Url deleteAssociationIndicatorUrl(Integer groupId, String associatedUrl, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "indicatorId", associatedUrl);
        UrlResponse data = deleteItem("v2.groups.type.byId.indicators.urls.byIndicatorId", UrlResponse.class, ownerName, map);

        return (Url) data.getData().getData();
    }

    public WriteListResponse<SecurityLabel> deleteAssociationSecurityLabels(Integer groupId, List<String> associatedSecurityLabelList) throws IOException {
        return deleteAssociationSecurityLabels(groupId, associatedSecurityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> deleteAssociationSecurityLabels(Integer groupId, List<String> associatedSecurityLabelList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<SecurityLabel> data = deleteList("v2.groups.type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabelName", associatedSecurityLabelList);

        return data;
    }

    public SecurityLabel deleteAssociationSecurityLabel(Integer groupId, String associatedSecurityLabel) throws IOException, FailedResponseException {
        return deleteAssociationSecurityLabel(groupId, associatedSecurityLabel, null);
    }

    public SecurityLabel deleteAssociationSecurityLabel(Integer groupId, String associatedSecurityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "securityLabelName", associatedSecurityLabel);
        SecurityLabelResponse data = deleteItem("v2.groups.type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map);

        return (SecurityLabel) data.getData().getData();
    }


    public WriteListResponse<Tag> deleteAssociationTags(Integer groupId, List<String> associatedTagList) throws IOException {
        return deleteAssociationTags(groupId, associatedTagList, null);
    }

    public WriteListResponse<Tag> deleteAssociationTags(Integer groupId, List<String> associatedTagList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Tag> data = deleteList("v2.groups.type.byId.tags.byName", TagResponse.class, ownerName, map, "tagName", associatedTagList);

        return data;
    }

    public Tag deleteAssociationTag(Integer groupId, String associatedTag) throws IOException, FailedResponseException {
        return deleteAssociationTag(groupId, associatedTag, null);
    }

    public Tag deleteAssociationTag(Integer groupId, String associatedTag, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "tagName", associatedTag);
        TagResponse data = deleteItem("v2.groups.type.byId.tags.byName", TagResponse.class, ownerName, map);

        return (Tag) data.getData().getData();
    }

    public WriteListResponse<VictimEmailAddress> deleteAssociationVictimEmailAddresses(Integer groupId, List<Integer> associatedVictimAssetList) throws IOException {
        return deleteAssociationVictimEmailAddresses(groupId, associatedVictimAssetList, null);
    }

    public WriteListResponse<VictimEmailAddress> deleteAssociationVictimEmailAddresses(Integer groupId, List<Integer> associatedVictimEmailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimEmailAddress> data = deleteList("v2.groups.type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map, "assetId", associatedVictimEmailAddressList);

        return data;
    }

    public VictimEmailAddress deleteAssociationVictimEmailAddress(Integer groupId, Integer associatedVictimEmailAddressId) throws IOException, FailedResponseException {
        return deleteAssociationVictimEmailAddress(groupId, associatedVictimEmailAddressId, null);
    }

    public VictimEmailAddress deleteAssociationVictimEmailAddress(Integer groupId, Integer associatedVictimEmailAddressId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associatedVictimEmailAddressId);
        VictimEmailAddressResponse data = deleteItem("v2.groups.type.byId.victimAssets.emailAddresses.byAssetId", VictimEmailAddressResponse.class, ownerName, map);

        return (VictimEmailAddress) data.getData().getData();
    }

    public WriteListResponse<VictimNetworkAccount> deleteAssociationVictimNetworkAccounts(Integer groupId, List<Integer> associatedVictimNetworkAccountIdList) throws IOException {
        return deleteAssociationVictimNetworkAccounts(groupId, associatedVictimNetworkAccountIdList, null);
    }

    public WriteListResponse<VictimNetworkAccount> deleteAssociationVictimNetworkAccounts(Integer groupId, List<Integer> associatedVictimNetworkAccountIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimNetworkAccount> data = deleteList("v2.groups.type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, "assetId", associatedVictimNetworkAccountIdList);

        return data;
    }

    public VictimNetworkAccount deleteAssociationVictimNetworkAccount(Integer groupId, Integer associatedVictimNetworkAccountId) throws IOException, FailedResponseException {
        return deleteAssociationVictimNetworkAccount(groupId, associatedVictimNetworkAccountId, null);
    }

    public VictimNetworkAccount deleteAssociationVictimNetworkAccount(Integer groupId, Integer associatedVictimNetworkAccountId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associatedVictimNetworkAccountId);
        VictimNetworkAccountResponse data = deleteItem("v2.groups.type.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map);

        return (VictimNetworkAccount) data.getData().getData();
    }

    public WriteListResponse<VictimPhone> deleteAssociationVictimPhonees(Integer groupId, List<Integer> associatedVictimPhoneIdList) throws IOException {
        return deleteAssociationVictimPhonees(groupId, associatedVictimPhoneIdList, null);
    }

    public WriteListResponse<VictimPhone> deleteAssociationVictimPhonees(Integer groupId, List<Integer> associatedVictimPhoneIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimPhone> data = deleteList("v2.groups.type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, "assetId", associatedVictimPhoneIdList);

        return data;
    }

    public VictimPhone deleteAssociationVictimPhone(Integer groupId, Integer associatedVictimPhoneId) throws IOException, FailedResponseException {
        return deleteAssociationVictimPhone(groupId, associatedVictimPhoneId, null);
    }

    public VictimPhone deleteAssociationVictimPhone(Integer groupId, Integer associatedVictimPhoneId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associatedVictimPhoneId);
        VictimPhoneResponse data = deleteItem("v2.groups.type.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map);

        return (VictimPhone) data.getData().getData();
    }

    public WriteListResponse<VictimSocialNetwork> deleteAssociationVictimSocialNetworks(Integer groupId, List<Integer> associatedSocialNetworkIdList) throws IOException {
        return deleteAssociationVictimSocialNetworks(groupId, associatedSocialNetworkIdList, null);
    }

    public WriteListResponse<VictimSocialNetwork> deleteAssociationVictimSocialNetworks(Integer groupId, List<Integer> associatedVictimSocialNetworkIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimSocialNetwork> data = deleteList("v2.groups.type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, "assetId", associatedVictimSocialNetworkIdList);

        return data;
    }

    public VictimSocialNetwork deleteAssociationVictimSocialNetwork(Integer groupId, Integer associatedVictimSocialNetworkId) throws IOException, FailedResponseException {
        return deleteAssociationVictimSocialNetwork(groupId, associatedVictimSocialNetworkId, null);
    }

    public VictimSocialNetwork deleteAssociationVictimSocialNetwork(Integer groupId, Integer associatedVictimSocialNetworkId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associatedVictimSocialNetworkId);
        VictimSocialNetworkResponse data = deleteItem("v2.groups.type.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map);

        return (VictimSocialNetwork) data.getData().getData();
    }

    public WriteListResponse<VictimWebSite> deleteAssociationVictimWebSitees(Integer groupId, List<Integer> associatedWebsiteIdList) throws IOException {
        return deleteAssociationVictimWebSitees(groupId, associatedWebsiteIdList, null);
    }

    public WriteListResponse<VictimWebSite> deleteAssociationVictimWebSitees(Integer groupId, List<Integer> associatedVictimWebsiteIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<VictimWebSite> data = deleteList("v2.groups.type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, "assetId", associatedVictimWebsiteIdList);

        return data;
    }

    public VictimWebSite deleteAssociationVictimWebSite(Integer groupId, Integer associatedVictimWebsiteId) throws IOException, FailedResponseException {
        return deleteAssociationVictimWebSite(groupId, associatedVictimWebsiteId, null);
    }

    public VictimWebSite deleteAssociationVictimWebSite(Integer groupId, Integer associatedVictimWebsiteId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "assetId", associatedVictimWebsiteId);
        VictimWebSiteResponse data = deleteItem("v2.groups.type.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map);

        return (VictimWebSite) data.getData().getData();
    }

        public WriteListResponse<Victim> deleteAssociationVictims(Integer groupId, List<Integer> associatedVictimIdList) throws IOException {
        return deleteAssociationVictims(groupId, associatedVictimIdList, null);
    }

    public WriteListResponse<Victim> deleteAssociationVictims(Integer groupId, List<Integer> associatedVictimIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", groupId);
        WriteListResponse<Victim> data = deleteList("v2.groups.type.byId.victims.byVictimId", VictimResponse.class, ownerName, map, "victimId", associatedVictimIdList);

        return data;
    }

    public Victim deleteAssociationVictim(Integer groupId, Integer associatedVictimId) throws IOException, FailedResponseException {
        return deleteAssociationVictim(groupId, associatedVictimId, null);
    }

    public Victim deleteAssociationVictim(Integer groupId, Integer associatedVictimId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", groupId, "victimId", associatedVictimId);
        VictimResponse data = deleteItem("v2.groups.type.byId.victims.byVictimId", VictimResponse.class, ownerName, map);

        return (Victim) data.getData().getData();
    }


}
