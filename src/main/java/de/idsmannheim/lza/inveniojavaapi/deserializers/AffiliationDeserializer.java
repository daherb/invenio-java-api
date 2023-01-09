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
import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary.OrganizationalOrInstitutionalId;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Affiliation;
import java.io.IOException;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class AffiliationDeserializer extends StdDeserializer<Affiliation> {

    public AffiliationDeserializer() {
        this(null);
    }
    public AffiliationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Affiliation deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        Optional<OrganizationalOrInstitutionalId> id = Optional.empty();
        Optional<String> name = Optional.empty();
        if (node.has("id") && node.get("id").isTextual()) {
            id = Optional.of(new OrganizationalOrInstitutionalId(node.get("id").asText()));
        }
        if (node.has("name") && node.get("name").isTextual()) {
            name = Optional.of(node.get("name").asText());
        }
        return new Affiliation(id,name);
    }
}
