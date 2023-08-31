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
import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class AlternateIdentifierDeserializer extends StdDeserializer<Metadata.AlternateIdentifier> {

    
    public AlternateIdentifierDeserializer() {
        this(null);
    }
    public AlternateIdentifierDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.AlternateIdentifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        // Minimal string valid as a DOI
        String identifier = "10.0/NOTYET";
        if (node.has("identifier") && !node.get("identifier").asText().isBlank()) {
            identifier = node.get("identifier").asText();
        }
        ControlledVocabulary.RecordIdentifierScheme scheme = 
                new ControlledVocabulary.RecordIdentifierScheme(node.get("scheme").asText());
        return new Metadata.AlternateIdentifier(identifier, scheme);
    }
}
