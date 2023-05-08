/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class DraftRecordTest {
    static ObjectMapper om = new ObjectMapper();
    private final SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void draftRecordTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException, ParseException {
        // Taken from https://inveniordm.docs.cern.ch/reference/rest_api_drafts_records/#get-a-draft-record
        String draftRecordText = "{\n" +
                "  \"access\": {\n" +
                "    \"record\": \"public\",\n" +
                "    \"files\": \"public\",\n" +
                "    \"embargo \": {\n" +
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
                "  \"is_published\": false,\n" +
                "  \"links\": {\n" +
                "    \"latest\": \"{scheme+hostname}/api/records/{id}/versions/latest\",\n" +
                "    \"versions\": \"{scheme+hostname}/api/records/{id}/versions\",\n" +
                "    \"self_html\": \"{scheme+hostname}/uploads/{id}\",\n" +
                "    \"publish\": \"{scheme+hostname}/api/records/{id}/draft/actions/publish\",\n" +
                "    \"latest_html\": \"{scheme+hostname}/records/{id}/latest\",\n" +
                "    \"self\": \"{scheme+hostname}/api/records/{id}/draft\",\n" +
                "    \"files\": \"{scheme+hostname}/api/records/{id}/draft/files\",\n" +
                "    \"access_links\": \"{scheme+hostname}/api/records/{id}/access/links\"\n" +
                "  },\n" +
                "  \"metadata\": {\n" +
                "    \"resource_type\": {\n" +
                "      \"id\": \"image-photo\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Photo\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"title\": \"A Romans story\",\n" +
                "    \"publication_date\": \"2020-06-01\",\n" +
                "    \"creators\": [\n" +
                "      {\n" +
                "      \"person_or_org\": {\n" +
                "        \"family_name\": \"Brown\",\n" +
                "        \"given_name\": \"Troy\",\n" +
                "        \"type\": \"personal\"\n" +
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
                "    \"is_latest\": false,\n" +
                "    \"is_latest_draft\": true\n" +
                "  }\n" +
                "}"; 
        Access access = new Access(Access.AccessType.Public, Access.AccessType.Public);
        FilesOptions files = new FilesOptions(true);
        HashMap<String,String> links = new HashMap<>();
        links.put("latest", "{scheme+hostname}/api/records/{id}/versions/latest");
        links.put("versions", "{scheme+hostname}/api/records/{id}/versions");
        links.put("self_html", "{scheme+hostname}/uploads/{id}");
        links.put("publish", "{scheme+hostname}/api/records/{id}/draft/actions/publish");
        links.put("latest_html", "{scheme+hostname}/records/{id}/latest");
        links.put("self", "{scheme+hostname}/api/records/{id}/draft");
        links.put("files", "{scheme+hostname}/api/records/{id}/draft/files");
        links.put("access_links", "{scheme+hostname}/api/records/{id}/access/links");
        Parent parent = new Parent("{parent-id}", new Parent.Access(new ArrayList<>(List.of(new Parent.Owner(0)))));
        Record.Versions versions = new Record.Versions(1, false).setLatestDraft(true);
        Metadata.ResourceType resourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto));
        ArrayList<Metadata.Creator> creators = new ArrayList<>();
        creators.add(new Metadata.Creator(new Metadata.PersonOrOrg("Troy","Brown")));
        creators.add(new Metadata.Creator(new Metadata.PersonOrOrg("Thomas","Collins", "Collins, Thomas")
                .addIdentifiers(new ArrayList<>(List.of(new Metadata.PersonOrOrg.Identifier(new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID), "0000-0002-1825-0097")))))
        .addAffiliations(List.of(new Metadata.Affiliation(Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)), Optional.of("European Organization for Nuclear Research"))))
        );
        String title = "A Romans story";
        Metadata.ExtendedDateTimeFormat0 publicationDate = new Metadata.ExtendedDateTimeFormat0("2020").addStartMonth("06").addStartDay("01");
        Metadata metadata = new Metadata(resourceType, creators, title, publicationDate);
        DraftRecord draftRecord = new DraftRecord(access, files, metadata)
                .setCreated(dateFormater.parse("2020-11-27 10:52:23.945755"))
                .setExpiresAt(dateFormater.parse("2020-11-27 10:52:23.945868"))
                .setId("{id}")
                .setIsPublished(Boolean.FALSE)
                .addLinks(links)
                .setParent(parent)
                .addPids(new HashMap<>())
                .setRevisionId(3)
                .setUpdated(dateFormater.parse("2020-11-27 10:52:23.969244"))
                .setVersions(versions);
        DraftRecord dr2 = om.readValue(draftRecordText, DraftRecord.class);
        DraftRecord dr3 = om.readValue(om.writeValueAsString(draftRecord), DraftRecord.class);
        Assertions.assertEquals(draftRecord,dr2);
        Assertions.assertEquals(draftRecord,dr3);
    }
    private static final Logger LOG = Logger.getLogger(DraftRecordTest.class.getName());
}
