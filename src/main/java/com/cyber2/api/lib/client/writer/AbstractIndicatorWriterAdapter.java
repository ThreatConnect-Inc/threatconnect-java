package com.cyber2.api.lib.client.writer;

import com.cyber2.api.lib.client.response.WriteListResponse;
import com.cyber2.api.lib.client.Identifiable;
import com.cyber2.api.lib.client.UrlTypeable;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.response.entity.AdversaryListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import com.cyber2.api.lib.server.response.entity.AttributeListResponse;
import com.cyber2.api.lib.server.response.entity.AttributeResponse;
import com.cyber2.api.lib.server.response.entity.EmailListResponse;
import com.cyber2.api.lib.server.response.entity.EmailResponse;
import com.cyber2.api.lib.server.response.entity.IncidentListResponse;
import com.cyber2.api.lib.server.response.entity.IncidentResponse;
import com.cyber2.api.lib.server.response.entity.SecurityLabelResponse;
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.TagResponse;
import com.cyber2.api.lib.server.response.entity.ThreatListResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * AbstractIndicatorWriterAdapter is the primary client adapter for all T indicator
 level objects. It uses the {@link Connection} object to execute requests
 * against the {@link RequestExecutor} object. The responsibility of this class
 * is to encapsulate all the low level ThreatConnect API calls specifically
 * targeted at data under the T indicator type.
 *
 *
 * @author dtineo
 * @param <T>   Indicator type for which this adapter writes to
 */
public abstract class AbstractIndicatorWriterAdapter<T extends Indicator> extends AbstractWriterAdapter implements UrlTypeable, Identifiable<T,String> {

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
    protected AbstractIndicatorWriterAdapter(Connection conn, RequestExecutor executor, Class<? extends ApiEntitySingleResponse> singleType) {
        super(conn, executor);

        this.singleType = singleType;
    }

    /**
     * API call to save T
     *
     * @param indicators Collection of indicators with type T
     * @return Collection {@link java.util.List} with type T objects
     * @throws IOException When the HTTPS API request fails due to IO issues
     */
    public WriteListResponse<T> create(List<T> indicators) throws IOException {
        return createList("v2.indicators.type", singleType, indicators);
    }

   public WriteListResponse<T> update(List<T> indicatorList) throws IOException, FailedResponseException {

        List<String> idList = new ArrayList<>();
        for(T it : indicatorList)    idList.add( getId(it) );
        WriteListResponse<T> data = updateListWithParam("v2.indicators.type.byId", singleType, null, null, "id", idList, indicatorList);

        return data;
    }


    /**
     *
     * API call to retrieve single T.
     * <p>
     * Per the ThreatConnect User Documentation:
     * </p>
     * <div style="margin-left: 2em; border-left: solid 2px gray; padding-left: 2em;"><i>
     * By default, all requests that do not include an Owner are assumed to be
     * for the API User’s Organization.
     * </i></div>
     *
     * @param indicator The type T object to save
     * @return T object when it exists in ThreatConnect
     * @throws IOException When the HTTPS API request fails due to IO issues
     * @throws com.cyber2.api.lib.exception.FailedResponseException When the API
     * responds with an error and the request is unsuccessful
     */
    public T create(T indicator) throws IOException, FailedResponseException {
        return create(indicator, null);
    }

    public T create(T indicator, String ownerName)
        throws IOException, FailedResponseException {

        Object item = createItem("v2.indicators.type", singleType, ownerName, null, indicator);

        return (T) singleType.cast(item).getData().getData();
    }

    public T update(T indicator) throws IOException, FailedResponseException {
        return update(indicator, null);
    }

    public T update(T indicator, String ownerName)
        throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", getId(indicator) );
        Object item = updateItem("v2.indicators.type.byId", singleType, ownerName, map, indicator);

