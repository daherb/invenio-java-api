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
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;
import java.net.URL;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class SubjectDeserializer extends StdDeserializer<Metadata.Subject> {

    
    public SubjectDeserializer() {
        this(null);
    }
    public SubjectDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Subject deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        if (node.has("id"))
            return new Metadata.Subject(new URL(node.get("id").asText()));
        else if (node.has("subject"))
            return new Metadata.Subject(node.get("subject").asText());
        else
            throw new IllegalArgumentException("Either id or subject are required");
    }
}
