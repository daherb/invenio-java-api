package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.idsmannheim.lza.inveniojavaapi.Metadata.ResourceType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.*;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Affiliation;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Creator;
import de.idsmannheim.lza.inveniojavaapi.Metadata.Language;
import de.idsmannheim.lza.inveniojavaapi.Metadata.PersonOrOrg.Identifier;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

/**
 * 
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Tests for Metadata class
 */
@SpringBootTest
class MetadataTest {

    static ObjectMapper om = new ObjectMapper();
    
    // Example data
    static ResourceType resourceType = new ResourceType(
                new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto));
    static ArrayList<Creator> creators = new ArrayList<>();
    static String title = "InvenioRDM";
    static Metadata.ExtendedDateTimeFormat0 publicationDate;
    static ArrayList<Metadata.AdditionalTitle> additionalTitles = new ArrayList<>();
    static ArrayList<Metadata.AdditionalDescription> additionalDescriptions = 
                new ArrayList<>();
    static ArrayList<Metadata.License> rights = new ArrayList<>();
    static ArrayList<Metadata.Contributor> contributors = new ArrayList<>();
    static ArrayList<Metadata.Subject> subjects = new ArrayList<>();
    static ArrayList<Language> languages = new ArrayList<>();
    static ArrayList<Metadata.Date> dates = new ArrayList<>();
    static ArrayList<Metadata.AlternateIdentifier> alternateIdentifiers = new ArrayList<>();
    static ArrayList<Metadata.RelatedIdentifier> relatedIdentifiers = new ArrayList<>();
    static Metadata.Location locations;
    static ArrayList<Metadata.FundingReference> funding = new ArrayList<>();
    static ArrayList<Metadata.Reference> references = new ArrayList<>();
    static ControlledVocabulary.LanguageIdFactory languageIdFactory;
        
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
        // Initialize example data
        ArrayList<Identifier> identifiers = new ArrayList<>();
        languageIdFactory = new ControlledVocabulary.LanguageIdFactory();
        identifiers.add(new Identifier(
                new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID),
                "0000-0001-8135-3489"));
        ArrayList<Affiliation> affiliations = new ArrayList<>();
        affiliations.add(new Affiliation(Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)),
                Optional.of("CERN")));
        creators.add(new Creator(new Metadata.PersonOrOrg("Lars Holm","Nielsen").addIdentifiers(identifiers)).addAffiliations(affiliations));
        publicationDate = 
                new Metadata.ExtendedDateTimeFormat0("2018")
                .addEndYear("2020").addEndMonth("09");
        additionalTitles.add(new Metadata.AdditionalTitle("A research data management platform", 
                new Metadata.AdditionalTitle.TitleType(new ControlledVocabulary.TitleTypeId(ControlledVocabulary.TitleTypeId.ETitleType.AlternativeTitle), 
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), "Alternative Title")))
                .setLang(new Metadata.Language(languageIdFactory.usingId3("eng"))));
        additionalDescriptions.add(new Metadata.AdditionalDescription(
                "The description of a research data management platform.", 
                new Metadata.AdditionalDescription.DescriptionType(
                        new ControlledVocabulary.DescriptionTypeId(ControlledVocabulary.DescriptionTypeId.EDescriptionType.Methods),
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), 
                                "Methods")))
                .setLang(new Language(languageIdFactory.usingId3("eng"))));
        rights.add(new Metadata.License(Optional.of("cc-by-4.0"), 
                new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), 
                        "Creative Commons Attribution 4.0 International"),
                new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), 
                        "The Creative Commons Attribution license allows re-distribution and re-use of a licensed work on the condition that the creator is appropriately credited."))
                .setLink(new URL("https://creativecommons.org/licenses/by/4.0/")));
        Metadata.PersonOrOrg personOrOrg = new Metadata.PersonOrOrg(
                "Lars Holm", "Nielsen")
                .addIdentifiers(new ArrayList<>(List.of(new Metadata.PersonOrOrg.Identifier(
                        new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID), 
                        "0000-0001-8135-3489"))));
        contributors.add(new Metadata.Contributor(
                personOrOrg, 
                new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.Editor))
                .addAffiliations(List.of(new Affiliation(
                        Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)), 
                        Optional.of("CERN")))));
        subjects.add(new Metadata.Subject(new URL("https://id.nlm.nih.gov/mesh/D000001")));
        languages.add(new Language(languageIdFactory.usingId3("dan")));
        languages.add(new Language(languageIdFactory.usingId3("eng")));
        dates.add(new Metadata.Date(
                new Metadata.ExtendedDateTimeFormat0("1939").addEndYear("1945"),
                new Metadata.Date.DateType(
                new ControlledVocabulary.DateTypeId(ControlledVocabulary.DateTypeId.EDateType.Other),
                        new Metadata.LocalizedStrings().add(
                                new Language(languageIdFactory.usingId2("en")),
                                "Other")
                )).setDescription("A date"));
        alternateIdentifiers.add(new Metadata.AlternateIdentifier(
                "1924MNRAS..84..308E", 
                new ControlledVocabulary.RecordIdentifierScheme(
                        ControlledVocabulary.RecordIdentifierScheme.ERecordItentifierScheme.Bibcode)
        ));
        String identifier = "10.1234/foo.bar";
        ControlledVocabulary.RelatedRecordIdentifierScheme scheme = 
                new ControlledVocabulary.RelatedRecordIdentifierScheme(
                        ControlledVocabulary.RelatedRecordIdentifierScheme.ERelatedRecordIdentifierScheme.DOI);
        Metadata.RelatedIdentifier.RelationType relationType = 
                new Metadata.RelatedIdentifier.RelationType(
                        new ControlledVocabulary.RelationTypeId(ControlledVocabulary.RelationTypeId.ERelationTypeId.Cites), 
                        new Metadata.LocalizedStrings()
                                .add(new Language(languageIdFactory.usingId2("en")), "Cites"));
        Metadata.RelatedIdentifier.RelatedResourceType newResourceType = 
                new Metadata.RelatedIdentifier.RelatedResourceType(new ControlledVocabulary.RelatedResourceType(
                        ControlledVocabulary.RelatedResourceType.EResourceType.Dataset),
                new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")),
                        "Dataset"));
        relatedIdentifiers.add(new Metadata.RelatedIdentifier(
                identifier, 
                scheme, 
                relationType).setResourceType(newResourceType));
        ArrayList<Metadata.Location.LocationFeature> locationFeatures = 
                new ArrayList<>();
        locationFeatures.add(new Metadata.Location.LocationFeature(
                Optional.of(new Metadata.Location.LocationGeometry("Point",
                        new ArrayList<>(List.of(6.05,46.23333)))),
                List.of(new Metadata.Location.LocationIdentifier("2661235", "geonames")), 
                Optional.of("CERN"),
                Optional.of("Invenio birth place.")));
        locations = new Metadata.Location(locationFeatures);
        funding.add(new Metadata.FundingReference(
                new Metadata.FundingReference.Funder(
                        Optional.of(ControlledVocabulary.FunderIdFactory.usingId("00k4n6c32")),
                        Optional.empty())).setAward(
                new Metadata.FundingReference.Award(
                        Optional.of(new ControlledVocabulary.AwardId("00k4n6c32::246686")),
                        new Metadata.LocalizedStrings(), Optional.empty(), new ArrayList<>())
        ));
        funding.add(new Metadata.FundingReference(
                new Metadata.FundingReference.Funder(
                        Optional.of(ControlledVocabulary.FunderIdFactory.usingId("00k4n6c32")),
                        Optional.empty())).setAward(
                new Metadata.FundingReference.Award(Optional.empty(),
                        new Metadata.LocalizedStrings().add(
                                new Language(languageIdFactory.usingId2("en")), 
                                "Research on Experimental Physics"), 
                        Optional.of("EP-123456"),
                        new ArrayList<>(List.of(
                                new Metadata.AlternateIdentifier("https://experimental-physics.eu", 
                                new ControlledVocabulary.RecordIdentifierScheme(ControlledVocabulary.RecordIdentifierScheme.ERecordItentifierScheme.URL)))))));
        references.add(new Metadata.Reference("Nielsen et al,..")
                .setScheme(new ControlledVocabulary.ReferenceScheme(ControlledVocabulary.ReferenceScheme.EReferenceScheme.Other))
                .setIdentifier("10.1234/foo.bar"));
    }
    
    @Test
    void resourceTypeTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#resource-type-1
        String resourceTypeText =
               "{\"id\": \"image-photo\"}";
        ResourceType rt2 = om.readValue(resourceTypeText, ResourceType.class);
        ResourceType rt3 = om.readValue(om.writeValueAsString(resourceType), ResourceType.class);
        Assertions.assertEquals(resourceType,rt2);
        Assertions.assertEquals(resourceType,rt3);
    }
    
    @Test
    void creatorsTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#creators-1-n
        String creatorsText = 
                "[{\n" +
                "    \"person_or_org\": {\n" +
                "      \"name\": \"Nielsen, Lars Holm\",\n" +
                "      \"type\": \"personal\",\n" +
                "      \"given_name\": \"Lars Holm\",\n" +
                "      \"family_name\": \"Nielsen\",\n" +
                "      \"identifiers\": [{\n" +
                "        \"scheme\": \"orcid\",\n" +
                "        \"identifier\": \"0000-0001-8135-3489\"\n" +
                "      }]\n" +
                "    },\n" +
                "    \"affiliations\": [{\n" +
                "      \"id\": \"01ggx4157\",\n" +
                "      \"name\": \"CERN\"\n" +
                "    }]\n" +
                "  }],\n";
        ArrayList<Creator> cs2 = om.readerForListOf(Creator.class).readValue(creatorsText);
        ArrayList<Creator> cs3 = om.readerForListOf(Creator.class).readValue(om.writeValueAsString(creators));
        //Assertions.assertEquals(creators.get(0).affiliations.get(0).id, cs2.get(0).affiliations.get(0).id);
        Assertions.assertEquals(creators, cs2);
        Assertions.assertEquals(creators, cs3);
        
    }
    
    @Test
    void titleTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#title-1
        String titleText = "\"InvenioRDM\"";
        String t2 = om.readValue(titleText, String.class);
        String t3 = om.readValue(om.writeValueAsString(title),String.class);
        Assertions.assertEquals(title, t2);
        Assertions.assertEquals(title, t3);
    }
    
    @Test
    void publicationDateTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#publication-date-1
        String publicationDateText = "\"2018/2020-09\"";
        Metadata.ExtendedDateTimeFormat0 p2 = om.readValue(publicationDateText, Metadata.ExtendedDateTimeFormat0.class);
        Metadata.ExtendedDateTimeFormat0 p3 = 
                om.readValue(om.writeValueAsString(publicationDate), Metadata.ExtendedDateTimeFormat0.class);
        Assertions.assertEquals(publicationDate, p2);
        Assertions.assertEquals(publicationDate, p3);
    }
    
    @Test
    void additionalTitlesTest() throws JsonProcessingException, IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#additional-titles-0-n
        String additionalTitlesText = "[{\n" +
                "    \"title\": \"A research data management platform\",\n" +
                "    \"type\": {\n" +
                "      \"id\": \"alternative-title\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Alternative Title\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"lang\": {\"id\": \"eng\"}\n" +
                "  }]";
        ArrayList<Metadata.AdditionalTitle> at2 = om.readerForListOf(Metadata.AdditionalTitle.class).readValue(additionalTitlesText);
        ArrayList<Metadata.AdditionalTitle> at3 = om.readerForListOf(Metadata.AdditionalTitle.class).readValue(om.writeValueAsString(additionalTitles));
        Assertions.assertEquals(additionalTitles, at2);
        Assertions.assertEquals(additionalTitles, at3);
    }
    
    /*
    Description is a simple string, so no test needed
    */
    @Test
    void additionalDescriptionsTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#additional-descriptions-0-n
        String additionalDescriptionsText = "[{\n" +
                "    \"description\": \"The description of a research data management platform.\",\n" +
                "    \"type\": {\n" +
                "      \"id\": \"methods\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Methods\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"lang\": {\"id\": \"eng\"}\n" +
                "  }]";
        ArrayList<Metadata.AdditionalDescription> ad2 = om.readerForListOf(Metadata.AdditionalDescription.class).readValue(additionalDescriptionsText);
        ArrayList<Metadata.AdditionalDescription> ad3 = om.readerForListOf(Metadata.AdditionalDescription.class).readValue(om.writeValueAsString(additionalDescriptions));
        Assertions.assertEquals(additionalDescriptions, ad2);
        Assertions.assertEquals(additionalDescriptions, ad3);
    }
    
    @Test
    void rightsTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#rights-licenses-0-n
        String rightsText = "[{\n" +
                "    \"id\": \"cc-by-4.0\",\n" +
                "    \"title\": {\"en\": \"Creative Commons Attribution 4.0 International\"},\n" +
                "    \"description\": {\"en\": \"The Creative Commons Attribution license allows re-distribution and re-use of a licensed work on the condition that the creator is appropriately credited.\"},\n" +
                "    \"link\": \"https://creativecommons.org/licenses/by/4.0/\"\n" +
                "  }]";
        ArrayList<Metadata.License> r2 = om.readerForListOf(Metadata.License.class).readValue(rightsText);
        ArrayList<Metadata.License> r3 = om.readerForListOf(Metadata.License.class).readValue(om.writeValueAsString(rights));
        Assertions.assertEquals(rights, r2);
        Assertions.assertEquals(rights, r3);
    }
    
    @Test
    void contributorsTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#contributors-0-n
        // And updated to match new role format
        String contributorsText = "[{\n" +
                "    \"person_or_org\": {\n" +
                "      \"name\": \"Nielsen, Lars Holm\",\n" +
                "      \"type\": \"personal\",\n" +
                "      \"given_name\": \"Lars Holm\",\n" +
                "      \"family_name\": \"Nielsen\",\n" +
                "      \"identifiers\": [{\n" +
                "        \"scheme\": \"orcid\",\n" +
                "        \"identifier\": \"0000-0001-8135-3489\"\n" +
                "      }]\n" +
                "    },\n" +
                "    \"role\": {\"id\": \"editor\"},\n" +
                "    \"affiliations\": [{\n" +
                "      \"id\": \"01ggx4157\",\n" +
                "      \"name\": \"CERN\"\n" +
                "    }]\n" +
                "  }]";
        ArrayList<Metadata.Contributor> c2 = om.readerForListOf(Metadata.Contributor.class).readValue(contributorsText);
        ArrayList<Metadata.Contributor> c3 = om.readerForListOf(Metadata.Contributor.class).readValue(om.writeValueAsString(contributors));
        Assertions.assertEquals(contributors, c2);
        Assertions.assertEquals(contributors, c3);
    }
    
    @Test
    void subjectsTest() throws MalformedURLException, JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#subjects-0-n
        String subjectsText = "[{\n" +
                "    \"id\": \"https://id.nlm.nih.gov/mesh/D000001\"\n" +
                "  }]";
        ArrayList<Metadata.Subject> s2 = om.readerForListOf(Metadata.Subject.class).readValue(subjectsText);
        ArrayList<Metadata.Subject> s3 = om.readerForListOf(Metadata.Subject.class).readValue(om.writeValueAsString(subjects));
        Assertions.assertEquals(subjects, s2);
        Assertions.assertEquals(subjects, s3);
    }
    
    @Test
    void languagesTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#languages-0-n
        String languagesText = "[{\"id\": \"dan\"}, {\"id\": \"eng\"}]";
        ArrayList<Metadata.Language> l2 = om.readerForListOf(Metadata.Language.class).readValue(languagesText);
        ArrayList<Metadata.Language> l3 = om.readerForListOf(Metadata.Language.class).readValue(om.writeValueAsString(languages));
        Assertions.assertEquals(languages, l2);
        Assertions.assertEquals(languages, l3);
    }
    
    @Test
    void datesTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#dates-0-n
        String datesText = "[{\n" +
                "    \"date\": \"1939/1945\",\n" +
                "    \"type\": {\n" +
                "      \"id\": \"other\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Other\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"description\": \"A date\"\n" +
                "  }]";
        
        ArrayList<Metadata.Date> d2 = om.readerForListOf(Metadata.Date.class).readValue(datesText);
        ArrayList<Metadata.Date> d3 = om.readerForListOf(Metadata.Date.class).readValue(om.writeValueAsString(dates));
        Assertions.assertEquals(dates, d2);
        Assertions.assertEquals(dates, d3);
    }
    
    /*
    Version and Publisher are simple strings, so no tests needed
    */
    
    @Test
    void alternateIdentifiers() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#alternate-identifiers-0-n
        String alternateIdentifiersText = "[{\n" +
                "    \"identifier\": \"1924MNRAS..84..308E\",\n" +
                "    \"scheme\": \"bibcode\"\n" +
                "  }]";
        ArrayList<Metadata.AlternateIdentifier> ai2 = om.readerForListOf(Metadata.AlternateIdentifier.class).readValue(alternateIdentifiersText);
        ArrayList<Metadata.AlternateIdentifier> ai3 = om.readerForListOf(Metadata.AlternateIdentifier.class).readValue(om.writeValueAsString(alternateIdentifiers));
        Assertions.assertEquals(alternateIdentifiers, ai2);
        Assertions.assertEquals(alternateIdentifiers, ai3);
    }
    
    @Test
    void relatedIdentifiersTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#related-identifiersworks-0-n
        String relatedIdentifiersText = "[{\n" +
                "    \"identifier\": \"10.1234/foo.bar\",\n" +
                "    \"scheme\": \"doi\",\n" +
                "    \"relation_type\": {\n" +
                "      \"id\": \"cites\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Cites\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"resource_type\": {\n" +
                "      \"id\": \"dataset\",\n" +
                "      \"title\": {\n" +
                "        \"en\": \"Dataset\"\n" +
                "      }\n" +
                "    }\n" +
                "  }]";
        ArrayList<Metadata.RelatedIdentifier> ri2 = 
                om.readerForListOf(Metadata.RelatedIdentifier.class)
                        .readValue(relatedIdentifiersText);
        ArrayList<Metadata.RelatedIdentifier> ri3 = 
                om.readerForListOf(Metadata.RelatedIdentifier.class)
                        .readValue(om.writeValueAsString(relatedIdentifiers));
        Assertions.assertEquals(relatedIdentifiers, ri2);
        Assertions.assertEquals(relatedIdentifiers, ri3);
    }
    
    /*
    Sizes and formats are simple lists, so no tests needed
    */
    
    @Test
    void locationsTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#locations-0-n
        String locationsText = "{\n" +
                "    \"features\": [{\n" +
                "      \"geometry\": {\n" +
                "        \"type\": \"Point\",\n" +
                "        \"coordinates\": [6.05, 46.23333]\n" +
                "      },\n" +
                "      \"identifiers\": [{\n" +
                "        \"scheme\": \"geonames\",\n" +
                "        \"identifier\": \"2661235\"\n" +
                "      }],\n" +
                "      \"place\": \"CERN\",\n" +
                "      \"description\": \"Invenio birth place.\"\n" +
                "    }]\n" +
                "  }";
        Metadata.Location l2 = om.readValue(locationsText,Metadata.Location.class);
        Metadata.Location l3 = 
                om.readValue(om.writeValueAsString(locations),Metadata.Location.class);
        Assertions.assertEquals(locations, l2);
        Assertions.assertEquals(locations, l3);
    }
    
    @Test
    void fundingTest() throws IOException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#funding-references-0-n
        String fundingText = "[{\n" +
                "      \"funder\": {\n" +
                "        \"id\": \"00k4n6c32\"\n" +
                "      },\n" +
                "      \"award\": {\n" +
                "        \"id\": \"00k4n6c32::246686\"\n" +
                "      }\n" +
                "    }, {\n" +
                "      \"funder\": {\n" +
                "        \"id\": \"00k4n6c32\"\n" +
                "      },\n" +
                "      \"award\": {\n" +
                "        \"title\": {\n" +
                "          \"en\": \"Research on Experimental Physics\"\n" +
                "        },\n" +
                "        \"number\": \"EP-123456\",\n" +
                "        \"identifiers\": [{\n" +
                "          \"scheme\": \"url\",\n" +
                "          \"identifier\": \"https://experimental-physics.eu\"\n" +
                "        }]\n" +
                "      }\n" +
                "    }\n" +
                "  ]";
        ArrayList<Metadata.FundingReference> f2 = om.readerForListOf(Metadata.FundingReference.class)
                .readValue(fundingText);
        ArrayList<Metadata.FundingReference> f3 = om.readerForListOf(Metadata.FundingReference.class)
                .readValue(om.writeValueAsString(funding));
        Assertions.assertEquals(funding, f2);
        Assertions.assertEquals(funding, f3);
    }
    
    @Test
    void referencesTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#references-0-n
        String referencesText = "[{\n" +
                "      \"reference\": \"Nielsen et al,..\",\n" +
                "      \"identifier\": \"10.1234/foo.bar\",\n" +
                "      \"scheme\": \"other\"\n" +
                "  }]";
        
        ArrayList<Metadata.Reference> localReferences = new ArrayList<>();
        localReferences.add(new Metadata.Reference("Nielsen et al,..") 
                .setScheme(new ControlledVocabulary.ReferenceScheme(ControlledVocabulary.ReferenceScheme.EReferenceScheme.Other))
                .setIdentifier("10.1234/foo.bar"));
         ArrayList<Metadata.Reference> r2 = om.readerForListOf(Metadata.Reference.class)
                .readValue(referencesText);
        ArrayList<Metadata.Reference> r3 = om.readerForListOf(Metadata.Reference.class)
                .readValue(om.writeValueAsString(localReferences));
        Assertions.assertEquals(localReferences, r2);
        Assertions.assertEquals(localReferences, r3);
    }
    
    @Test
    void draftRequestMetadataTest() throws JsonProcessingException, IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        ArrayList<Metadata.Creator> newCreators = new ArrayList<>();
        newCreators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Troy","Brown")));
        newCreators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Thomas", "Collins","Collins, Thomas").addIdentifiers(
                    new ArrayList<>(List.of(new Metadata.PersonOrOrg.Identifier(new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID),"0000-0002-1825-0097")))
                )).addAffiliations(
                new ArrayList<>(List.of(new Metadata.Affiliation(Optional.of(new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)), Optional.of("Entity One"))))
                ));
        Metadata exampleMetadata = new Metadata(
                new ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto)),
                newCreators,
                "A Romans story",
                new Metadata.ExtendedDateTimeFormat0("2020").addStartMonth("06").addStartDay("01")
                        );
        Metadata draftMetadata = om.readValue(this.getClass().getClassLoader().getResourceAsStream("draftRequestMetadata.json"), Metadata.class);
        for (Field f : Metadata.class.getDeclaredFields()) {
            f.setAccessible(true);
            Assertions.assertEquals(f.get(draftMetadata),f.get(exampleMetadata),"Field " + f.getName() + ": ");
        }
    }

    @Test
    void draftReplyMetadataTest() throws JsonProcessingException, IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        ArrayList<Metadata.Creator> newCreators = new ArrayList<>();
        newCreators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Troy","Brown")));
        newCreators.add(new Metadata.Creator(
                new Metadata.PersonOrOrg("Thomas", "Collins","Collins, Thomas")
                .addIdentifiers(new ArrayList<>(
                    List.of(new Metadata.PersonOrOrg.Identifier(
                            new ControlledVocabulary.PersonOrOrgIdentifierScheme(ControlledVocabulary.PersonOrOrgIdentifierScheme.EScheme.ORCID),
                            "0000-0002-1825-0097")))
                )).addAffiliations(
                new ArrayList<>(List.of(new Metadata.Affiliation(Optional.of(
                        new ControlledVocabulary.OrganizationalOrInstitutionalId(ControlledVocabulary.OrganizationalOrInstitutionalId.EOrganization.CERN)),
                        Optional.of("European Organization for Nuclear Research"))))
                ));
        Metadata exampleMetadata = new Metadata(
                new ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.ImagePhoto)),
                newCreators,
                "A Romans story",
                new Metadata.ExtendedDateTimeFormat0("2020").addStartMonth("06").addStartDay("01")
                        );
        Metadata draftMetadata = om.readValue(this.getClass().getClassLoader().getResourceAsStream("draftReplyMetadata.json"), Metadata.class);
        for (Field f : Metadata.class.getDeclaredFields()) {
            f.setAccessible(true);
            Assertions.assertEquals(f.get(draftMetadata),f.get(exampleMetadata),"Field " + f.getName() + ": ");
        }
    }
    
    @Test
    void exampleMetadataTest() throws JsonProcessingException, IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Metadata metadata = new Metadata(
                resourceType,
                creators,
                title,
                publicationDate
                        );
        metadata.addAdditionalTitles(additionalTitles);
        metadata.addAdditionalDescriptions(additionalDescriptions);
        metadata.addRights(rights);
        metadata.addContributors(contributors);
        metadata.addSubjects(subjects);
        metadata.addLanguages(languages);
        metadata.addDates(dates);
        metadata.addAlternativeIdentifiers(alternateIdentifiers);
        metadata.addRelatedIdentifiers(relatedIdentifiers);
        metadata.setLocations(locations);
        metadata.addFundingReferences(funding);
        metadata.addReferences(references);
        metadata.setDescription("<strong>Test</strong>");
        metadata.setVersion("v1.0.0");
        metadata.setPublisher("InvenioRDM");
        metadata.addSizes(List.of("11 pages"));
        metadata.addFormats(List.of("application/pdf"));
        Metadata exampleMetadata = om.readValue(this.getClass().getClassLoader().getResourceAsStream("exampleMetadata.json"), Metadata.class);
        for (Field f : Metadata.class.getDeclaredFields()) {
            f.setAccessible(true);
            Assertions.assertEquals(f.get(metadata),f.get(exampleMetadata),"Field " + f.getName() + ": ");
        }
    }
    
    @Test
    void derekoMetadataTest() throws IOException, IllegalArgumentException, IllegalAccessException {
        ResourceType derekoResourceTypeesourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.PublicationAnnotationCollection));
        ArrayList<Metadata.Creator> derekoCreators = new ArrayList<>();
        derekoCreators.add(new Creator(new Metadata.PersonOrOrg("Leibniz-Institut für Deutsche Sprache (IDS)")));
            // .setRole(new Creator.Role(new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.DataCollector))));
        String derekoTitle = "Deutsches Referenzkorpus";
        Metadata.ExtendedDateTimeFormat0 derekoPublicationDate = new Metadata.ExtendedDateTimeFormat0("2022");
        Metadata metadata = new Metadata(derekoResourceTypeesourceType, derekoCreators, derekoTitle, derekoPublicationDate);
        ArrayList<Metadata.AdditionalTitle> derekoAdditionalTitles = new ArrayList<>();
        derekoAdditionalTitles.add(new Metadata.AdditionalTitle("DeReKo", 
                new Metadata.AdditionalTitle.TitleType(new ControlledVocabulary.TitleTypeId(ControlledVocabulary.TitleTypeId.ETitleType.AlternativeTitle),
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("de")), "Alternativer Titel")
                .add(new Language(languageIdFactory.usingId2("en")),"Alternative title"))).setLang(new Language(languageIdFactory.usingId3("deu"))));
        metadata.addAdditionalTitles(derekoAdditionalTitles);
        metadata.setDescription("Die Korpora geschriebener Gegenwartssprache des IDS - bilden mit 53 Milliarden Wörtern (Stand 08.03.2022) die weltweit größte linguistisch motivierte Sammlung elektronischer Korpora mit geschriebenen deutschsprachigen Texten aus der Gegenwart und der neueren Vergangenheit. - sind über COSMAS II und KorAP kostenlos abfragbar - enthalten belletristische, wissenschaftliche und populärwissenschaftliche Texte, eine große Zahl von Zeitungstexten sowie eine breite Palette weiterer Textarten und werden kontinuierlich weiterentwickelt. - werden im Hinblick auf Umfang, Variabilität, Qualität und Aktualität akquiriert und erlauben in der Nutzungsphase über COSMAS II und v.a. KorAP die Komposition virtueller Korpora, die repräsentativ oder auf spezielle Aufgabenstellungen zugeschnitten sind. - enthalten ausschließlich urheberrechtlich abgesichertes Material.");
        ArrayList<Metadata.License> derekoRights = new ArrayList<>(List.of(
                new Metadata.License("cc-by-4.0"),
                new Metadata.License("cc-by-nc-4.0"),
                new Metadata.License("cc0-1.0"),
                // new Metadata.License(new Metadata.LocalizedStrings().add(new Language(ControlledVocabulary.LanguageId.EN), "Proprietory"), new Metadata.LocalizedStrings()),
                new Metadata.License(new Metadata.LocalizedStrings().add(new Language(ControlledVocabulary.LanguageId.EN), "Restricted"), new Metadata.LocalizedStrings())
                //new Metadata.License(new Metadata.LocalizedStrings().add(new Language(ControlledVocabulary.LanguageId.EN), "Other"), new Metadata.LocalizedStrings())
        ));
        metadata.addRights(derekoRights);
        ArrayList<Metadata.Contributor> derekoContributors = new ArrayList<>();
        derekoContributors.add(new Metadata.Contributor(new Metadata.PersonOrOrg("Marc", "Kupietz", "Kupietz, Marc"), 
                new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.DataCurator))
                .addAffiliations(new ArrayList<>(List.of(new Affiliation(Optional.empty(), Optional.of("Leibniz-Institut für Deutsche Sprache (IDS)"))))));
        metadata.addContributors(derekoContributors);
        metadata.addLanguages(new ArrayList<>(List.of(new Language(languageIdFactory.usingId3("deu")))));
        ArrayList<Metadata.Date> derekoDates = new ArrayList<>();
        derekoDates.add(new Metadata.Date(new Metadata.ExtendedDateTimeFormat0("1965"), 
                new Metadata.Date.DateType(new ControlledVocabulary.DateTypeId(ControlledVocabulary.DateTypeId.EDateType.Other), 
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), "Other"))).setDescription("Start year"));
        derekoDates.add(new Metadata.Date(new Metadata.ExtendedDateTimeFormat0("2022"), 
                new Metadata.Date.DateType(new ControlledVocabulary.DateTypeId(ControlledVocabulary.DateTypeId.EDateType.Updated), 
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), "Updated"))).setDescription("Last update"));
        derekoDates.add(new Metadata.Date(new Metadata.ExtendedDateTimeFormat0("1591").addEndYear("2021"), 
                new Metadata.Date.DateType(new ControlledVocabulary.DateTypeId(ControlledVocabulary.DateTypeId.EDateType.Other), 
                        new Metadata.LocalizedStrings().add(new Language(languageIdFactory.usingId2("en")), "Other"))).setDescription("Time coverage"));
        metadata.addDates(derekoDates);
        metadata.setPublisher("Leibniz-Institut für Deutsche Sprache (IDS)");
        ArrayList<Metadata.AlternateIdentifier> derekoAlternateIdentifiers = new ArrayList<>(List.of(
                new Metadata.AlternateIdentifier("https://hdl.handle.net/NOTYET", new ControlledVocabulary.RecordIdentifierScheme(ControlledVocabulary.RecordIdentifierScheme.ERecordItentifierScheme.DOI))
        ));
        metadata.addAlternativeIdentifiers(derekoAlternateIdentifiers);
        metadata.addFormats(new ArrayList<>(List.of("application/xml")));
        metadata.addFundingReferences(new ArrayList<>(List.of(new Metadata.FundingReference(new Metadata.FundingReference.Funder(Optional.empty(), Optional.of("DFG"))))));
        /*
        new Metadata.Creator().setRole()))
        */
        Metadata derekoMetadata = om.readValue(this.getClass().getClassLoader().getResourceAsStream("DeReKo_Invenio.json"), Metadata.class);
        for (Field f : Metadata.class.getDeclaredFields()) {
            f.setAccessible(true);
            Assertions.assertEquals(f.get(metadata),f.get(derekoMetadata),"Field " + f.getName() + ": ");
        }
    }
    
    @Test
    void jsonTest() throws IOException {
        om.readValue(this.getClass().getClassLoader().getResourceAsStream("DeReKo_Invenio.json"), Metadata.class);
        om.readValue(this.getClass().getClassLoader().getResourceAsStream("IDSBestand_Invenio.json"), Metadata.class);
        om.readValue(this.getClass().getClassLoader().getResourceAsStream("WDD_Invenio.json"), Metadata.class);
    }
    
    @Test
    void setterTest() {
        Metadata metadata = new Metadata(
                resourceType,
                creators,
                title,
                publicationDate
                        );
        metadata.addAdditionalTitles(additionalTitles);
        metadata.addAdditionalDescriptions(additionalDescriptions);
        metadata.addRights(rights);
        metadata.addContributors(contributors);
        metadata.addSubjects(subjects);
        metadata.addLanguages(languages);
        metadata.addDates(dates);
        metadata.addAlternativeIdentifiers(alternateIdentifiers);
        metadata.addRelatedIdentifiers(relatedIdentifiers);
        metadata.setLocations(locations);
        metadata.addFundingReferences(funding);
        metadata.addReferences(references);
        metadata.setDescription("<strong>Test</strong>");
        metadata.setVersion("v1.0.0");
        metadata.setPublisher("InvenioRDM");
        metadata.addSizes(List.of("11 pages"));
        metadata.addFormats(List.of("application/pdf"));
        // Test all setters
        // Description
        Optional<String> oldDescription = metadata.getDescription();
        Optional<String> newDescription = metadata.setDescription("new description").getDescription();
        Assertions.assertEquals(oldDescription, Optional.of("<strong>Test</strong>"));
        Assertions.assertNotEquals(oldDescription, newDescription);
        Assertions.assertEquals(newDescription, Optional.of("new description"));
        // Locations
        Metadata.Location oldLocations = metadata.getLocations();
        Metadata.Location newLocations = metadata.setLocations(new Metadata.Location(
                List.of(new Metadata.Location.LocationFeature(Optional.empty(), new ArrayList<>(), Optional.of("A new location"), Optional.empty()))
        )).getLocations();
        Assertions.assertEquals(locations, oldLocations);
        Assertions.assertNotEquals(oldLocations, newLocations);
        Assertions.assertEquals(new Metadata.Location(
                List.of(new Metadata.Location.LocationFeature(Optional.empty(), new ArrayList<>(), Optional.of("A new location"), Optional.empty()))
        ), newLocations);
        // Publication date
        Metadata.ExtendedDateTimeFormat0 oldPublicationDate = metadata.getPublicationDate();
        Metadata.ExtendedDateTimeFormat0 newPublicationDate = metadata.setPublicationDate(new Metadata.ExtendedDateTimeFormat0("1111").addEndYear("2222")).getPublicationDate();
        Assertions.assertEquals(publicationDate, oldPublicationDate);
        Assertions.assertNotEquals(oldPublicationDate, newPublicationDate);
        Assertions.assertEquals(new Metadata.ExtendedDateTimeFormat0("1111").addEndYear("2222"), newPublicationDate);
        // Publisher
        Optional<String> oldPublisher = metadata.getPublisher();
        Optional<String> newPublisher = metadata.setPublisher("new publisher").getPublisher();
        Assertions.assertEquals(Optional.of("InvenioRDM"), oldPublisher);
        Assertions.assertNotEquals(oldPublisher, newPublisher);
        Assertions.assertEquals(Optional.of("new publisher"), newPublisher);
        //Title
        String oldTitle = metadata.getTitle();
        String newTitle = metadata.setTitle("new title").getTitle();
        Assertions.assertEquals(title, oldTitle);
        Assertions.assertNotEquals(oldTitle, newTitle);
        Assertions.assertEquals("new title", newTitle);
        Optional<String> oldVersion = metadata.getVersion();
        Optional<String> newVersion = metadata.setVersion("v2").getVersion();
        Assertions.assertEquals(Optional.of("v1.0.0"), oldVersion);
        Assertions.assertNotEquals(oldVersion, newVersion);
        Assertions.assertEquals(Optional.of("v2"), newVersion);
    }
}
