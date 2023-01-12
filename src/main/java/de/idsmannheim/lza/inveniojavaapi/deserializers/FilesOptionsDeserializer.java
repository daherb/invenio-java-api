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
import de.idsmannheim.lza.inveniojavaapi.FilesOptions;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class FilesOptionsDeserializer extends StdDeserializer<FilesOptions> {

    
    public FilesOptionsDeserializer() {
        this(null);
    }
    
    public FilesOptionsDeserializer(Class<?> vc) {
        super(vc);
    }
    
    @Override
    public FilesOptions deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        boolean enabled = node.get("enabled").asBoolean();
        String defaultPreview = null;
        if (node.has("default_preview")) {
            defaultPreview = node.get("default_preview").asText();
        }
        ArrayList<String> order = new ArrayList<>();
        if (node.has("order")) {
            order.addAll(om.readerForListOf(String.class).readValue(node.get("order").toString()));
        }
        return new FilesOptions(enabled, defaultPreview).addOrder(order);
    }
    
}
