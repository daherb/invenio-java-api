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
public class AdditionalDescriptionDeserializer extends StdDeserializer<Metadata.AdditionalDescription> {

    public static class DescriptionTypeDeserializer extends StdDeserializer<Metadata.AdditionalDescription.DescriptionType> {

        public DescriptionTypeDeserializer() {
            this(null);
        }
        
        public DescriptionTypeDeserializer(Class<?> vc) {
            super(vc);
        }

        
        @Override
        public Metadata.AdditionalDescription.DescriptionType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            String idText = node.get("id").asText();
            ControlledVocabulary.DescriptionTypeId id = new ControlledVocabulary.DescriptionTypeId(idText);
            Metadata.LocalizedStrings titles = new Metadata.LocalizedStrings();
            if (node.has("title")) {
                titles.addAll(new ObjectMapper().registerModule(new Jdk8Module())
                        .readValue(node.get("title").toString(), Metadata.LocalizedStrings.class));
            }
            return new Metadata.AdditionalDescription.DescriptionType(id, titles);
        }
        
    }
    
    public AdditionalDescriptionDeserializer() {
        this(null);
    }
    public AdditionalDescriptionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.AdditionalDescription deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        String description = node.get("description").asText();
        Metadata.AdditionalDescription.DescriptionType type =
                om.readValue(node.get("type").toString(), Metadata.AdditionalDescription.DescriptionType.class);
        Metadata.AdditionalDescription additionalDescription = new Metadata.AdditionalDescription(description, type);
        if (node.has("lang"))
            additionalDescription.setLang(om.readValue(node.get("lang").toString(),Metadata.Language.class));
        return additionalDescription;
    }
}
