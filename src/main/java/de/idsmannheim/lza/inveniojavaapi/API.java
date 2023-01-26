/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

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
     * List a draft's files (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#draft-files)
     */
    public static Files listDraftFiles(String id) throws NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException, URISyntaxException, URISyntaxException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        LOG.info(body);
        return om.readValue(body, Files.class);
    }
    
    /**
     * Start draft file upload(s) (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#start-draft-file-uploads)
     * 
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param entries Array of objects describing the file uploads to be initialized.
     * @return List of file entries for files to be uploaded
     */
    
    public static Files startDraftFileUpload(String id, ArrayList<Files.FileEntry> entries) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .POST(HttpRequest.BodyPublishers.ofString(om.writeValueAsString(entries)))
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        return om.readValue(body, Files.class);
    }
    
    /**
     * Upload a draft file's content (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#upload-a-draft-files-content)
     * 
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param filename Name of the file.
     * @param file The file object for the file content
     * @return The file entry for the uploaded file
     * @throws java.net.URISyntaxException 
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.security.KeyManagementException 
     * @throws com.fasterxml.jackson.core.JsonProcessingException 
     * @throws java.io.FileNotFoundException 
     * @throws java.lang.InterruptedException 
     */
    public static Files.FileEntry uploadDraftFile(String id, String filename, File file) throws URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, FileNotFoundException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files/" + URLEncoder.encode(filename,StandardCharsets.UTF_8.toString()) + "/content", "");
        HttpRequest request = getHttpRequestBuilder(uri)
                .header("Content-Type", "application/octet-stream")
                .PUT(HttpRequest.BodyPublishers.ofFile(file.toPath()))
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        return om.readValue(body, Files.FileEntry.class);
    }
    
    /**
     * Complete a draft file upload (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#complete-a-draft-file-upload)
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param filename Name of the file.
     * @return The complete file entry
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.lang.InterruptedException
     */
    public static Files.FileEntry completeDraftFileUpload(String id, String filename) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files/" + URLEncoder.encode(filename,StandardCharsets.UTF_8.toString()) + "/commit", "");
        LOG.info(uri.toString());
        HttpRequest request = getHttpRequestBuilder(uri)
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        return om.readValue(body, Files.FileEntry.class);
    }
    
    /**
     * Get metadata for a draft file (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#get-a-draft-files-metadata)
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param filename Name of the file.
     * @return The file entry
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.lang.InterruptedException
     */
    public static Files.FileEntry getDraftFileMetadata(String id, String filename) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files/" + URLEncoder.encode(filename,StandardCharsets.UTF_8.toString()), "");
        LOG.info(uri.toString());
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        return om.readValue(body, Files.FileEntry.class);
    }
    
    /**
     * Download  a draft file (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#download-a-draft-file)
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param filename Name of the file.
     * @return The file stream pointing to the message body
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.lang.InterruptedException
     */
    public static InputStream getDraftFileContent(String id, String filename) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files/" + URLEncoder.encode(filename,StandardCharsets.UTF_8.toString())+ "/content", "");
        LOG.info(uri.toString());
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        return getHttpClient().send(request,BodyHandlers.ofInputStream()).body();
    }
    
    /**
     * Download  a draft file (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#download-a-draft-file)
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @param filename Name of the file.
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.lang.InterruptedException
     */
    public static void deleteDraftFile(String id, String filename) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id + "/draft/files/" + URLEncoder.encode(filename,StandardCharsets.UTF_8.toString()), "");
        LOG.info(uri.toString());
        HttpRequest request = getHttpRequestBuilder(uri)
                .DELETE()
                .build();
        getHttpClient().send(request,BodyHandlers.discarding());
    }
    
        /**
     * Get a record (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#get-a-record)
     * @param id Identifier of the record, e.g. 4d0ns-ntd89
     * @return The record for the given id
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.lang.InterruptedException
     */
    public static Record getRecord(String id) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JsonProcessingException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI(PROTOCOL, "//" + HOST + API_RECORDS + "/" + id, "");
        LOG.info(uri.toString());
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        String body = getHttpClient().send(request,BodyHandlers.ofString()).body();
        return om.readValue(body, Record.class);
    }
    
    /**
     * Search records (https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#search-records)
     * @param query Search query used to filter results based on ElasticSearch's query string syntax.
     * @param sort  Sort search results.
     * @param size Specify number of items in the results page (default: 10).
     * @param page Specify the page of results.
     * @param allVersions Specify if all versions should be included.
     * @return The list of records  
     * @throws IOException
     * @throws InterruptedException
     * @throws URISyntaxException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException 
     * @throws java.lang.NoSuchMethodException 
     */
    // TODO add search parameters
    public static Records searchRecords(Optional<String> query, Optional<String> sort, Optional<Integer> size, Optional<Integer> page, Optional<Boolean> allVersions) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        StringBuilder uriString = new StringBuilder();
        uriString.append("//").append(HOST).append(API_RECORDS);
        boolean hasPrevParam = false;
        if (query.isPresent() || sort.isPresent() || size.isPresent() || page.isPresent() || allVersions.isPresent())
            uriString.append("?");
        if (query.isPresent()) {
            uriString.append("q=").append(URLEncoder.encode(query.get(),StandardCharsets.UTF_8.toString()));
            hasPrevParam = true;
        }
        if (sort.isPresent()) {
            if (hasPrevParam)
                uriString.append("&");
            uriString.append("sort=").append(URLEncoder.encode(sort.get(),StandardCharsets.UTF_8.toString()));
            hasPrevParam = true;
        }
        if (size.isPresent()) {
            if (hasPrevParam)
                uriString.append("&");
            uriString.append("size=").append(URLEncoder.encode(size.get().toString(),StandardCharsets.UTF_8.toString()));
            hasPrevParam = true;
        }
        if (page.isPresent()) {
            if (hasPrevParam)
                uriString.append("&");
            uriString.append("sort=").append(URLEncoder.encode(sort.get(),StandardCharsets.UTF_8.toString()));
            hasPrevParam = true;
        }
        if (allVersions.isPresent()) {
            if (hasPrevParam)
                uriString.append("&");
            uriString.append("all_versions=").append(URLEncoder.encode(allVersions.get().toString().toLowerCase(),StandardCharsets.UTF_8.toString()));
        }
        URI uri = new URI("https", uriString.toString(), ""); //("https", "//" + HOST + API_RECORDS, "");
        
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
    public static Records listUserRecords() throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        URI uri = new URI("https", "//" + HOST + API_USER_RECORDS, "");
        
        HttpRequest request = getHttpRequestBuilder(uri)
                .GET()
                .build();
        return om.readValue(getHttpClient().send(request,BodyHandlers.ofString()).body(), Records.class);
        
    }
}
