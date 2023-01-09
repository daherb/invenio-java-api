/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class LocalizedStringsDeserializer extends StdDeserializer<Metadata.LocalizedStrings> {

    
    public LocalizedStringsDeserializer() {
        this(null);
    }
    public LocalizedStringsDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.LocalizedStrings deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        Metadata.LocalizedStrings strings = new Metadata.LocalizedStrings();
        Iterator<Map.Entry<String,JsonNode>> it = node.fields();
        while (it.hasNext()) {
            Map.Entry<String,JsonNode> field = it.next();
            strings.add(new Metadata.Language(ControlledVocabulary.LanguageIdFactory.usingId2(field.getKey())), field.getValue().asText());
        }
        return strings;
    }
}
