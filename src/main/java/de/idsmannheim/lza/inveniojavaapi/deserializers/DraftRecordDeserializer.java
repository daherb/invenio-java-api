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
import de.idsmannheim.lza.inveniojavaapi.DraftRecord;
import de.idsmannheim.lza.inveniojavaapi.FilesOptions;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Deserializer for DraftRecord
 */
public class DraftRecordDeserializer extends StdDeserializer<DraftRecord> {
     
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
        return new DraftRecord(access, files, metadata);
    }
    
}
