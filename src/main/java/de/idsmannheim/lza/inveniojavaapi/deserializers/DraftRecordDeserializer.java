/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.idsmannheim.lza.inveniojavaapi.Access;
import de.idsmannheim.lza.inveniojavaapi.DraftRecord;
import de.idsmannheim.lza.inveniojavaapi.Record;
import de.idsmannheim.lza.inveniojavaapi.ExternalPid;
import de.idsmannheim.lza.inveniojavaapi.FilesOptions;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import de.idsmannheim.lza.inveniojavaapi.Parent;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Deserializer for DraftRecord
 */
public class DraftRecordDeserializer extends StdDeserializer<DraftRecord> {
    
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    
    public DraftRecordDeserializer() {
        this(null);
    }
    
    public DraftRecordDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public DraftRecord deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Access access = om.readValue(node.get("access").toString(), Access.class);
        FilesOptions files = om.readValue(node.get("files").toString(), FilesOptions.class);
        Metadata metadata = om.readValue(node.get("metadata").toString(), Metadata.class);
        DraftRecord record = new DraftRecord(access, files, metadata);
        if (node.has("created")) {
            try {
                record.setCreated(dateFormater.parse(node.get("created").asText()));
            } catch (ParseException ex) {
                throw new JsonParseException(p, "Error parsing date", ex);
            }
        }
        if (node.has("expires_at")) {
            try {
                record.setExpiresAt(dateFormater.parse(node.get("expires_at").asText()));
            } catch (ParseException ex) {
                throw new JsonParseException(p, "Error parsing date", ex);
            }
        }
        if (node.has("id")) {
            record.setId(node.get("id").asText());
        }
        if (node.has("is_published")) {
            record.setIsPublished(node.get("is_published").asBoolean());
        }
        if (node.has("links")) {
            record.addLinks(om.readerForMapOf(String.class).readValue(node.get("links").toString()));
        }
        if (node.has("parent")) {
            record.setParent(om.readValue(node.get("parent").toString(), Parent.class));
        }
        if (node.has("pids")) {
            record.addPids(om.readerForMapOf(ExternalPid.class).readValue(node.get("pids").toString()));
        }
        if (node.has("revision_id")) {
            record.setRevisionId(node.get("revision_id").asInt());
        }
        if (node.has("updated")) {
            try {
                record.setUpdated(dateFormater.parse(node.get("updated").asText()));
            } catch (ParseException ex) {
                throw new JsonParseException(p, "Error parsing date", ex);
            }
        }
        if (node.has("versions")) {
            record.setVersions(om.readValue(node.get("versions").toString(),Record.Versions.class));
        }
        return record;
    }
    
}
