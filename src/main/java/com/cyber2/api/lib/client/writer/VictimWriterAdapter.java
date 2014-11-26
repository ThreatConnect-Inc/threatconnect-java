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
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.VictimEmailAddress;
import com.cyber2.api.lib.server.entity.VictimNetworkAccount;
import com.cyber2.api.lib.server.entity.VictimPhone;
import com.cyber2.api.lib.server.entity.VictimSocialNetwork;
import com.cyber2.api.lib.server.entity.VictimWebSite;
import com.cyber2.api.lib.server.response.entity.AdversaryListResponse;
import com.cyber2.api.lib.server.response.entity.AdversaryResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressListResponse;
import com.cyber2.api.lib.server.response.entity.EmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.EmailListResponse;
import com.cyber2.api.lib.server.response.entity.EmailResponse;
import com.cyber2.api.lib.server.response.entity.IncidentListResponse;
import com.cyber2.api.lib.server.response.entity.IncidentResponse;
import com.cyber2.api.lib.server.response.entity.VictimResponse;
import com.cyber2.api.lib.server.response.entity.VictimListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureListResponse;
import com.cyber2.api.lib.server.response.entity.SignatureResponse;
import com.cyber2.api.lib.server.response.entity.ThreatListResponse;
import com.cyber2.api.lib.server.response.entity.ThreatResponse;
import com.cyber2.api.lib.server.response.entity.VictimEmailAddressListResponse;
import com.cyber2.api.lib.server.response.entity.VictimEmailAddressResponse;
import com.cyber2.api.lib.server.response.entity.VictimNetworkAccountListResponse;
import com.cyber2.api.lib.server.response.entity.VictimNetworkAccountResponse;
import com.cyber2.api.lib.server.response.entity.VictimPhoneListResponse;
import com.cyber2.api.lib.server.response.entity.VictimPhoneResponse;
import com.cyber2.api.lib.server.response.entity.VictimSocialNetworkListResponse;
import com.cyber2.api.lib.server.response.entity.VictimSocialNetworkResponse;
import com.cyber2.api.lib.server.response.entity.VictimWebSiteListResponse;
import com.cyber2.api.lib.server.response.entity.VictimWebSiteResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author dtineo
 */
public class VictimWriterAdapter extends AbstractWriterAdapter {

    protected VictimWriterAdapter(Connection conn, RequestExecutor executor) {
        super(conn, executor);
    }

    public WriteListResponse<Victim> create(List<Victim> victims) throws IOException, FailedResponseException {

        WriteListResponse data = createList("v2.victims", VictimListResponse.class, victims);

        return data;
    }

    public Victim create(Victim victim) throws IOException, FailedResponseException {
        return create(victim, null);
    }

    public Victim create(Victim victim, String ownerName)
        throws IOException, FailedResponseException {

        VictimResponse item = createItem("v2.victims", VictimResponse.class, ownerName, null, victim);

        return (Victim) item.getData().getData();
    }


    public WriteListResponse<Victim> update(List<Victim> victimList) throws IOException, FailedResponseException {

        List<Integer> idList = new ArrayList<>();
        for(Victim it : victimList)    idList.add( it.getId() );
        WriteListResponse<Victim> data = updateListWithParam("v2.victims.byId", VictimListResponse.class, null, null, "id", idList, victimList);

        return data;
    }

    public Victim update(Victim victim) throws IOException, FailedResponseException {
        return update(victim, null);
    }