        return (T) singleType.cast(item).getData().getData();
    }
    
    public WriteListResponse<Attribute> createAttributes(String indicatorId, List<Attribute> attributes)
        throws IOException {
        return createAttributes(indicatorId, attributes, null);
    }

    public WriteListResponse<Attribute> createAttributes(String indicatorId, List<Attribute> attributes, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse data = createList("v2.indicators.type.byId.attributes", AttributeListResponse.class, ownerName, map, attributes);

        return data;
    }

    public Attribute createAttribute(String indicatorId, Attribute attribute) throws IOException, FailedResponseException {
        return createAttribute(indicatorId, attribute, null);
    }

    public Attribute createAttribute(String indicatorId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        AttributeResponse item = createItem("v2.indicators.type.byId.attributes", AttributeResponse.class, ownerName, map, attribute);

        return (Attribute) item.getData().getData();
    }

   public WriteListResponse<Attribute> updateAttributes(String indicatorId, List<Attribute> attributes)
        throws IOException {
        return updateAttributes(indicatorId, attributes, null);
    }

    public WriteListResponse<Attribute> updateAttributes(String indicatorId, List<Attribute> attributes, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Attribute> data = updateList("v2.indicators.type.byId.attributes.byId", AttributeListResponse.class, ownerName, map, attributes);

        return data;
    }

    public Attribute updateAttribute(String indicatorId, Attribute attribute) throws IOException, FailedResponseException {
        return updateAttribute(indicatorId, attribute, null);
    }

    public Attribute updateAttribute(String indicatorId, Attribute attribute, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        AttributeResponse item = updateItem("v2.indicators.type.byId.attributes.byId", AttributeResponse.class, ownerName, map, attribute);

        return (Attribute) item.getData().getData();
    }

    public WriteListResponse<Adversary> associateGroupAdversaries(String indicatorId, List<Integer> associateGroupAdversaryIdList) throws IOException {
        return associateGroupAdversaries(indicatorId, associateGroupAdversaryIdList, null);
    }

    public WriteListResponse<Adversary> associateGroupAdversaries(String indicatorId, List<Integer> associateGroupAdversaryIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Adversary> data = createListWithParam("v2.indicators.type.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "indicatorId", associateGroupAdversaryIdList);

        return data;
    }

    public Adversary associateGroupAdversary(String indicatorId, Integer associateGroupAdversaryId) throws IOException, FailedResponseException {
        return associateGroupAdversary(indicatorId, associateGroupAdversaryId, null);
    }

    public Adversary associateGroupAdversary(String indicatorId, Integer associateGroupAdversaryId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "indicatorId", associateGroupAdversaryId);
        AdversaryResponse data = createItem("v2.indicators.type.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return (Adversary) data.getData().getData();
    }

    public WriteListResponse<Email> associateGroupEmails(String indicatorId, List<Integer> associateGroupEmailIdList) throws IOException {
        return associateGroupEmails(indicatorId, associateGroupEmailIdList, null);
    }

    public WriteListResponse<Email> associateGroupEmails(String indicatorId, List<Integer> associateGroupEmailIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Email> data = createListWithParam("v2.indicators.type.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "indicatorId", associateGroupEmailIdList);

        return data;
    }

    public Email associateGroupEmail(String indicatorId, Integer associateGroupEmailId) throws IOException, FailedResponseException {
        return associateGroupEmail(indicatorId, associateGroupEmailId, null);
    }

    public Email associateGroupEmail(String indicatorId, Integer associateGroupEmailId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "indicatorId", associateGroupEmailId);
        EmailResponse data = createItem("v2.indicators.type.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return (Email) data.getData().getData();
    }

    public WriteListResponse<Incident> associateIncidents(String indicatorId, List<Integer> associateIncidentIdList) throws IOException {
        return associateIncidents(indicatorId, associateIncidentIdList, null);
    }

    public WriteListResponse<Incident> associateIncidents(String indicatorId, List<Integer> associateIncidentIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Incident> data = createListWithParam("v2.indicators.type.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "indicatorId", associateIncidentIdList);

        return data;
    }

    public Incident associateIncident(String indicatorId, Integer associateIncidentId) throws IOException, FailedResponseException {
        return associateIncident(indicatorId, associateIncidentId, null);
    }

    public Incident associateIncident(String indicatorId, Integer associateIncidentId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "indicatorId", associateIncidentId);
        IncidentResponse data = createItem("v2.indicators.type.byId.indicators.groups.byGroupId", IncidentResponse.class, ownerName, map, null);

        return (Incident) data.getData().getData();
    }

    public WriteListResponse<Signature> associateSignatures(String indicatorId, List<Integer> associateSignatureIdList) throws IOException {
        return associateSignatures(indicatorId, associateSignatureIdList, null);
    }

    public WriteListResponse<Signature> associateSignatures(String indicatorId, List<Integer> associateSignatureIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Signature> data = createListWithParam("v2.indicators.type.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "indicatorId", associateSignatureIdList);

        return data;
    }

    public boolean associateSignature(String indicatorId, Integer associateSignatureId) throws IOException, FailedResponseException {
        return associateSignature(indicatorId, associateSignatureId, null);
    }

    public boolean associateSignature(String indicatorId, Integer associateSignatureId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "indicatorId", associateSignatureId);
        ApiEntitySingleResponse data = createItem("v2.indicators.type.byId.groups.signatures.byGroupId", ApiEntitySingleResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<Threat> associateGroupThreats(String indicatorId, List<Integer> associateGroupThreatIdList) throws IOException {
        return associateGroupThreats(indicatorId, associateGroupThreatIdList, null);
    }

    public WriteListResponse<Threat> associateGroupThreats(String indicatorId, List<Integer> associateGroupThreatIdList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Threat> data = createListWithParam("v2.indicators.type.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "indicatorId", associateGroupThreatIdList);

        return data;
    }

    public boolean associateGroupThreat(String indicatorId, Integer associateGroupThreatId) throws IOException, FailedResponseException {
        return associateGroupThreat(indicatorId, associateGroupThreatId, null);
    }

    public boolean associateGroupThreat(String indicatorId, Integer associateGroupThreatId, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "indicatorId", associateGroupThreatId);
        ApiEntitySingleResponse data = createItem("v2.indicators.type.byId.groups.threats.byGroupId", ApiEntitySingleResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<SecurityLabel> associateSecurityLabels(String indicatorId, List<String> associateSecurityLabelList) throws IOException {
        return associateSecurityLabels(indicatorId, associateSecurityLabelList, null);
    }

    public WriteListResponse<SecurityLabel> associateSecurityLabels(String indicatorId, List<String> associateSecurityLabelList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<SecurityLabel> data = createListWithParam("v2.indicators.type.byId.securityLabels.byName", SecurityLabelResponse.class, ownerName, map, "securityLabelName", associateSecurityLabelList);

        return data;
    }

    public boolean associateSecurityLabel(String indicatorId, String associateSecurityLabel) throws IOException, FailedResponseException {
        return associateSecurityLabel(indicatorId, associateSecurityLabel, null);
    }

    public boolean associateSecurityLabel(String indicatorId, String associateSecurityLabel, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "securityLabelName", associateSecurityLabel);
        ApiEntitySingleResponse data = createItem("v2.indicators.type.byId.securityLabels.byName", ApiEntitySingleResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

    public WriteListResponse<Tag> associateTags(String indicatorId, List<String> associateTagList) throws IOException {
        return associateTags(indicatorId, associateTagList, null);
    }

    public WriteListResponse<Tag> associateTags(String indicatorId, List<String> associateTagList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", indicatorId);
        WriteListResponse<Tag> data = createListWithParam("v2.indicators.type.byId.tags.byName", TagResponse.class, ownerName, map, "tagName", associateTagList);

        return data;
    }

    public boolean associateTag(String indicatorId, String associateTag) throws IOException, FailedResponseException {
        return associateTag(indicatorId, associateTag, null);
    }

    public boolean associateTag(String indicatorId, String associateTag, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", indicatorId, "tagName", associateTag);
        TagResponse data = createItem("v2.indicators.type.byId.tags.byName", TagResponse.class, ownerName, map, null);

        return data.isSuccess();
    }

}
