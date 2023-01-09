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
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class FundingReferenceDeserializer extends StdDeserializer<Metadata.FundingReference> {

    public static class FunderDeserializer extends StdDeserializer<Metadata.FundingReference.Funder> {

    public FunderDeserializer() {
        this(null);
    }
    
    public FunderDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.FundingReference.Funder deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<ControlledVocabulary.FunderId> id = Optional.empty();
        Optional<String> name = Optional.empty();
        JsonNode node = p.getCodec().readTree(p);
        if (node.has("id")) {
            id = Optional.of(ControlledVocabulary.FunderIdFactory.usingId(node.get("id").asText()));
        }
        if (node.has("name")) {
            name = Optional.of(node.get("name").asText());
        }
        return new Metadata.FundingReference.Funder(id, name);
    }
    
}
    public static class AwardDeserializer extends StdDeserializer<Metadata.FundingReference.Award> {

    public AwardDeserializer() {
        this(null);
    }
    public AwardDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.FundingReference.Award deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<ControlledVocabulary.AwardId> id = Optional.empty();
        Metadata.LocalizedStrings title = new Metadata.LocalizedStrings();
        Optional<String> number = Optional.empty();
        ArrayList<Metadata.AlternateIdentifier> identifiers = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        if (node.has("id")) {
            id = Optional.of(new ControlledVocabulary.AwardId(node.get("id").asText()));
        }
        if (node.has("title")) {
            title.addAll(om.readValue(node.get("title").toString(), Metadata.LocalizedStrings.class));
        }
        if (node.has("number")) {
            number = Optional.of(node.get("number").asText());
        }
        if (node.has("identifiers")) {
            identifiers.addAll(om.readerForListOf(Metadata.AlternateIdentifier.class)
                .readValue(node.get("identifiers").toString()));
        }
        return new Metadata.FundingReference.Award(id, title, number, identifiers);
    }
    
}
    public FundingReferenceDeserializer() {
        this(null);
    }
    public FundingReferenceDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.FundingReference deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Metadata.FundingReference.Funder funder = om.readValue(node.get("funder").toString(),
                Metadata.FundingReference.Funder.class);
        Metadata.FundingReference.Award award = om.readValue(node.get("award").toString(), 
                Metadata.FundingReference.Award.class);
        return new Metadata.FundingReference(funder,award);
    }
}
