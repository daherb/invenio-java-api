/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import de.idsmannheim.lza.inveniojavaapi.cmdi.CmdiProfileMapping;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class for loading CMDI data matching the TextCorpus profile (https://catalog.clarin.eu/ds/ComponentRegistry#/?itemId=clarin.eu%3Acr1%3Ap_1562754657343&registrySpace=public) 
 * and exporting to Invenio Metadata
 * 
 */
public class CMDI {
    
    public static Metadata readCmdiMetadata(CmdiProfileMapping mapper) throws IllegalArgumentException, IOException {
        ControlledVocabulary.LanguageIdFactory languageIdFactory = new ControlledVocabulary.LanguageIdFactory();
        Element cmdiResourceName = mapper.getResourceName();
        Element cmdiResourceTitle = mapper.getResourceTitle();
        Element cmdiResourceClass = mapper.getResourceClass();
        Element cmdiVersion = mapper.getVersion();
        // LifeCycleStatus ignored
        Element cmdiPublicationDate = mapper.getPublicationDate();
        // LastUpdated ignored
        Element cmdiLegalOwner = mapper.getLegalOwner();
        // Genre ignored
        // FieldOfResearch ignored
        Element cmdiLocation = mapper.getLocation();
        // ModalityInfo ignored
        List<Element> cmdiCreators = mapper.getCreators();
        // ...
        List<Element> cmdiSubjectLanguages = mapper.getSubjectLanguages();
        
        // Map mandatory fields
        // We cannot get the resource type directly from the metadata but we can
        // try to map the ResourceClass (which is currently hardcoded to Corpus)
        // e.g. to PublicationAnnotationCollection
        Metadata.ResourceType resourceType;
        if (cmdiResourceClass != null && !cmdiResourceClass.getText().isBlank()) {
            if (cmdiResourceClass.getText().equalsIgnoreCase("Corpus")
                    || cmdiResourceClass.getText().equalsIgnoreCase("collection")) {
                //  Either Dataset or PublicationAnnotationCollection seem reasonable choices (see schema.org)
                resourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.PublicationAnnotationCollection));
            }
            else {
                resourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other));
            }
        }
        else {
           resourceType = new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other));
        }
        // Potentially not always present. Use current timestamp if missing
        // Try to parse the publication date
        Metadata.ExtendedDateTimeFormat0 publicationDate;
        if (cmdiPublicationDate != null && ! cmdiPublicationDate.getText().isBlank()) {
            publicationDate = Metadata.ExtendedDateTimeFormat0.parseDateToExtended(cmdiPublicationDate.getText());
        }
        // Otherwise just use current year
        else {
            publicationDate = new Metadata.ExtendedDateTimeFormat0(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        }
        String title = "";
        if (cmdiResourceTitle != null && !cmdiResourceTitle.getText().isBlank()) {
            title = cmdiResourceTitle.getText();
        }
        // Either add rightsholders or creators as creators
        ArrayList<Metadata.Creator> creators = new ArrayList<>();
        if (cmdiLegalOwner != null && !cmdiLegalOwner.getText().isBlank()) {
            Metadata.Creator creator = new Metadata.Creator(new Metadata.PersonOrOrg(cmdiLegalOwner.getText()))
                    .setRole(new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.RightsHolder));
            creators.add(creator);
        }
        if (cmdiCreators != null && !cmdiCreators.isEmpty()) {
            for (Element c : cmdiCreators) {
                creators.add(new Metadata.Creator(new Metadata.PersonOrOrg(c.getText()))
                        .setRole(new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.DataCollector)));
            }
        }
        Metadata metadata = new Metadata(resourceType, creators, title, publicationDate);
        // Add optional fields if they exist
        /* TODO: 
        - Map missing fields
        */
        if (cmdiResourceName != null && !cmdiResourceName.getText().isBlank()) {
            String resourceName = cmdiResourceName.getText();
            metadata.addAdditionalTitles(List.of(new Metadata.AdditionalTitle(resourceName, 
                    new Metadata.AdditionalTitle.TitleType(new ControlledVocabulary.TitleTypeId(ControlledVocabulary.TitleTypeId.ETitleType.AlternativeTitle),
                            new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), "Alternative title")))));
        }
        if (cmdiVersion != null && !cmdiVersion.getText().isBlank()) {
            metadata.setVersion(cmdiVersion.getText());
        }
        if (cmdiLocation != null) {
            // TODO: Split into components or get data in separate elements
            String text = CmdiProfileMapping.getAllText(cmdiLocation).stream().collect(Collectors.joining("\n"));
            if (!text.isBlank()) {
                metadata.setLocations(new Metadata.Location(List.of(
                        new Metadata.Location.LocationFeature(Optional.empty(), new ArrayList<>(), Optional.of(text), Optional.of("Contact information")))));
            }
        }
        if (cmdiSubjectLanguages != null && !cmdiSubjectLanguages.isEmpty()) {
            List<Metadata.Language> languages = new ArrayList<>();
            for (Element l : cmdiSubjectLanguages) {
                languages.add(new Metadata.Language(languageIdFactory.usingId3(l.getText())));
            }
            metadata.addLanguages(languages);
        }
        return metadata;
    }

    private static final Logger LOG = Logger.getLogger(CMDI.class.getName());
}
