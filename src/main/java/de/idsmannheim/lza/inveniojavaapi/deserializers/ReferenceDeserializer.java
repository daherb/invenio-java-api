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
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ReferenceDeserializer extends StdDeserializer<Metadata.Reference> {

    public ReferenceDeserializer() {
        this(null);
    }
    public ReferenceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Reference deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<ControlledVocabulary.ReferenceScheme> scheme = Optional.empty();
        Optional<String> identifier = Optional.empty();
        JsonNode node = p.getCodec().readTree(p);
        String reference = node.get("reference").asText();
        if (node.has("scheme")) {
            scheme = Optional.of(new ControlledVocabulary.ReferenceScheme(node.get("scheme").asText()));
        }
        if (node.has("identifier")) {
            identifier = Optional.of(node.get("identifier").asText());
        }
        return new Metadata.Reference(reference, scheme, identifier);
    }
}
