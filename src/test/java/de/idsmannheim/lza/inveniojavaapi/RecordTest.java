/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.idsmannheim.lza.inveniojavaapi.Record.Versions;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class RecordTest {
    static ObjectMapper om = new ObjectMapper();
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void recordTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException {
        // Taken from https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#get-a-record
        String recordText = "{\n" +
                "  \"access\": {\n" +
                "    \"record\": \"restricted\",\n" +
                "    \"files\": \"restricted\",\n" +
                "    \"embargo\": {\n" +
                "      \"reason\": null,\n" +
                "      \"active\": false\n" +
                "    }\n" +
                "  },\n" +
                "  \"created\": \"2020-11-27 10:52:23.945755\",\n" +
                "  \"expires_at\": \"2020-11-27 10:52:23.945868\",\n" +
                "  \"files\": {\n" +
                "    \"enabled\": true\n" +
                "  },\n" +
                "  \"id\": \"{id}\",\n" +
                "  \"is_published\": true,\n" +
                "  \"links\": {\n" +
                "    \"latest\": \"{scheme+hostname}/api/records/{id}/versions/latest\",\n" +
                "    \"versions\": \"{scheme+hostname}/api/records/{id}/versions\",\n" +
                "    \"self_html\": \"{scheme+hostname}/records/{id}\",\n" +
                "    \"latest_html\": \"{scheme+hostname}/records/{id}/latest\",\n" +
                "    \"self\": \"{scheme+hostname}/api/records/{id}\",\n" +
                "    \"self_iiif_manifest\": \"{scheme+hostname}/api/records/{id}/manifest\",\n" +
                "    \"self_iiif_sequence\": \"{scheme+hostname}/api/records/{id}/sequence/default\",\n" +
                "    \"files\": \"{scheme+hostname}/api/records/{id}/files\",\n" +
                "    \"access_links\": \"{scheme+hostname}/api/records/{id}/access/links\",\n" +
                "    \"communities\": \"{scheme+hostname}/api/records/{id}/communities\",\n" +
                "    \"requests\": \"{scheme+hostname}/api/records/{id}/requests\",\n" +
                "    \"communities-suggestions\": \"{scheme+hostname}/api/records/{id}/communities-suggestions\"\n" +
                "  },\n" +
                "  \"metadata\": {\n" +
                "    \"resource_type\": {\n" +
                "      \"id\": \"image-photo\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Photo\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"title\": \"An Updated Romans story\",\n" +
                "    \"publication_date\": \"2020-06-01\",\n" +
                "    \"creators\": [\n" +
                "      {\n" +
                "        \"person_or_org\": {\n" +
                "          \"given_name\": \"Troy\",\n" +
                "          \"type\": \"personal\",\n" +
                "          \"name\": \"Brown, Troy\",\n" +
                "          \"family_name\": \"Brown\"\n" +
                "        }\n" +
                "      },\n" +
                "      {\n" +
                "        \"person_or_org\": {\n" +
                "          \"family_name\": \"Collins\",\n" +
                "          \"given_name\": \"Thomas\",\n" +
                "          \"identifiers\": [\n" +
                "            {\"scheme\": \"orcid\", \"identifier\": \"0000-0002-1825-0097\"}\n" +
                "          ],\n" +
                "          \"name\": \"Collins, Thomas\",\n" +
                "          \"type\": \"personal\"\n" +
                "        },\n" +
                "        \"affiliations\": [\n" +
                "          {\n" +
                "            \"id\": \"01ggx4157\",\n" +
                "            \"name\": \"European Organization for Nuclear Research\"\n" +
                "          }\n" +
                "        ]\n" +
                "      }\n" +
                "    ]\n" +
                "  },\n" +
                "  \"parent\": {\n" +
                "    \"id\": \"{parent-id}\",\n" +
                "    \"access\": {\n" +
                "      \"owned_by\": [\n" +
                "        {\n" +
                "            \"user\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"links\": []\n" +
                "    }\n" +
                "  },\n" +
                "  \"pids\": {},\n" +
                "  \"revision_id\": 3,\n" +
                "  \"updated\": \"2020-11-27 10:52:23.969244\",\n" +
                "  \"versions\": {\n" +
                "    \"index\": 1,\n" +
                "    \"is_latest\": true,\n" +
                "    \"is_latest_draft\": true\n" +
                "  }\n" +
                "}"; 
        try {
            Access access = new Access(Access.AccessType.Restricted, Access.AccessType.Restricted).setEmbargo(new Access.Embargo());
            Date created = dateFormater.parse("2020-11-27 10:52:23.945755");
            Date expiresAt = dateFormater.parse("2020-11-27 10:52:23.945868"); // Where should that go
            FilesOptions files = new FilesOptions(true);
            String id = "{id}";
            boolean isDraft = false;
            boolean isPublished = true;
            Metadata.ResourceType resourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto));
            ArrayList<Metadata.Creator> creators = new ArrayList<>();
            creators.add(new Metadata.Creator(new Metadata.PersonOrOrg("Troy", "Brown", "Brown, Troy")));
            creators.add(new Metadata.Creator(new Metadata.PersonOrOrg("Thomas", "Collins")
                    .addIdentifiers(new ArrayList<>(List.of(new Metadata.PersonOrOrg.Identifier(new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID), "0000-0002-1825-0097"))))
            )
                    .addAffiliations(new ArrayList<>(List.of(new Metadata.Affiliation(
                            Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)),
                            Optional.of("European Organization for Nuclear Research")))))
            );
            String title = "An Updated Romans story";
            Metadata.ExtendedDateTimeFormat0 publicationDate = new Metadata.ExtendedDateTimeFormat0("2020").addStartMonth("06").addStartDay("01");
            Metadata metadata = new Metadata(resourceType, creators, title, publicationDate);
            Record.Parent parent = new Record.Parent("{parent-id}");
            int revisionId = 3;
            String status = null;
            Date updated = dateFormater.parse("2020-11-27 10:52:23.969244");
            Versions versions = new Versions(1, true);
            Record record = new Record(access, created, files, id, isDraft, isPublished, metadata, parent, revisionId, status, updated, versions);
            HashMap<String,String> links = new HashMap<>();
            links.put("latest", "{scheme+hostname}/api/records/{id}/versions/latest");
            links.put("versions", "{scheme+hostname}/api/records/{id}/versions");
            links.put("self_html", "{scheme+hostname}/records/{id}");
            links.put("latest_html", "{scheme+hostname}/records/{id}/latest");
            links.put("self", "{scheme+hostname}/api/records/{id}");
            links.put("self_iiif_manifest", "{scheme+hostname}/api/records/{id}/manifest");
            links.put("self_iiif_sequence", "{scheme+hostname}/api/records/{id}/sequence/default");
            links.put("files", "{scheme+hostname}/api/records/{id}/files");
            links.put("access_links", "{scheme+hostname}/api/records/{id}/access/links");
            links.put("communities", "{scheme+hostname}/api/records/{id}/communities");
            links.put("requests", "{scheme+hostname}/api/records/{id}/requests");
            links.put("communities-suggestions", "{scheme+hostname}/api/records/{id}/communities-suggestions");
            record.addLinks(links);
            Record r2 = om.readValue(recordText, Record.class);
            Record r3 = om.readValue(om.writeValueAsString(record), Record.class);
            Assertions.assertEquals(r2,record);
            Assertions.assertEquals(r3,record);
        }
        catch (ParseException e) {
            Assertions.fail(e);
        }
    }
}
