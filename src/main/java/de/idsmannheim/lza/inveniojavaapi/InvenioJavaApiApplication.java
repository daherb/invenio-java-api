package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.factories.SchemaFactoryWrapper;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.zientarski.SchemaMapper;

@SpringBootApplication
public class InvenioJavaApiApplication {
    
    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException, NoSuchAlgorithmException, KeyManagementException, JDOMException {
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
        Files.FileEntry entry = api.uploadDraftFile(created.id, fileName, new File("/home/herb/Downloads/LZA_IDS_Depositing_Policy.pdf").toURI());
        LOG.info(om.writeValueAsString(entry));
        Files.FileEntry completed = api.completeDraftFileUpload(created.id, fileName);
        LOG.info(om.writeValueAsString(completed));
        Files.FileEntry metadata = api.getDraftFileMetadata(created.id, fileName);
        LOG.info(om.writeValueAsString(metadata));
        // Try to download file
        api.getDraftFileContent(created.id, fileName).transferTo(new FileOutputStream("/tmp/policy.pdf"));
        
    }
    
    private String generateSchema() throws JsonMappingException, JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
        mapper.acceptJsonFormatVisitor(Metadata.class, visitor);
        JsonSchema schema = visitor.finalSchema();
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(schema);
    }
    
    
    private String generateSchema4() {
        SchemaMapper schemaMapper = new SchemaMapper();
        JSONObject jsonObject = schemaMapper.toJsonSchema4(Metadata.class, true);
        return jsonObject.toString(4);
    }
    
    private static final Logger LOG = Logger.getLogger(InvenioJavaApiApplication.class.getName());
    
}
