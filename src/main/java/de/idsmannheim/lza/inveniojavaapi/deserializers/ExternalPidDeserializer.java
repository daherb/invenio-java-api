/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.idsmannheim.lza.inveniojavaapi.ExternalPid;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Deserializer for ExternalPid class
 */
public class ExternalPidDeserializer extends StdDeserializer<ExternalPid> {

    public ExternalPidDeserializer() {
        this(null);
    }
    
    public ExternalPidDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public ExternalPid deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String identifier = node.get("identifier").asText();
        String provider = node.get("provider").asText();
        ExternalPid pid = new ExternalPid(identifier, provider);
        if (node.has("client")) {
            pid.setClient(node.get("client").asText());
        }
        return pid;
    }
    
}
