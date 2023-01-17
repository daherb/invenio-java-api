/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class API {
    
    public static String PROTOCOL = "https";
    public static String HOST = "repos-devel2.ids-mannheim.de:5000";
    public static String TOKEN = "voCZ7NcC0lwmCmluJlxhW5m1BYJjKQoHBIyxgmabuJCyEIzsFG6yE7JHwxe8";
    public static String API_RECORDS = "/api/records";
    public static String API_USER_RECORDS = "/api/user/records";
    
    /**
     * Create a suitable HttpClient
     * 
     * @return The created Client
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     */
    public static HttpClient getHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        HttpClient client = HttpClient.newBuilder()
                .version(Version.HTTP_1_1)
                .build();
        return client;
    }
    
    /**
     * Create a suitable HttRequest Builder including header
     * 
     * @param uri The URI to be requested
     * @return 
     */
    public static HttpRequest.Builder getHttpRequestBuilder(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .header("Authorization", "Bearer " + TOKEN)
                .header("Accept", "application/json")
                .header("Application-Type", "application/json");
    }
    
    /**
     * Creates a draft record (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#create-a-draft-record)
     * 
     * @param draftRecord The record object combining access, files and metadata fields
     * @return The created record
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     */
    public static Record createDraftRecord(DraftRecord draftRecord) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS, "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(draftRecord)))
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), Record.class);
        
    }
    
    /**
     * Get a draft record (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#get-a-draft-record)
     * 
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @return The draft record
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     * @throws IOException
     * @throws InterruptedException 
     */
    public static DraftRecord getDraftRecord(String id) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET().build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(),DraftRecord.class);
    }
    
    /**
     * Update a draft record
     * 
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param draftRecord The record object combining access, files and metadata fields
     * @return The updated record object
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     */
    public static DraftRecord updateDraftRecord(String id, DraftRecord draftRecord) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .PUT(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(draftRecord)))
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), DraftRecord.class);
        
    }
    
    /**
     * Publish a draft record (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#publish-a-draft-record)
     * 
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @return The created record
     * @throws java.net.URISyntaxException 
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.security.KeyManagementException 
     * @throws java.io.IOException 
     * @throws java.lang.InterruptedException 
     */
    public static DraftRecord publishDraftRecord(String id) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/actions/publish", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), DraftRecord.class);
    }
    
    /**
     * Edit a published record (Create a draft record from a published record)
     * (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#edit-a-published-record-create-a-draft-record-from-a-published-record)
     * 
     * 
     * @param id
     * @return 
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static DraftRecord createDraftFromPublished(String id) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), DraftRecord.class);
    }
    
    /**
     * Delete/discard a draft record (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#deletediscard-a-draft-record)

     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws java.io.IOException
     * @throws java.lang.InterruptedException
     */
    public static void deleteDraftRecord(String id) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .DELETE()
                .build();
        getHttpClient().send(request,BodyHandlers.ofString());
    }
    
    
    /**
     * Search all records (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#search-records)
     * @return The list of records  
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     */
    // TODO add search parameters
    public static Records searchRecords() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI("https", "//" + HOST + API_RECORDS, "");
        
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), Records.class);
        
    }
    
    /**
     * Lists user records (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#user-records)
     * @return List of records
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     */
    // TODO add query parameters
    public static HttpResponse<String> listUserRecords() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI("https", "//" + HOST + API_USER_RECORDS, "");
        
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        return getHttpClient().send(request,BodyHandlers.ofString());
        
    }
}
