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
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class LocationDeserializer extends StdDeserializer<Metadata.Location> {

    public static class LocationFeatureDeserializer extends StdDeserializer<Metadata.Location.LocationFeature> {

        public LocationFeatureDeserializer() {
            this(null);
        }
        
        public LocationFeatureDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Metadata.Location.LocationFeature deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            Optional<Metadata.Location.LocationGeometry> geometry = Optional.empty();
            ArrayList<Metadata.Location.LocationIdentifier> identifiers = new ArrayList<>();
            Optional<String> place = Optional.empty();
            Optional<String> description = Optional.empty();
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            if (node.has("geometry")) {
                geometry = Optional.of(om.readValue(node.get("geometry").toString(),
                        Metadata.Location.LocationGeometry.class));
            }
            if (node.has("identifiers")) {
                identifiers.addAll(om.readerForListOf(Metadata.Location.LocationIdentifier.class)
                        .readValue(node.get("identifiers").toString()));
            }
            if (node.has("place")) {
                place  = Optional.of(node.get("place").asText());
            }
            if (node.has("description")) {
                description = Optional.of(node.get("description").asText());
            }
            return new Metadata.Location.LocationFeature(geometry, identifiers, place, description);
        }
        
        
    }
    
    public static class LocationIdentifierDeserializer extends StdDeserializer<Metadata.Location.LocationIdentifier> {

        public LocationIdentifierDeserializer() {
            this(null);
        }
        
        public LocationIdentifierDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Metadata.Location.LocationIdentifier deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            String identifier = node.get("identifier").asText();
            String scheme = node.get("scheme").asText();
            return new Metadata.Location.LocationIdentifier(identifier, scheme);
        }
        
        
    }
    
    public static class LocationGeometryDeserializer extends StdDeserializer<Metadata.Location.LocationGeometry> {

        public LocationGeometryDeserializer() {
            this(null);
        }
        
        public LocationGeometryDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Metadata.Location.LocationGeometry deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            String type = node.get("type").asText();
            ArrayList<Object> coordinates =
                    om.readerForListOf(Object.class)
                            .readValue(node.get("coordinates").toString());
            return new Metadata.Location.LocationGeometry(type, coordinates);
        }
        
        
    }
    
    public LocationDeserializer() {
        this(null);
    }
    public LocationDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Location deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
        ArrayList<Metadata.Location.LocationFeature> features =
                om.readerForListOf(Metadata.Location.LocationFeature.class)
                        .readValue(node.get("features").toString());
        return new Metadata.Location(features);
    }
    
}
