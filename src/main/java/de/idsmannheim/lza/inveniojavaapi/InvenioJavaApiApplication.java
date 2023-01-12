package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InvenioJavaApiApplication {

	public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException {
                ObjectMapper om = new ObjectMapper();
                om.findAndRegisterModules();
		SpringApplication.run(InvenioJavaApiApplication.class, args);
                DraftRecord draftRecord = new DraftRecord(
                        new Access(Access.AccessType.Public, Access.AccessType.Public),
                        new FilesOptions(true), 
                        new Metadata(new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other)),
                                new ArrayList<>(Collections.singletonList(new Metadata.Creator(new Metadata.PersonOrOrg("IDS Mannheim")))),
                                "Draft Record",
                                new Metadata.ExtendedDateTimeFormat0("2023")));
                // LOG.info(API.createDraftRecord(draftRecord).body());
                LOG.info(om.readValue(API.listRecords().body(),Records.class).toString());
	}
    private static final Logger LOG = Logger.getLogger(InvenioJavaApiApplication.class.getName());

}
