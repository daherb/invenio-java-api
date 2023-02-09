package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvenioJavaApiApplication {
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JDOMException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        SpringApplication.run(InvenioJavaApiApplication.class, args);
        String host = "repos-devel2.ids-mannheim.de:5000";
        String token = "voCZ7NcC0lwmCmluJlxhW5m1BYJjKQoHBIyxgmabuJCyEIzsFG6yE7JHwxe8";
        API api = new API(host, token);
        APITools tools = new APITools(api);
        // createTestData(API api);
        // Delete all draft records
//                Records records = API.listUserRecords();
//                for (String id : records.hits.hits.stream().filter((r) -> r.status.equals("draft")).map((r) -> r.id).collect(Collectors.toList())) {
//                    LOG.info("Trying to delete " + id);
//                    API.deleteDraftRecord(id);
//                }
//                LOG.info(om.writeValueAsString(API.searchRecords(Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty())));
//                LOG.info(om.writeValueAsString(API.searchRecords(Optional.of("metadata.creators:Blank"),Optional.empty(),Optional.empty(),Optional.empty(),Optional.empty())));
//                LOG.info(API.listDraftFiles("6k6r1-hpf49")));
// createTestData();
// LOG.info(CMDI.readCmdiMetadata(new SAXBuilder().build(new File("/home/herb/projekte/Metadata-i5-to-CMDI_Wikipedia-de/wdd17.cmdi"))).toString());
// CMDI.readCmdiMetadata(new SAXBuilder().build(new File("/home/herb/projekte/Metadata-i5-to-CMDI_Wikipedia-de/wdd17.cmdi")));
        // tools.deleteAllDrafts();
        String id = tools.uploadDraftSip(new File("/home/herb/projekte/wikipedia-mini-test-sips/wdd13").toPath());
        LOG.info(id);
        System.out.println("Press key");System.in.read();
        // tools.deleteDraftSip(id);
    }
    
    /**
     * Puts some test data into invenio
     *
     * @param api The API object to use for the calls
     * @throws java.io.UnsupportedEncodingException
     * @throws java.net.URISyntaxException
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.security.KeyManagementException
     * @throws java.lang.InterruptedException
     */
    public static void createTestData(API api) throws UnsupportedEncodingException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, IOException, InterruptedException {
        ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        DraftRecord draftRecord = new DraftRecord(
                new Access(Access.AccessType.Public, Access.AccessType.Public),
                new FilesOptions(true),
                new Metadata(new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other)),
                        new ArrayList<>(Collections.singletonList(new Metadata.Creator(new Metadata.PersonOrOrg("IDS Mannheim")))),
                        "Draft Record",
                        new Metadata.ExtendedDateTimeFormat0("2023")));
        // String fileName = "doc/LZA_IDS_Depositing_Policy.pdf";
        String fileName = "LZA_IDS_Depositing_Policy.pdf";
        Record created = api.createDraftRecord(draftRecord);
        LOG.info(om.writeValueAsString(created));
        ArrayList<Files.FileEntry> entries = new ArrayList(List.of(new Files.FileEntry(fileName)));
        Files files = api.startDraftFileUpload(created.id, entries);
        LOG.info(om.writeValueAsString(files));
        Files.FileEntry entry = api.uploadDraftFile(created.id, fileName, new File("/home/herb/Downloads/LZA_IDS_Depositing_Policy.pdf"));
        LOG.info(om.writeValueAsString(entry));
        Files.FileEntry completed = api.completeDraftFileUpload(created.id, fileName);
        LOG.info(om.writeValueAsString(completed));
        Files.FileEntry metadata = api.getDraftFileMetadata(created.id, fileName);
        LOG.info(om.writeValueAsString(metadata));
        // Try to download file
        api.getDraftFileContent(created.id, fileName).transferTo(new FileOutputStream("/tmp/policy.pdf"));
        
    }
    
    public static void demo(APITools tools) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
        LOG.info("Starting demo");
        System.out.println("Press key");
        System.in.read();
        LOG.info("Uploading draft SIP");
        String id = tools.uploadDraftSip(new File("/home/herb/projekte/wikipedia-mini-test-sips/wdd13").toPath(), true);
        System.out.println("Press key");
        System.in.read();
        LOG.log(Level.INFO, "Downloading draft SIP {0}", id);
        tools.downloadDraftSip(id, Path.of("/tmp", "drafts"));
        System.out.println("Press key");
        System.in.read();
        LOG.log(Level.INFO, "Delete draft SIP {0}", id);
        tools.deleteDraftSip(id);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Upload several SIPS");
        Instant start = Instant.now();
        tools.uploadDraftSips(new File("/home/herb/projekte/wikipedia-mini-test-sips").toPath(), true);
        Instant end = Instant.now();
        LOG.log(Level.INFO, "Took {0} seconds", end.getEpochSecond() - start.getEpochSecond());
        LOG.info("Done");
    }
    
    public static void versionedUpload(API api, APITools tools) throws IOException, JDOMException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, InterruptedException, FileNotFoundException, JsonProcessingException, UnsupportedEncodingException {
         ObjectMapper om = new ObjectMapper();
        om.findAndRegisterModules();
        om.enable(SerializationFeature.INDENT_OUTPUT);
        LOG.info("Starting upload");
        LOG.info("Upload version 1");
        String id = tools.uploadDraftSip(Path.of("/home/herb/projekte/test-sips/disko/sip1"), false);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Publish version 1");
        tools.publishDraftSip(id);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Upload version 2");
        id = tools.updateSip(id,Path.of("/home/herb/projekte/test-sips/disko/sip2"));
        System.out.println("Press key");
        System.in.read();
        LOG.info("Publish version 2");
        tools.publishDraftSip(id);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Upload version 3");
        id = tools.updateSip(id,Path.of("/home/herb/projekte/test-sips/disko/sip3"));
        System.out.println("Press key");
        System.in.read();
        LOG.info("Publish version 3");
        tools.publishDraftSip(id);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Upload version 4");
        id = tools.updateSip(id,Path.of("/home/herb/projekte/test-sips/disko/sip4"));
        System.out.println("Press key");
        System.in.read();
        LOG.info("Publish version 4");
        tools.publishDraftSip(id);
        System.out.println("Press key");
        System.in.read();
        LOG.info("Upload version 5");
        id = tools.updateSip(id,Path.of("/home/herb/projekte/test-sips/disko/sip5"));
        System.out.println("Press key");
        System.in.read();
        LOG.info("Publish version 5");
        tools.publishDraftSip(id);
        System.out.println("Press key");
        System.in.read();
    }
    private static final Logger LOG = Logger.getLogger(InvenioJavaApiApplication.class.getName());
    
}
