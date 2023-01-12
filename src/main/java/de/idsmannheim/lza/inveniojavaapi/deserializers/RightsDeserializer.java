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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class RightsDeserializer extends StdDeserializer<Metadata.License> {

    
    public RightsDeserializer() {
        this(null);
    }
    public RightsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.License deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        Optional<String> id = Optional.empty();
        Metadata.LocalizedStrings title = new Metadata.LocalizedStrings();
        Metadata.LocalizedStrings description = new Metadata.LocalizedStrings();
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        if (node.has("id")) {
            id = Optional.of(node.get("id").asText());
        }
        if (node.has("title")) {
            title.addAll(om.readValue(node.get("title").toString(), Metadata.LocalizedStrings.class));
        }
        if (node.has("description")) {
            description.addAll(om.readValue(node.get("description").toString(), Metadata.LocalizedStrings.class));
        }
        Metadata.License license = new Metadata.License(id, title, description);
        if (node.has("link")) {
            try {
                license.setLink(new URL(node.get("link").asText()));
            }
            catch (MalformedURLException e) {
            }
        }
        if (node.has("props")) {
            license.addProps(om.readerForMapOf(String.class).readValue(node.get("props").toString()));
        }
        return license;
    }
}
