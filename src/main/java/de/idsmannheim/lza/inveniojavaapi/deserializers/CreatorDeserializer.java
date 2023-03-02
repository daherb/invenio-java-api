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
import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Affiliation;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Creator;
import de.idsmannheim.lza.inveniojavaapi.Metadata.PersonOrOrg;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class CreatorDeserializer extends StdDeserializer<Creator> {

    public CreatorDeserializer() {
        this(null);
    }
    public CreatorDeserializer(Class<?> vc) {
        super(vc);
    }

    public static class CreatorRoleDeserializer extends StdDeserializer<Creator.Role> {
        
        public CreatorRoleDeserializer() {
            this(null);
        }
        public CreatorRoleDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Creator.Role deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            return new Creator.Role(new ControlledVocabulary.Role(node.get("id").asText()));
        }
    }
    
    @Override
    public Creator deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        PersonOrOrg personOrOrg = new ObjectMapper().readValue(node.get("person_or_org").toString(), PersonOrOrg.class);
        Creator creator = new Creator(personOrOrg);
        if (node.has("role") && node.get("role").isTextual()) {
             creator.setRole(om.readValue(node.get("role").toString(),Creator.Role.class));
        }
        if (node.has("affiliations")) {
            creator.addAffiliations(new ObjectMapper().readerForListOf(Affiliation.class).readValue(node.get("affiliations")));
        }
        return creator;
        
    }
}
