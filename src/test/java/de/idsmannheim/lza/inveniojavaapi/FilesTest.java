package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
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
 * Tests for Files class
 */
@SpringBootTest
public class FilesTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void filesTest() throws JsonProcessingException, ParseException, URISyntaxException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#entries-0-n
        String tombstoneStringText = "{\n" +
                "  \"enabled\": \"true\",\n" +
                "  \"entries\": {\n" +
                "    \"paper.pdf\": {\n" +
                "      \"bucket_id\": \"<bucket-id>\",\n" +
                "      \"checksum\": \"md5:abcdef...\",\n" +
                "      \"created\": \"2022-10-12T11:08:56.953781+00:00\",\n" +
                "      \"file_id\": \"<file-id>\",\n" +
                "      \"key\": \"paper.pdf\",\n" +
                "      \"links\": {\n" +
                "        \"self\": \"{scheme+hostname}/api/records/12345-abcde/files/your_file.png\",\n" +
                "        \"content\": \"{scheme+hostname}/api/records/12345-abcde/files/your_file.png/content\",\n" +
                "        \"iiif_canvas\": \"{scheme+hostname}/api/iiif/record:8a4dq-z5237/canvas/your_file.png\",\n" +
                "        \"iiif_base\": \"{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png\",\n" +
                "        \"iiif_info\": \"{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png/info.json\",\n" +
                "        \"iiif_api\": \"{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png/full/full/0/default.png\"\n" +
                "      },\n" +
                "      \"metadata\": {\n" +
                "        \"width\": 2302,\n" +
                "        \"height\": 948\n" +
                "      },\n" +
                "      \"mimetype\": \"application/pdf\",\n" +
                "      \"size\": 12345,\n" +
                "      \"status\": \"completed\",\n" +
                "      \"storage_class\": \"A\",\n" +
                "      \"updated\": \"2022-10-12T11:08:56.953781+00:00\",\n" +
                "      \"version_id\": \"<object-version-id>\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"default_preview\": \"paper.pdf\"\n" +
                "}"; 
        Files files = new Files(true);
        Date created = om.readValue("\"2022-10-12T11:08:56.953781+00:00\"", Date.class); // 2022-10-12T11:08:56.953781+00:00
        Date updated = om.readValue("\"2022-10-12T11:08:56.953781+00:00\"", Date.class);
        HashMap<String,Object> metadata = new HashMap<>();
        metadata.put("width", 2302);
        metadata.put("height", 948);
        HashMap<String,String> link = new HashMap<>();
        link.put("self", "{scheme+hostname}/api/records/12345-abcde/files/your_file.png");
        link.put("content", "{scheme+hostname}/api/records/12345-abcde/files/your_file.png/content");
        link.put("iiif_canvas", "{scheme+hostname}/api/iiif/record:8a4dq-z5237/canvas/your_file.png");
        link.put("iiif_base", "{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png");
        link.put("iiif_info", "{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png/info.json");
        link.put("iiif_api", "{scheme+hostname}/api/iiif/record:8a4dq-z5237:your_file.png/full/full/0/default.png");
        files.addEntriesMap(new HashMap<>(Collections.singletonMap("paper.pdf", 
                new Files.FileEntry("<bucket-id>", "md5:abcdef...", created, 
                        "<file-id>", "paper.pdf", "application/pdf", 12345, 
                        "completed", "A", updated, "<object-version-id>")
                        .addLinks(link)
                        .addMetadata(metadata))));
        files.setDefaultPreview("paper.pdf");
        Files f2 = om.readValue(tombstoneStringText, Files.class);
        Files f3 = om.readValue(om.writeValueAsString(files), Files.class);
        Assertions.assertEquals(files,f2);
        Assertions.assertEquals(files,f3);
    }
}
