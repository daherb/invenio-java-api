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
import de.idsmannheim.lza.inveniojavaapi.Access;
import de.idsmannheim.lza.inveniojavaapi.ExternalPid;
import de.idsmannheim.lza.inveniojavaapi.FilesOptions;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import de.idsmannheim.lza.inveniojavaapi.Record;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 *
 * Deserializer for Record
 */
public class RecordDeserializer extends StdDeserializer<Record> {
    
    public static class ParentDeserializer extends StdDeserializer<Record.Parent> {
        
        public ParentDeserializer() {
            this(null);
        }
        
        public ParentDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Record.Parent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            String id = node.get("id").asText();
            Record.Parent parent = new Record.Parent(id);
            if (node.has("communities")) {
                parent.addCommunities(om.readerForMapOf(Object.class).readValue(node.get("communities").toString()));
            }
            return parent;
        }
    }
    
    public static class VersionsDeserializer extends StdDeserializer<Record.Versions> {
        
        public VersionsDeserializer() {
            this(null);
        }
        
        public VersionsDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Record.Versions deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            int index = node.get("index").asInt();
            boolean latest = node.get("is_latest").asBoolean();
            Record.Versions versions = new Record.Versions(index, latest);
            if (node.has("is_latest_draft")) {
                versions.setLatestDraft(node.get("is_latest_draft").asBoolean());
            }
            return versions;
        }
    }
    
    public RecordDeserializer() {
        this(null);
    }
    
    public RecordDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Record deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Access access = om.readValue(node.get("access").toString(), Access.class);
        Date created = om.readValue(node.get("created").toString(), Date.class);
        HashMap<String,Object> customFields = om.readerForMapOf(Object.class).readValue(node.get("custom_fields").toString());
        FilesOptions files = om.readValue(node.get("files").toString(), FilesOptions.class);
        String id = node.get("id").asText();
        boolean isDraft = node.get("is_draft").asBoolean();
        boolean isPublished = node.get("is_published").asBoolean();
        HashMap<String,String> links = om.readerForMapOf(String.class).readValue(node.get("links").toString());
        Metadata metadata = om.readValue(node.get("metadata").toString(), Metadata.class);
        Record.Parent parent = om.readValue(node.get("parent").toString(), Record.Parent.class);
        HashMap<String,ExternalPid> pids = om.readerForMapOf(ExternalPid.class).readValue(node.get("pids").toString());
        int revisionId = node.get("revision_id").asInt();
        String status = node.get("status").asText();
        Date updated = om.readValue(node.get("updated").toString(), Date.class);
        Record.Versions versions = om.readValue(node.get("versions").toString(), Record.Versions.class);
        return new Record(access, created, files, id, isDraft, isPublished, metadata, parent, revisionId, status, updated, versions)
                .addCustomFields(customFields)
                .addLinks(links)
                .addPids(pids);
    }
}
