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
import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata.PersonOrOrg;
import de.idsmannheim.lza.inveniojavaapi.Metadata.PersonOrOrg.Identifier;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class PersonOrOrgDeserializer extends StdDeserializer<PersonOrOrg> {

    public static class IdentifierDeserializer extends StdDeserializer<PersonOrOrg.Identifier> {

        public IdentifierDeserializer() {
            this(null);
        }
        
        public IdentifierDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Identifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            String id = node.get("identifier").asText();
            ControlledVocabulary.PersonOrOrgIdentifierScheme scheme = 
                    new ControlledVocabulary.PersonOrOrgIdentifierScheme(node.get("scheme").asText());
            return new PersonOrOrg.Identifier(scheme, id);
        }
    }
    
    public PersonOrOrgDeserializer() {
        this(null);
    }
    
    public PersonOrOrgDeserializer(Class<?> vc) {
        super(vc);
    }

    
    @Override
    public PersonOrOrg deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String typeStr = node.get("type").asText();
        ArrayList<Identifier>  identifiers = new ArrayList<>();
        if (node.has("identifiers")) {
            identifiers.addAll(
                new ObjectMapper().readerForListOf(Identifier.class).readValue(node.get("identifiers"))
            );
        }
        switch (typeStr) {
            case "personal" -> {
                if (node.has("name"))
                    return new PersonOrOrg(
                            node.get("given_name").asText(),
                            node.get("family_name").asText(),
                            node.get("name").asText(),
                            identifiers);
                else
                    return new PersonOrOrg(
                        node.get("given_name").asText(),
                        node.get("family_name").asText(),
                        identifiers);
            }
            case "organizational" -> {
                return new PersonOrOrg(
                        node.get("name").asText(),
                        identifiers);
            }
            default -> throw new IllegalArgumentException("PersonOrOrg type is invalid: " + typeStr);
        }
    }
}
