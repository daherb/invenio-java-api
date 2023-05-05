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
import de.idsmannheim.lza.inveniojavaapi.Metadata.AdditionalTitle.TitleType;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class AdditionalTitleDeserializer extends StdDeserializer<Metadata.AdditionalTitle> {

    public static class TitleTypeDeserializer extends StdDeserializer<Metadata.AdditionalTitle.TitleType> {

        public TitleTypeDeserializer() {
            this(null);
        }
        
        public TitleTypeDeserializer(Class<?> vc) {
            super(vc);
        }

        
        @Override
        public Metadata.AdditionalTitle.TitleType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
            JsonNode node = p.getCodec().readTree(p);
            String idText = node.get("id").asText();
            ControlledVocabulary.TitleTypeId id = new ControlledVocabulary.TitleTypeId(idText);
            Metadata.LocalizedStrings titles = new Metadata.LocalizedStrings();
            if (node.has("title")) {
                titles.addAll(new ObjectMapper().registerModule(new Jdk8Module())
                        .readValue(node.get("title").toString(), Metadata.LocalizedStrings.class));
            }
            return new TitleType(id, titles);
        }
        
    }
    
    public AdditionalTitleDeserializer() {
        this(null);
    }
    public AdditionalTitleDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata.AdditionalTitle deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        String title = node.get("title").asText();
        Metadata.AdditionalTitle.TitleType type =
                om.readValue(node.get("type").toString(), Metadata.AdditionalTitle.TitleType.class);
        Metadata.AdditionalTitle additionalTitle = new Metadata.AdditionalTitle(title, type);
        if (node.has("lang"))
            additionalTitle.setLang(om.readValue(node.get("lang").toString(),Metadata.Language.class));
        return additionalTitle;
    }
    
}
