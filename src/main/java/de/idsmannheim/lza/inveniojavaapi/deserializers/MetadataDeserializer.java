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
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class MetadataDeserializer extends StdDeserializer<Metadata> {

    
    public MetadataDeserializer() {
        this(null);
    }
    public MetadataDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Metadata deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        ArrayList<Metadata.Creator> creators = new ArrayList<>();
        JsonNode node = p.getCodec().readTree(p);
        ObjectMapper om = new ObjectMapper()
                .registerModule(new Jdk8Module());
        Metadata.ResourceType resourceType = om.readValue(node.get("resource_type").toString(), Metadata.ResourceType.class);
        creators.addAll(om.readerForListOf(Metadata.Creator.class)
                .readValue(node.get("creators")));
        String title = node.get("title").asText();
        // Handle missing publication date when creating a new version
        // https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#create-a-new-version
        Metadata.ExtendedDateTimeFormat0 date;
        if (node.has("publication_date")) {
            date = om.readValue(node.get("publication_date").toString(), Metadata.ExtendedDateTimeFormat0.class);
        }
        else {
            Calendar c = new Calendar.Builder().setInstant(Instant.now().toEpochMilli()).build();
            date = new Metadata.ExtendedDateTimeFormat0(String.valueOf(c.get(Calendar.YEAR)));
        }
        Metadata metadata = new Metadata(resourceType, creators, title, date);
        if (node.has("additional_titles")) {
            metadata.addAdditionalTitles(om.readerForListOf(Metadata.AdditionalTitle.class).readValue(node.get("additional_titles").toString()));
        }
        if (node.has("additional_descriptions")) {
            metadata.addAdditionalDescriptions(om.readerForListOf(Metadata.AdditionalDescription.class).readValue(node.get("additional_descriptions").toString()));
        }
        if (node.has("rights")) {
            metadata.addRights(om.readerForListOf(Metadata.License.class).readValue(node.get("rights").toString()));
        }
        if (node.has("contributors")) {
            metadata.addContributors(om.readerForListOf(Metadata.Contributor.class).readValue(node.get("contributors").toString()));
        }
        if (node.has("subjects")) {
            metadata.addSubjects(om.readerForListOf(Metadata.Subject.class).readValue(node.get("subjects").toString()));
        }
        if (node.has("languages")) {
            metadata.addLanguages(om.readerForListOf(Metadata.Language.class).readValue(node.get("languages").toString()));
        }
        if (node.has("dates")) {
            metadata.addDates(om.readerForListOf(Metadata.Date.class).readValue(node.get("dates").toString()));
        }
        if (node.has("identifiers")) {
            metadata.addAlternativeIdentifiers(om.readerForListOf(Metadata.AlternateIdentifier.class).readValue(node.get("identifiers").toString()));
        }
        if (node.has("related_identifiers")) {
            metadata.addRelatedIdentifiers(om.readerForListOf(Metadata.RelatedIdentifier.class).readValue(node.get("related_identifiers").toString()));
        }
        if (node.has("locations")) {
            metadata.setLocations(om.readValue(node.get("locations").toString(), Metadata.Location.class));
        }
        if (node.has("funding")) {
            metadata.addFundingReferences(om.readerForListOf(Metadata.FundingReference.class).readValue(node.get("funding").toString()));
        }
        if (node.has("references")) {
            metadata.addReferences(om.readerForListOf(Metadata.Reference.class).readValue(node.get("references").toString()));
        }
        return metadata;
    }
}
