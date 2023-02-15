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
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ContributorDeserializer extends StdDeserializer<Metadata.Contributor> {

    
    public ContributorDeserializer() {
        this(null);
    }
    public ContributorDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Contributor deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Metadata.PersonOrOrg personOrOrg = om.readValue(node.get("person_or_org").toString(), Metadata.PersonOrOrg.class);
        ControlledVocabulary.Role role = new ControlledVocabulary.Role(node.get("role").asText());
        Metadata.Contributor contributor = new Metadata.Contributor(personOrOrg, role);
        if (node.has("affiliations")) {
            contributor.addAffiliations(om.readerForListOf(Metadata.Affiliation.class)
                    .readValue(node.get("affiliations").toString()));
        }
        return contributor;
    }
}
