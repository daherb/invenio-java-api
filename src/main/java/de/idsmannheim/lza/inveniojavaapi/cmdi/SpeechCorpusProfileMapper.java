/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Namespace;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class SpeechCorpusProfileMapper extends CmdiProfileMapping {

    private List<Namespace> namespaces = List.of(
                Namespace.getNamespace("cmd1", "http://www.clarin.eu/cmd/1"),
                Namespace.getNamespace("cmdp", "http://www.clarin.eu/cmd/1/profiles/clarin.eu:cr1:p_1527668176128")
        );
    
    public SpeechCorpusProfileMapper(Document d) {
        super(d);
        setNamespaces(namespaces);
    }

    @Override
    public Optional<String> getSelfLink() {
        return getOptionalText("/cmd1:CMD/cmd1:Header/cmd1:MdSelfLink");
    }
    
    @Override
    public Optional<String> getResourceName() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName");
    }

    @Override
    public Optional<String> getResourceTitle() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceTitle");
    }

    @Override
    public Optional<Metadata.ResourceType> getResourceType() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceClass")
                .map((t) -> {
                    if (t.equals("Corpus")) {
                        return new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.PublicationAnnotationCollection));
                    }
                    else {
                        return new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other));
                    }
        });
    }

    @Override
    public Optional<String> getVersion() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:Version");
    }

    @Override
    public Optional<String> getPublicationDate() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate");
    }

    @Override
    public Optional<String> getLegalOwner() {
        return getOptionalText("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:LegalOwner");
    }

    @Override
    public Optional<String> getLocation() {
        return getElementList("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:Location")
                .stream().map((e) -> CmdiProfileMapping.getAllText(e).stream().collect(Collectors.joining(", ")))
                        .findAny();
    }

    @Override
    public List<String> getCreators() {
        return getTextList("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:Creation/cmdp:Creators/cmdp:Creator");
    }

    @Override
    public List<String> getSubjectLanguages() {
        return getTextList("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:SpeechCorpusContext/cmdp:SubjectLanguages/cmdp:SubjectLanguage/cmdp:Language/cmdp:ISO639/cmdp:iso-639-3-code");
    }

    @Override
    public List<String> getLicenses() {
        return getTextList("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:Access");
    }

    @Override
    public Map<String, String> getDescription() {
        return getLangMap("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:Descriptions/cmdp:Description");
    }
}
