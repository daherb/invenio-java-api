/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class for loading CMDI data matching the TextCorpus profile (https://catalog.clarin.eu/ds/ComponentRegistry#/?itemId=clarin.eu%3Acr1%3Ap_1562754657343&registrySpace=public) 
 * and exporting to Invenio Metadata
 * 
 */
public class CMDI {
    
    public static Metadata readCmdiMetadata(Document cmdiDocument) throws IllegalArgumentException, IOException {
        List<Namespace> namespaces = List.of(
                Namespace.getNamespace("cmd1", "http://www.clarin.eu/cmd/1"),
                Namespace.getNamespace("cmd", "http://www.clarin.eu/cmd/"),
                Namespace.getNamespace("cmdp", "http://www.clarin.eu/cmd/1/profiles/clarin.eu:cr1:p_1559563375778")
        );
        Element cmdiResourceName = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        Element cmdiResourceTitle = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceTitle" +
                       "| /cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:title",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        Element cmdiResourceClass = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceClass" +
                      " | /cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:type",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        Element cmdiVersion = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Version",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        // LifeCycleStatus ignored
        Element cmdiPublicationDate = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate" + 
                       "| /cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:issued",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        // LastUpdated ignored
        Element cmdiLegalOwner = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LegalOwner",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        // Genre ignored
        // FieldOfResearch ignored
        Element cmdiLocation = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Location",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
        // ModalityInfo ignored
        List<Element> cmdiCreators = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:Creation/cmdp:Creators/cmdp:Creator" +
                       "| /cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:creator",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
        // ...
        List<Element> cmdiSubjectLanguages = XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:TextCorpusContext/cmdp:SubjectLanguages/cmdp:SubjectLanguage/cmdp:Language/cmdp:ISO639/cmdp:iso-639-3-code" +
                       "| /cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:language",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
        
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
                            new Metadata.LocalizedStrings().add(new Metadata.Language(ControlledVocabulary.LanguageIdFactory.usingId2("en")), "Alternative title")))));
        }
        if (cmdiVersion != null && !cmdiVersion.getText().isBlank()) {
            metadata.setVersion(cmdiVersion.getText());
        }
        if (cmdiLocation != null) {
            // TODO: Split into components or get data in separate elements
            String text = getAllText(cmdiLocation).stream().collect(Collectors.joining("\n"));
            if (!text.isBlank()) {
                metadata.setLocations(new Metadata.Location(List.of(
                        new Metadata.Location.LocationFeature(Optional.empty(), new ArrayList<>(), Optional.of(text), Optional.of("Contact information")))));
            }
        }
        if (cmdiSubjectLanguages != null && !cmdiSubjectLanguages.isEmpty()) {
            List<Metadata.Language> languages = new ArrayList<>();
            for (Element l : cmdiSubjectLanguages) {
                languages.add(new Metadata.Language(ControlledVocabulary.LanguageIdFactory.usingId3(l.getText())));
            }
            metadata.addLanguages(languages);
        }
        return metadata;
    }
    
    // Gets all text from an element and its children
    public static List<String> getAllText(Element e) {
        List<String> result = new ArrayList<>(List.of(e.getTextNormalize()));
        result.addAll(e.getChildren().stream().flatMap(c -> getAllText(c).stream()).filter(s -> !s.isBlank()).collect(Collectors.toList()));
        return result;
    }
    
    private static final Logger LOG = Logger.getLogger(CMDI.class.getName());
}
