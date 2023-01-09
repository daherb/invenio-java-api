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
public class DateDeserializer extends StdDeserializer<Metadata.Date> {

    public static class DateTypeDeserializer extends StdDeserializer<Metadata.Date.DateType> {
        
        
        public DateTypeDeserializer() {
            this(null);
        }
        
        public DateTypeDeserializer(Class<?> vc) {
            super(vc);
        }
        
        @Override
        public Metadata.Date.DateType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                    .registerModule(new Jdk8Module());
            ControlledVocabulary.DateTypeId id = new ControlledVocabulary.DateTypeId(node.get("id").asText());
            Metadata.LocalizedStrings title = om.readValue(node.get("title").toString(), Metadata.LocalizedStrings.class);
            return new Metadata.Date.DateType(id, title);
        }
    }
    
    public DateDeserializer() {
        this(null);
    }
    public DateDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<String> description = Optional.empty();
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Metadata.ExtendedDateTimeFormat0 date = om.readValue(node.get("date").toString(), Metadata.ExtendedDateTimeFormat0.class);
        Metadata.Date.DateType type = om.readValue(node.get("type").toString(), Metadata.Date.DateType.class);
        if (node.has("description"))
            description = Optional.of(node.get("description").asText());
        return new Metadata.Date(date, type, description);
    }
}