    public Victim update(Victim victim, String ownerName)
        throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victim.getId());
        VictimResponse item = updateItem("v2.victims.byId", VictimResponse.class, ownerName, map, victim);

        return (Victim) item.getData().getData();
    }

    public WriteListResponse<Adversary> associateAdversaries(Integer victimId, List<Integer> adversaryIdList) throws IOException{
        return associateAdversaries(victimId, adversaryIdList, null); 
    }

    public WriteListResponse<Adversary> associateAdversaries(Integer victimId, List<Integer> adversaryIdList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<Adversary> data = createListWithParam("v2.victims.byId.groups.adversaries.byGroupId", AdversaryListResponse.class, ownerName, map, "groupId", adversaryIdList);

        return data;
    }

    public Adversary associateAdversary(Integer victimId, Integer adversaryId) throws IOException, FailedResponseException {
        return associateAdversary(victimId, adversaryId, null); 
    }

    public Adversary associateAdversary(Integer victimId, Integer adversaryId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victimId, "groupId", adversaryId);
        AdversaryResponse data = createItem("v2.victims.byId.groups.adversaries.byGroupId", AdversaryResponse.class, ownerName, map, null);

        return (Adversary)data.getData().getData();
    }

    public WriteListResponse<Email> associateEmails(Integer victimId, List<Integer> emailIdList) throws IOException{
        return associateEmails(victimId, emailIdList, null); 
    }

    public WriteListResponse<Email> associateEmails(Integer victimId, List<Integer> emailIdList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<Email> data = createListWithParam("v2.victims.byId.groups.emails.byGroupId", EmailListResponse.class, ownerName, map, "groupId", emailIdList);

        return data;
    }

    public Email associateEmail(Integer victimId, Integer emailId) throws IOException, FailedResponseException {
        return associateEmail(victimId, emailId, null); 
    }

    public Email associateEmail(Integer victimId, Integer emailId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victimId, "groupId", emailId);
        EmailResponse data = createItem("v2.victims.byId.groups.emails.byGroupId", EmailResponse.class, ownerName, map, null);

        return (Email)data.getData().getData();
    }

    public WriteListResponse<Incident> associateIncidents(Integer victimId, List<Integer> incidentIdList) throws IOException{
        return associateIncidents(victimId, incidentIdList, null); 
    }

    public WriteListResponse<Incident> associateIncidents(Integer victimId, List<Integer> incidentIdList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<Incident> data = createListWithParam("v2.victims.byId.groups.incidents.byGroupId", IncidentListResponse.class, ownerName, map, "groupId", incidentIdList);

        return data;
    }

    public Incident associateIncident(Integer victimId, Integer incidentId) throws IOException, FailedResponseException {
        return associateIncident(victimId, incidentId, null); 
    }

    public Incident associateIncident(Integer victimId, Integer incidentId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victimId, "groupId", incidentId);
        IncidentResponse data = createItem("v2.victims.byId.groups.incidents.byGroupId", IncidentResponse.class, ownerName, map, null);

        return (Incident)data.getData().getData();
    }

    public WriteListResponse<Signature> associateSignatures(Integer victimId, List<Integer> signatureIdList) throws IOException{
        return associateSignatures(victimId, signatureIdList, null); 
    }

    public WriteListResponse<Signature> associateSignatures(Integer victimId, List<Integer> signatureIdList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<Signature> data = createListWithParam("v2.victims.byId.groups.signatures.byGroupId", SignatureListResponse.class, ownerName, map, "groupId", signatureIdList);

        return data;
    }

    public Signature associateSignature(Integer victimId, Integer signatureId) throws IOException, FailedResponseException {
        return associateSignature(victimId, signatureId, null); 
    }

    public Signature associateSignature(Integer victimId, Integer signatureId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victimId, "groupId", signatureId);
        SignatureResponse data = createItem("v2.victims.byId.groups.signatures.byGroupId", SignatureResponse.class, ownerName, map, null);

        return (Signature)data.getData().getData();
    }

    
    public WriteListResponse<Threat> associateThreats(Integer victimId, List<Integer> threatIdList) throws IOException{
        return associateThreats(victimId, threatIdList, null); 
    }

    public WriteListResponse<Threat> associateThreats(Integer victimId, List<Integer> threatIdList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<Threat> data = createListWithParam("v2.victims.byId.groups.threats.byGroupId", ThreatListResponse.class, ownerName, map, "groupId", threatIdList);

        return data;
    }

    public Threat associateThreat(Integer victimId, Integer threatId) throws IOException, FailedResponseException {
        return associateThreat(victimId, threatId, null); 
    }

    public Threat associateThreat(Integer victimId, Integer threatId, String ownerName) 
            throws IOException, FailedResponseException {

        Map<String,Object> map = createParamMap("id", victimId, "groupId", threatId);
        ThreatResponse data = createItem("v2.victims.byId.groups.threats.byGroupId", ThreatResponse.class, ownerName, map, null);

        return (Threat)data.getData().getData();
    }


    public WriteListResponse<VictimEmailAddress> createVictimAssetEmailAddresses(Integer victimId, List<VictimEmailAddress> victimEmailAddressList) throws IOException {
        return createVictimAssetEmailAddresses(victimId, victimEmailAddressList, null); 
    }

    public WriteListResponse<VictimEmailAddress> createVictimAssetEmailAddresses(Integer victimId, List<VictimEmailAddress> victimEmailAddressList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<VictimEmailAddress> data = createList("v2.victims.byId.victimAssets.emailAddresses", VictimEmailAddressListResponse.class, ownerName, map, victimEmailAddressList);

        return data;
    }

    public VictimEmailAddress createVictimAssetEmailAddress(Integer victimId, VictimEmailAddress victimEmailAddress) throws IOException, FailedResponseException {
        return createVictimAssetEmailAddress(victimId,victimEmailAddress, null); 
    }
            
    public VictimEmailAddress createVictimAssetEmailAddress(Integer victimId, VictimEmailAddress victimEmailAddress, String ownerName)
            throws IOException, FailedResponseException {
        
        Map<String,Object> map = createParamMap("id", victimId);
        VictimEmailAddressResponse data = createItem("v2.victims.byId.victimAssets.emailAddresses"
                                            , VictimEmailAddressResponse.class, ownerName, map, victimEmailAddress);

        return (VictimEmailAddress)data.getData().getData();
    }

    public WriteListResponse<VictimNetworkAccount> createVictimAssetNetworkAccounts(Integer victimId, List<VictimNetworkAccount> victimNetworkAccountList) throws IOException {
        return createVictimAssetNetworkAccounts(victimId, victimNetworkAccountList, null); 
    }

    public WriteListResponse<VictimNetworkAccount> createVictimAssetNetworkAccounts(Integer victimId, List<VictimNetworkAccount> victimNetworkAccountList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<VictimNetworkAccount> data = createList("v2.victims.byId.victimAssets.networkAccounts", VictimNetworkAccountListResponse.class, ownerName, map, victimNetworkAccountList);

        return data;
    }

    public VictimNetworkAccount createVictimAssetNetworkAccount(Integer victimId, VictimNetworkAccount victimNetworkAccount) throws IOException, FailedResponseException {
        return createVictimAssetNetworkAccount(victimId,victimNetworkAccount, null); 
    }
            
    public VictimNetworkAccount createVictimAssetNetworkAccount(Integer victimId, VictimNetworkAccount victimNetworkAccount, String ownerName)
            throws IOException, FailedResponseException {
        
        Map<String,Object> map = createParamMap("id", victimId);
        VictimNetworkAccountResponse data = createItem("v2.victims.byId.victimAssets.networkAccounts"
                                            , VictimNetworkAccountResponse.class, ownerName, map, victimNetworkAccount);

        return (VictimNetworkAccount)data.getData().getData();
    }

    public WriteListResponse<VictimPhone> createVictimAssetPhones(Integer victimId, List<VictimPhone> victimPhoneList) throws IOException {
        return createVictimAssetPhones(victimId, victimPhoneList, null); 
    }

    public WriteListResponse<VictimPhone> createVictimAssetPhones(Integer victimId, List<VictimPhone> victimPhoneList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<VictimPhone> data = createList("v2.victims.byId.victimAssets.phones", VictimPhoneListResponse.class, ownerName, map, victimPhoneList);

        return data;
    }

    public VictimPhone createVictimAssetPhone(Integer victimId, VictimPhone victimPhone) throws IOException, FailedResponseException {
        return createVictimAssetPhone(victimId,victimPhone, null); 
    }
            
    public VictimPhone createVictimAssetPhone(Integer victimId, VictimPhone victimPhone, String ownerName)
            throws IOException, FailedResponseException {
        
        Map<String,Object> map = createParamMap("id", victimId);
        VictimPhoneResponse data = createItem("v2.victims.byId.victimAssets.phones"
                                            , VictimPhoneResponse.class, ownerName, map, victimPhone);

        return (VictimPhone)data.getData().getData();
    }

    public WriteListResponse<VictimSocialNetwork> createVictimAssetSocialNetworks(Integer victimId, List<VictimSocialNetwork> victimSocialNetworkList) throws IOException {
        return createVictimAssetSocialNetworks(victimId, victimSocialNetworkList, null); 
    }

    public WriteListResponse<VictimSocialNetwork> createVictimAssetSocialNetworks(Integer victimId, List<VictimSocialNetwork> victimSocialNetworkList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<VictimSocialNetwork> data = createList("v2.victims.byId.victimAssets.socialNetworks", VictimSocialNetworkListResponse.class, ownerName, map, victimSocialNetworkList);

        return data;
    }

    public VictimSocialNetwork createVictimAssetSocialNetwork(Integer victimId, VictimSocialNetwork victimSocialNetwork) throws IOException, FailedResponseException {
        return createVictimAssetSocialNetwork(victimId,victimSocialNetwork, null); 
    }
            
    public VictimSocialNetwork createVictimAssetSocialNetwork(Integer victimId, VictimSocialNetwork victimSocialNetwork, String ownerName)
            throws IOException, FailedResponseException {
        
        Map<String,Object> map = createParamMap("id", victimId);
        VictimSocialNetworkResponse data = createItem("v2.victims.byId.victimAssets.socialNetworks"
                                            , VictimSocialNetworkResponse.class, ownerName, map, victimSocialNetwork);

        return (VictimSocialNetwork)data.getData().getData();
    }

    public WriteListResponse<VictimWebSite> createVictimAssetWebSites(Integer victimId, List<VictimWebSite> victimWebSiteList) throws IOException {
        return createVictimAssetWebSites(victimId, victimWebSiteList, null); 
    }

    public WriteListResponse<VictimWebSite> createVictimAssetWebSites(Integer victimId, List<VictimWebSite> victimWebSiteList, String ownerName) 
            throws IOException {

        Map<String,Object> map = createParamMap("id", victimId);
        WriteListResponse<VictimWebSite> data = createList("v2.victims.byId.victimAssets.websites", VictimWebSiteListResponse.class, ownerName, map, victimWebSiteList);

        return data;
    }

    public VictimWebSite createVictimAssetWebSite(Integer victimId, VictimWebSite victimWebSite) throws IOException, FailedResponseException {
        return createVictimAssetWebSite(victimId,victimWebSite, null); 
    }
            
    public VictimWebSite createVictimAssetWebSite(Integer victimId, VictimWebSite victimWebSite, String ownerName)
            throws IOException, FailedResponseException {
        
        Map<String,Object> map = createParamMap("id", victimId);
        VictimWebSiteResponse data = createItem("v2.victims.byId.victimAssets.websites"
                                            , VictimWebSiteResponse.class, ownerName, map, victimWebSite);

        return (VictimWebSite)data.getData().getData();
    }

    public WriteListResponse<VictimEmailAddress> updateVictimAssetEmailAddresses(Integer victimId, List<VictimEmailAddress> emailAddressList)
        throws IOException {
        return updateVictimAssetEmailAddresses(victimId, emailAddressList, null);
    }

    public WriteListResponse<VictimEmailAddress> updateVictimAssetEmailAddresses(Integer victimId, List<VictimEmailAddress> emailAddressList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", victimId);
        List<Integer> idList = new ArrayList<>();
        for(VictimEmailAddress it : emailAddressList)    idList.add( it.getId() );
        WriteListResponse<VictimEmailAddress> data = updateListWithParam("v2.victims.byId.victimAssets.emailAddresses.byAssetId", EmailAddressListResponse.class, ownerName, map, "assetId", idList, emailAddressList);

        return data;
    }

    public VictimEmailAddress updateEmailAddress(Integer victimId, EmailAddress emailAddress) throws IOException, FailedResponseException {
        return updateEmailAddress(victimId, emailAddress, null);
    }

    public VictimEmailAddress updateEmailAddress(Integer victimId, EmailAddress emailAddress, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", victimId);
        EmailAddressResponse item = updateItem("v2.victims.byId.victimAssets.emailAddresses.byAssetId", EmailAddressResponse.class, ownerName, map, emailAddress);

        return (VictimEmailAddress) item.getData().getData();
    }

    public WriteListResponse<VictimNetworkAccount> updateVictimAssetNetworkAccounts(Integer victimId, List<VictimNetworkAccount> networkAccountList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", victimId);
        List<Integer> idList = new ArrayList<>();
        for(VictimNetworkAccount it : networkAccountList)    idList.add( it.getId() );
        WriteListResponse<VictimNetworkAccount> data = updateListWithParam("v2.victims.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountListResponse.class, ownerName, map, "assetId", idList, networkAccountList);

        return data;
    }

    public VictimNetworkAccount updateVictimAssetNetworkAccount(Integer victimId, VictimNetworkAccount networkAccount) throws IOException, FailedResponseException {
        return updateVictimAssetNetworkAccount(victimId, networkAccount, null);
    }

    public VictimNetworkAccount updateVictimAssetNetworkAccount(Integer victimId, VictimNetworkAccount networkAccount, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", victimId);
        VictimNetworkAccountResponse item = updateItem("v2.victims.byId.victimAssets.networkAccounts.byAssetId", VictimNetworkAccountResponse.class, ownerName, map, networkAccount);

        return (VictimNetworkAccount) item.getData().getData();
    }


    public WriteListResponse<VictimPhone> updateVictimAssetPhones(Integer victimId, List<VictimPhone> phoneNumberList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", victimId);
        List<Integer> idList = new ArrayList<>();
        for(VictimPhone it : phoneNumberList)    idList.add( it.getId() );
        WriteListResponse<VictimPhone> data = updateListWithParam("v2.victims.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneListResponse.class, ownerName, map, "assetId", idList, phoneNumberList);

        return data;
    }

    public VictimPhone updateVictimAssetPhone(Integer victimId, VictimPhone phoneNumber) throws IOException, FailedResponseException {
        return updateVictimAssetPhone(victimId, phoneNumber, null);
    }

    public VictimPhone updateVictimAssetPhone(Integer victimId, VictimPhone phoneNumber, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", victimId);
        VictimPhoneResponse item = updateItem("v2.victims.byId.victimAssets.phoneNumbers.byAssetId", VictimPhoneResponse.class, ownerName, map, phoneNumber);

        return (VictimPhone) item.getData().getData();
    }


    public WriteListResponse<VictimSocialNetwork> updateVictimAssetSocialNetworks(Integer victimId, List<VictimSocialNetwork> socialNetworkList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", victimId);
        List<Integer> idList = new ArrayList<>();
        for(VictimSocialNetwork it : socialNetworkList)    idList.add( it.getId() );
        WriteListResponse<VictimSocialNetwork> data = updateListWithParam("v2.victims.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkListResponse.class, ownerName, map, "assetId", idList, socialNetworkList);

        return data;
    }

    public VictimSocialNetwork updateVictimAssetSocialNetwork(Integer victimId, VictimSocialNetwork socialNetwork) throws IOException, FailedResponseException {
        return updateVictimAssetSocialNetwork(victimId, socialNetwork, null);
    }

    public VictimSocialNetwork updateVictimAssetSocialNetwork(Integer victimId, VictimSocialNetwork socialNetwork, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", victimId);
        VictimSocialNetworkResponse item = updateItem("v2.victims.byId.victimAssets.socialNetworks.byAssetId", VictimSocialNetworkResponse.class, ownerName, map, socialNetwork);

        return (VictimSocialNetwork) item.getData().getData();
    }


    public WriteListResponse<VictimWebSite> updateVictimAssetWebSites(Integer victimId, List<VictimWebSite> websiteList, String ownerName)
        throws IOException {

        Map<String, Object> map = createParamMap("id", victimId);
        List<Integer> idList = new ArrayList<>();
        for(VictimWebSite it : websiteList)    idList.add( it.getId() );
        WriteListResponse<VictimWebSite> data = updateListWithParam("v2.victims.byId.victimAssets.websites.byAssetId", VictimWebSiteListResponse.class, ownerName, map, "assetId", idList, websiteList);

        return data;
    }

    public VictimWebSite updateVictimAssetWebSite(Integer victimId, VictimWebSite website) throws IOException, FailedResponseException {
        return updateVictimAssetWebSite(victimId, website, null);
    }

    public VictimWebSite updateVictimAssetWebSite(Integer victimId, VictimWebSite website, String ownerName)
        throws IOException, FailedResponseException {

        Map<String, Object> map = createParamMap("id", victimId);
        VictimWebSiteResponse item = updateItem("v2.victims.byId.victimAssets.websites.byAssetId", VictimWebSiteResponse.class, ownerName, map, website);

        return (VictimWebSite) item.getData().getData();
    }

    
}
