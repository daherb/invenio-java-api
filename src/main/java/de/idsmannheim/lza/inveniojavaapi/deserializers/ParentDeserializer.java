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
import de.idsmannheim.lza.inveniojavaapi.Parent;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ParentDeserializer extends StdDeserializer<Parent> {

    public static class AccessDeserializer extends StdDeserializer<Parent.Access> {
        
        public AccessDeserializer() {
            this(null);
        }
        
        public AccessDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Parent.Access deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
            ArrayList<Parent.Owner> ownedBy = new ArrayList<>();
            ownedBy.addAll(om.readerForListOf(Parent.Owner.class).readValue(node.get("owned_by").toString()));
            return new Parent.Access(ownedBy);
        }
    }
    
    public static class OwnerDeserializer extends StdDeserializer<Parent.Owner> {
        
        public OwnerDeserializer() {
            this(null);
        }
        
        public OwnerDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Parent.Owner deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            int user = node.get("user").asInt();
            return new Parent.Owner(user);
        }
    }
    
    public ParentDeserializer() {
        this(null);
    }
    
    public ParentDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public Parent deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        String id = node.get("id").asText();
        Parent.Access access = om.readValue(node.get("access").toString(), Parent.Access.class);
        return new Parent(id, access);
    }
    
}
