package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.idsmannheim.lza.inveniojavaapi.Metadata.ResourceType;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
@SpringBootTest
class MetadataTests {

    private static final Logger LOG = Logger.getLogger(MetadataTests.class.getName());
    
    @Test
    void resourceTypeTest() throws JsonProcessingException {
        // String resourceTypeText =
        //        "{\"resource_type\": {\"id\": \"image-photo\"}}";
        String resourceTypeText =
               "{\"id\": \"image-photo\"}";
        ResourceType resourceType = new ResourceType(
                new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto));
        ObjectMapper om = new ObjectMapper();
        
        //ControlledVocabulary.ResourceType rt = om.readValue("\"image-photo\"", ControlledVocabulary.ResourceType.class);
        ControlledVocabulary.ResourceType rt = om.readValue("\"image-photo\"", ControlledVocabulary.ResourceType.class);
        //ResourceType resourceType2 = om.readValue(resourceTypeText, ResourceType.class);
        //Assertions.assertEquals(resourceType, resourceType2);
        LOG.info(rt.toString());
        LOG.info(om.writeValueAsString(resourceType));
    }
    
    @Test
    void metadataTest() throws JsonProcessingException {
        Metadata blankMetadata = new Metadata(
                new ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other)), //resourceType
                new ArrayList<Metadata.Creator>(), //creators
                "", // title
                new Metadata.ExtendedDateTimeFormat0("2022") // publicationDate
            );
        
        /**
         *  "metadata": {
         *    "creators": [
         *      {
         *        "person_or_org": {
         *          "family_name": "Brown",
         *          "given_name": "Troy",
         *          "type": "personal"
         *        }
         *      },
         *      {
         *        "person_or_org": {
         *          "family_name": "Collins",
         *          "given_name": "Thomas",
         *          "identifiers": [
         *            {"scheme": "orcid", "identifier": "0000-0002-1825-0097"}
         *          ],
         *           "name": "Collins, Thomas",
         *          "type": "personal"
         *        },
         *        "affiliations": [
         *          {
         *            "id": "01ggx4157",
         *             "name": "Entity One"
         *          }
         *         ]
         *       }
         *    ],
         *    "publication_date": "2020-06-01",
         * v    "resource_type": { "id": "image-photo" },
         * v    "title": "A Romans story",
         *  }
         */
        ArrayList<Metadata.Creator> creators = new ArrayList<>();
        creators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Troy","Brown", new ArrayList<>()),
                Optional.empty(),
                new ArrayList<>()));
        creators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Thomas", "Collins","Collins, Thomas",
                    List.of(new Metadata.PersonOrOrg.Identifier(new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID),"0000-0002-1825-0097"))
                ),
                Optional.empty(),
                new ArrayList<>(List.of(new Metadata.Affiliation(Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)), Optional.of("Entity One"))))
                ));
        Metadata exampleMetadata = new Metadata(
                new ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto)),
                creators,
                "A Romans Story",
                new Metadata.ExtendedDateTimeFormat0("2020").addStartMonth("06").addStartDay("01")
                        );
        ObjectMapper om = new ObjectMapper();
        om.registerModule(new Jdk8Module());
        LOG.info(om.writeValueAsString(blankMetadata));
        LOG.info(om.writeValueAsString(exampleMetadata));
        };

}
