/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.idsmannheim.lza.inveniojavaapi.Files;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class FilesDeserializer extends StdDeserializer<Files> {

    public static class FileEntryDeserializer extends StdDeserializer<Files.FileEntry> {
        
        public FileEntryDeserializer() {
            this(null);
        }
        
        public FileEntryDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Files.FileEntry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            String bucketId = node.get("bucket_id").asText();
            String checksum = node.get("checksum").asText();
            Date created = om.readValue(node.get("created").toString(), Date.class);
            String fileId = node.get("file_id").asText();
            String key = node.get("key").asText();
            String mimetype = node.get("mimetype").asText();
            int size = node.get("size").asInt();
            String status = node.get("status").asText();
            String storageClass = node.get("storage_class").asText();
            Date updated = om.readValue(node.get("updated").toString(), Date.class);
            String versionId = node.get("version_id").asText();
            Files.FileEntry entry = new Files.FileEntry(bucketId, checksum, created, fileId, key, mimetype, size, status, storageClass, updated, versionId);
            if (node.has("links")) {
                entry.addLinks(om.readerForMapOf(String.class).readValue(node.get("links")));
            }
            if (node.has("metadata")) {
                entry.addMetadata(om.readerForMapOf(Object.class).readValue(node.get("metadata")));
            }
            return entry;
        }
    }
    public FilesDeserializer() {
        this(null);
    }
    
    public FilesDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Files deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        boolean enabled = node.get("enabled").asBoolean();
        Files files = new Files(enabled);
        if (node.has("entries")) {
            if (node.get("entries").isArray()) {
                files.addEntries((ArrayList < Files.FileEntry >) om.readerForListOf(Files.FileEntry.class).readValue(node.get("entries").toString()));
            }
            else {
                files.addEntries((HashMap < String, Files.FileEntry >) om.readerForMapOf(Files.FileEntry.class).readValue(node.get("entries").toString()));
            }
        }
        if (node.has("default_preview")) {
            files.setDefaultPreview(node.get("default_preview").asText());
        }
        return files;
    }
    
}
