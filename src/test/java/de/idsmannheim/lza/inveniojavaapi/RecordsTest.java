package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.idsmannheim.lza.inveniojavaapi.Records.Aggregation;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Tests for Records class
 */
@SpringBootTest
public class RecordsTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void recordsTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException, ParseException {
        // Taken from own repository data
        String recordsText = "{\n" +
                "  \"aggregations\": {\n" +
                "    \"access_status\": {\n" +
                "      \"buckets\": [\n" +
                "        {\n" +
                "          \"doc_count\": 1,\n" +
                "          \"is_selected\": false,\n" +
                "          \"key\": \"metadata-only\",\n" +
                "          \"label\": \"Metadata-only\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"label\": \"Access status\"\n" +
                "    },\n" +
                "    \"resource_type\": {\n" +
                "      \"buckets\": [\n" +
                "        {\n" +
                "          \"doc_count\": 1,\n" +
                "          \"inner\": {\n" +
                "            \"buckets\": []\n" +
                "          },\n" +
                "          \"is_selected\": false,\n" +
                "          \"key\": \"other\",\n" +
                "          \"label\": \"Other\"\n" +
                "        }\n" +
                "      ],\n" +
                "      \"label\": \"Resource types\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"hits\": {\n" +
                "    \"hits\": [\n" +
                "      {\n" +
                "        \"access\": {\n" +
                "          \"embargo\": {\n" +
                "            \"active\": false,\n" +
                "            \"reason\": null\n" +
                "          },\n" +
                "          \"files\": \"public\",\n" +
                "          \"record\": \"public\",\n" +
                "          \"status\": \"metadata-only\"\n" +
                "        },\n" +
                "        \"created\": \"2023-01-11T12:52:28.945163+00:00\",\n" +
                "        \"custom_fields\": {},\n" +
                "        \"files\": {\n" +
                "          \"enabled\": false,\n" +
                "          \"order\": []\n" +
                "        },\n" +
                "        \"id\": \"vxacz-xhj42\",\n" +
                "        \"is_draft\": false,\n" +
                "        \"is_published\": true,\n" +
                "        \"links\": {\n" +
                "          \"access_links\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/access/links\",\n" +
                "          \"draft\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/draft\",\n" +
                "          \"files\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/files\",\n" +
                "          \"latest\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/versions/latest\",\n" +
                "          \"latest_html\": \"https://repos-devel2.ids-mannheim.de:5000/records/vxacz-xhj42/latest\",\n" +
                "          \"reserve_doi\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/draft/pids/doi\",\n" +
                "          \"self\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42\",\n" +
                "          \"self_html\": \"https://repos-devel2.ids-mannheim.de:5000/records/vxacz-xhj42\",\n" +
                "          \"self_iiif_manifest\": \"https://repos-devel2.ids-mannheim.de:5000/api/iiif/record:vxacz-xhj42/manifest\",\n" +
                "          \"self_iiif_sequence\": \"https://repos-devel2.ids-mannheim.de:5000/api/iiif/record:vxacz-xhj42/sequence/default\",\n" +
                "          \"versions\": \"https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/versions\"\n" +
                "        },\n" +
                "        \"metadata\": {\n" +
                "          \"creators\": [\n" +
                "            {\n" +
                "              \"person_or_org\": {\n" +
                "                \"name\": \"IDS Mannheim\",\n" +
                "                \"type\": \"organizational\"\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"publication_date\": \"2023-01-11\",\n" +
                "          \"publisher\": \"IDS-Repos2\",\n" +
                "          \"resource_type\": {\n" +
                "            \"id\": \"other\",\n" +
                "            \"title\": {\n" +
                "              \"de\": \"Sonstige\",\n" +
                "              \"en\": \"Other\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"rights\": [\n" +
                "            {\n" +
                "              \"description\": {\n" +
                "                \"en\": \"The Creative Commons Attribution license allows re-distribution and re-use of a licensed work on the condition that the creator is appropriately credited.\"\n" +
                "              },\n" +
                "              \"icon\": \"cc-by-icon\",\n" +
                "              \"id\": \"cc-by-4.0\",\n" +
                "              \"props\": {\n" +
                "                \"scheme\": \"spdx\",\n" +
                "                \"url\": \"https://creativecommons.org/licenses/by/4.0/legalcode\"\n" +
                "              },\n" +
                "              \"title\": {\n" +
                "                \"en\": \"Creative Commons Attribution 4.0 International\"\n" +
                "              }\n" +
                "            }\n" +
                "          ],\n" +
                "          \"title\": \"Blank\"\n" +
                "        },\n" +
                "        \"parent\": {\n" +
                "          \"communities\": {},\n" +
                "          \"id\": \"5k1hr-sxr38\"\n" +
                "        },\n" +
                "        \"pids\": {\n" +
                "          \"oai\": {\n" +
                "            \"identifier\": \"oai:ids-repos2.ids-mannheim.de:vxacz-xhj42\",\n" +
                "            \"provider\": \"oai\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"revision_id\": 3,\n" +
                "        \"status\": \"published\",\n" +
                "        \"updated\": \"2023-01-11T12:52:29.031012+00:00\",\n" +
                "        \"versions\": {\n" +
                "          \"index\": 1,\n" +
                "          \"is_latest\": true\n" +
                "        }\n" +
                "      }\n" +
                "    ],\n" +
                "    \"total\": 1\n" +
                "  },\n" +
                "  \"links\": {\n" +
                "    \"self\": \"https://repos-devel2.ids-mannheim.de:5000/api/records?page=1&size=25&sort=newest\"\n" +
                "  },\n" +
                "  \"sortBy\": \"newest\"\n" +
                "}"; 
        HashMap<String,Records.Aggregation> aggregations = new HashMap<>();
        ArrayList<Records.Bucket> accessBuckets = new ArrayList<>(
            Collections.singletonList(new Records.Bucket(1, false, "metadata-only", "Metadata-only")));
        aggregations.put("access_status", new Aggregation().setLabel("Access status").addBuckets(accessBuckets));
        ArrayList<Records.Bucket> resourceBuckets = new ArrayList<>(
            Collections.singletonList(new Records.Bucket(1, false, "other", "Other").setInner(new Aggregation()))
        );
        aggregations.put("resource_type", new Aggregation().setLabel("Resource types").addBuckets(resourceBuckets));
        ArrayList<Record> hitList = new ArrayList<>();
        HashMap<String, String> hitLinks = new HashMap<>();
        hitLinks.put("access_links", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/access/links");
        hitLinks.put("draft", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/draft");
        hitLinks.put("files", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/files");
        hitLinks.put("latest", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/versions/latest");
        hitLinks.put("latest_html", "https://repos-devel2.ids-mannheim.de:5000/records/vxacz-xhj42/latest");
        hitLinks.put("reserve_doi", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/draft/pids/doi");
        hitLinks.put("self", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42");
        hitLinks.put("self_html", "https://repos-devel2.ids-mannheim.de:5000/records/vxacz-xhj42");
        hitLinks.put("self_iiif_manifest", "https://repos-devel2.ids-mannheim.de:5000/api/iiif/record:vxacz-xhj42/manifest");
        hitLinks.put("self_iiif_sequence", "https://repos-devel2.ids-mannheim.de:5000/api/iiif/record:vxacz-xhj42/sequence/default");
        hitLinks.put("versions", "https://repos-devel2.ids-mannheim.de:5000/api/records/vxacz-xhj42/versions");
        HashMap<String, ExternalPid> pids = new HashMap<>();
        pids.put("oai", new ExternalPid("oai:ids-repos2.ids-mannheim.de:vxacz-xhj42", "oai"));
        HashMap<String,String> ccProps = new HashMap<>();
        ccProps.put("scheme", "spdx");
        ccProps.put("url", "https://creativecommons.org/licenses/by/4.0/legalcode");
        ControlledVocabulary.LanguageIdFactory languageIdFactory = new ControlledVocabulary.LanguageIdFactory();
        hitList.add(new Record(
                new Access(Access.AccessType.Public,Access.AccessType.Public).setEmbargo(new Access.Embargo()).setStatus("metadata-only"),
                DateFormater.getInstance().parse("2023-01-11T12:52:28.945163+00:00"),
                new FilesOptions(false),
                "vxacz-xhj42",
                false,
                true,
                new Metadata(
                        new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other)),
                        new ArrayList<Metadata.Creator>(Collections.singletonList(new Metadata.Creator(new Metadata.PersonOrOrg("IDS Mannheim")))),
                        "Blank", 
                        new Metadata.ExtendedDateTimeFormat0("2023").addStartMonth("01").addStartDay("11")
                ).addRights(Collections.singletonList(new Metadata.License(
                        "cc-by-4.0",
                        new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), "Creative Commons Attribution 4.0 International"),
                        new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), "The Creative Commons Attribution license allows re-distribution and re-use of a licensed work on the condition that the creator is appropriately credited."))
                        .addProps(ccProps)))
                .setPublisher("IDS-Repos2")
                ,
                new Record.Parent("5k1hr-sxr38"),
                3,
                "published",
                DateFormater.getInstance().parse("2023-01-11T12:52:29.031012+00:00"),
                new Record.Versions(1,true))
                .addLinks(hitLinks)
                .addPids(pids)
        );
        Records.Hits hits = new Records.Hits(1).addHits(hitList);
        HashMap<String, String> links = new HashMap<>();
        links.put("self", "https://repos-devel2.ids-mannheim.de:5000/api/records?page=1&size=25&sort=newest");
        Records records = new Records(hits, "newest").addAggregations(aggregations).addLinks(links);
        Records r2 = om.readValue(recordsText, Records.class);
        Records r3 = om.readValue(om.writeValueAsString(records), Records.class);
        Assertions.assertEquals(records,r2);
        Assertions.assertEquals(records,r3);
    }
}
