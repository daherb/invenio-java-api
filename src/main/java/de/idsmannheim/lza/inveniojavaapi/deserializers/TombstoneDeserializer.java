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
import de.idsmannheim.lza.inveniojavaapi.Tombstone;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class TombstoneDeserializer extends StdDeserializer<Tombstone> {

    public static class UserDeserializer extends StdDeserializer<Tombstone.User> {
        
        public UserDeserializer() {
            this(null);
        }
        
        public UserDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Tombstone.User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            return new Tombstone.User(node.get("user").asInt());
        }
    }
    public TombstoneDeserializer() {
        this(null);
    }
    
    public TombstoneDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Tombstone deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        String reason = node.get("reason").asText();
        String category = node.get("category").asText();
        Tombstone.User removedBy = om.readValue(node.get("removed_by").toString(), Tombstone.User.class);
        Date timestamp = om.readValue(node.get("timestamp").toString(), Date.class);
        return new Tombstone(reason, category, removedBy, timestamp);
    }
    
}
