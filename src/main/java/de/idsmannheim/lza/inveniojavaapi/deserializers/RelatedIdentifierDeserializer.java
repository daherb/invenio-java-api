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
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class RelatedIdentifierDeserializer extends StdDeserializer<Metadata.RelatedIdentifier> {

    public static class RelationTypeDeserializer extends StdDeserializer<Metadata.RelatedIdentifier.RelationType> {
        
        
        public RelationTypeDeserializer() {
            this(null);
        }
        public RelationTypeDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Metadata.RelatedIdentifier.RelationType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            ControlledVocabulary.RelationTypeId id =
                    new ControlledVocabulary.RelationTypeId(node.get("id").asText());
            Metadata.LocalizedStrings titles = om.readValue(node.get("title").toString(),
                    Metadata.LocalizedStrings.class);
            return new Metadata.RelatedIdentifier.RelationType(id, titles);
        }
        
    }
    
    public static class RelatedResourceTypeDeserializer extends StdDeserializer<Metadata.RelatedIdentifier.RelatedResourceType> {
        
        public RelatedResourceTypeDeserializer() {
            this(null);
        }
        public RelatedResourceTypeDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Metadata.RelatedIdentifier.RelatedResourceType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            ControlledVocabulary.RelatedResourceType id =
                    new ControlledVocabulary.RelatedResourceType(node.get("id").asText());
            Metadata.LocalizedStrings titles = om.readValue(node.get("title").toString(),
                    Metadata.LocalizedStrings.class);
            return new Metadata.RelatedIdentifier.RelatedResourceType(id, titles);
        }
    }
    
    public RelatedIdentifierDeserializer() {
        this(null);
    }
    public RelatedIdentifierDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.RelatedIdentifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<Metadata.RelatedIdentifier.RelatedResourceType> resourceType = Optional.empty();
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        String identifier = node.get("identifier").asText();
        ControlledVocabulary.RelatedRecordIdentifierScheme scheme = 
                new ControlledVocabulary.RelatedRecordIdentifierScheme(node.get("scheme").asText());
        Metadata.RelatedIdentifier.RelationType relationType = 
                om.readValue(node.get("relation_type").toString(), 
                        Metadata.RelatedIdentifier.RelationType.class);
        if (node.has("resource_type")) {
                resourceType = Optional.of(om.readValue(node.get("resource_type").toString(), 
                        Metadata.RelatedIdentifier.RelatedResourceType.class));
        }
        return new Metadata.RelatedIdentifier(identifier, scheme, relationType, resourceType);
    }
}
