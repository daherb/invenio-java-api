package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvenioJavaApiApplication {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
                ObjectMapper om = new ObjectMapper();
                om.findAndRegisterModules();
                om.enable(SerializationFeature.INDENT_OUTPUT);
		SpringApplication.run(InvenioJavaApiApplication.class, args);
                createTestData();
                // Delete all draft records
//                Records records = API.listUserRecords();
//                for (String id : records.hits.hits.stream().filter((r) -> r.status.equals("draft")).map((r) -> r.id).collect(Collectors.toList())) {
//                    LOG.info("Trying to delete " + id);
//                    API.deleteDraftRecord(id);
//                }
//                LOG.info(API.searchRecords().toString());
//                LOG.info(API.listDraftFiles("6k6r1-hpf49")));
	}
        
        /** 
         * Puts some test data into invenio
         * 
         */
        public static void createTestData() {
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
            String fileName = "doc#LZA_IDS_Depositing_Policy.pdf";
            Record created = API.createDraftRecord(draftRecord);
            LOG.info(om.writeValueAsString(created));
            ArrayList<Files.FileEntry> entries = new ArrayList(List.of(new Files.FileEntry(fileName)));
            Files files = API.startDraftFileUpload(created.id, entries);
            LOG.info(om.writeValueAsString(files));
            Files.FileEntry entry = API.uploadDraftFile(created.id, fileName, new File("/home/herb/Downloads/LZA_IDS_Depositing_Policy.pdf"));
            LOG.info(om.writeValueAsString(entry));
            Files.FileEntry completed = API.completeDraftFileUpload(created.id, fileName);
            LOG.info(om.writeValueAsString(completed));
        }
    private static final Logger LOG = Logger.getLogger(InvenioJavaApiApplication.class.getName());

}
