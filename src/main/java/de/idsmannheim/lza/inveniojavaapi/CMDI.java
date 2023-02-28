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
        Optional<String> cmdiResourceName = mapper.getResourceName();
        Optional<String> cmdiResourceTitle = mapper.getResourceTitle();
        Optional<Metadata.ResourceType> cmdiResourceType = mapper.getResourceType();
        Optional<String> cmdiVersion = mapper.getVersion();
        // LifeCycleStatus ignored
        Optional<Metadata.ExtendedDateTimeFormat0> cmdiPublicationDate = mapper.getPublicationDate();
        // LastUpdated ignored
        Optional<String> cmdiLegalOwner = mapper.getLegalOwner();
        // Genre ignored
        // FieldOfResearch ignored
        Optional<String> cmdiLocation = mapper.getLocation();
        // ModalityInfo ignored
        List<String> cmdiCreators = mapper.getCreators();
        // ...
        List<String> cmdiSubjectLanguages = mapper.getSubjectLanguages();
        
        List<String> cmdiLicense = mapper.getLicenses();
        
        // Map mandatory fields
        // We cannot get the resource type directly from the metadata but we can
        // try to map the ResourceClass (which is currently hardcoded to Corpus)
        // e.g. to PublicationAnnotationCollection
        //  Either Dataset or PublicationAnnotationCollection seem reasonable choices (see schema.org)
        Metadata.ResourceType resourceType = cmdiResourceType
                .orElse(new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other)));
//                    || cmdiResourceType.getText().equalsIgnoreCase("collection")) {
        // Potentially not always present. Use current year if missing
        // Try to parse the publication date
        Metadata.ExtendedDateTimeFormat0 publicationDate = cmdiPublicationDate
                .orElse(new Metadata.ExtendedDateTimeFormat0(String.valueOf(Calendar.getInstance().get(Calendar.YEAR))));
        // Either title or the string "n/a"
        String title = cmdiResourceTitle.orElse("n/a");
        // Either add rightsholders or creators as creators
        ArrayList<Metadata.Creator> creators = new ArrayList<>();
        if (cmdiLegalOwner.isPresent()) {
            Metadata.Creator creator = new Metadata.Creator(new Metadata.PersonOrOrg(cmdiLegalOwner.get()))
                    .setRole(new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.RightsHolder));
            creators.add(creator);
            
        }
        for (String c : cmdiCreators) {
                creators.add(new Metadata.Creator(new Metadata.PersonOrOrg(c))
                        .setRole(new ControlledVocabulary.Role(ControlledVocabulary.Role.ERole.DataCollector)));
        }
        Metadata metadata = new Metadata(resourceType, creators, title, publicationDate);
        // Add optional fields if they exist
        /* TODO: 
        - Map missing fields
        */
        if (cmdiResourceName.isPresent()) {
            metadata.addAdditionalTitles(List.of(new Metadata.AdditionalTitle(cmdiResourceName.get(), 
                    new Metadata.AdditionalTitle.TitleType(new ControlledVocabulary.TitleTypeId(ControlledVocabulary.TitleTypeId.ETitleType.AlternativeTitle),
                            new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), "Alternative title")))));
        }
        if (cmdiVersion.isPresent()) {
            metadata.setVersion(cmdiVersion.get());
        }
        if (cmdiLocation.isPresent()) {
            // TODO: Split into components or get data in separate elements
            if (!cmdiLocation.get().isBlank()) {
                metadata.setLocations(new Metadata.Location(List.of(
                        new Metadata.Location.LocationFeature(Optional.empty(), new ArrayList<>(), cmdiLocation, Optional.of("Contact information")))));
            }
        }
        List<Metadata.Language> languages = new ArrayList<>();
        for (String l : cmdiSubjectLanguages) {
            languages.add(new Metadata.Language(languageIdFactory.usingId3(l)));
        }
        if (!languages.isEmpty()) {
            metadata.addLanguages(languages);
        }
        List<Metadata.License> rights = new ArrayList<>();
        for (String l : cmdiLicense) {
            
            Metadata.License license = new Metadata.License(
                    Optional.empty(),
                    new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), "n/a"),
                    new Metadata.LocalizedStrings().add(new Metadata.Language(languageIdFactory.usingId2("en")), l));
            rights.add(license);
        }
        if (!rights.isEmpty()) {
            metadata.addRights(rights);
        }
        return metadata;
    }

    private static final Logger LOG = Logger.getLogger(CMDI.class.getName());
}
