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

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class LanguageDeserializer extends StdDeserializer<Metadata.Language> {

    
    public LanguageDeserializer() {
        this(null);
    }
    public LanguageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.Language deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        String langText;
        JsonNode node = p.getCodec().readTree(p);
        langText = node.get("id").asText();
        ControlledVocabulary.LanguageId lang;
        switch (langText.length()) {
            case 2 -> lang = ControlledVocabulary.LanguageIdFactory.usingId2(langText);
            case 3 -> lang = ControlledVocabulary.LanguageIdFactory.usingId3(langText);
            default -> throw new IllegalArgumentException("Invalid language code");
        }
        return new Metadata.Language(lang);
    }
}
