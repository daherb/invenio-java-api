/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import de.idsmannheim.lza.inveniojavaapi.Access;
import de.idsmannheim.lza.inveniojavaapi.Access.AccessType;
import de.idsmannheim.lza.inveniojavaapi.DateFormater;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class AccessDeserializer extends StdDeserializer<Access> {

    public static class EmbargoDeserializer extends StdDeserializer<Access.Embargo> {
        
        public EmbargoDeserializer() {
            this(null);
        }
        
        public EmbargoDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Access.Embargo deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            boolean active = node.get("active").asBoolean();
            if (active) {
                Date until;
                try {
                    until = DateFormater.getInstance().parse(node.get("until").asText());
                }
                catch(ParseException e) {
                    throw new JsonParseException(p, "Exception while parsing date", e);
                }
                Optional<String> reason = Optional.empty();
                return new Access.Embargo(until, reason);
            }
            else
                return new Access.Embargo();
        }
    }
    public AccessDeserializer() {
        this(null);
    }
    
    public AccessDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Access deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Access.AccessType recordAccess = List.of(AccessType.values()).stream()
                .filter(t -> t.toString().equalsIgnoreCase(node.get("record").asText()))
                .findFirst().get();
        Access.AccessType fileAccess = List.of(AccessType.values()).stream()
                .filter(t -> t.toString().equalsIgnoreCase(node.get("files").asText()))
                .findFirst().get();;
        Access access = new Access(recordAccess, fileAccess);
        if (node.has("embargo")) {
            access.setEmbargo(om.readValue(node.get("embargo").toString(), Access.Embargo.class));
        }
        if (node.has("status")) {
            access.setStatus(node.get("status").asText());
        }
        return access;
    }
    
}
