/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.xpath.XPathFactory;

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
    public Optional<String> getResourceName() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText);
    }

    @Override
    public Optional<String> getResourceTitle() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceTitle",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument)).map(Element::getText);
    }

    @Override
    public Optional<Metadata.ResourceType> getResourceType() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceClass",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText)
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
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:Version",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText);
    }

    @Override
    public Optional<Metadata.ExtendedDateTimeFormat0> getPublicationDate() {
        return Optional.of(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map((d) -> {
                    return Metadata.ExtendedDateTimeFormat0.parseDateToExtended(d.getText());
                });
    }

    @Override
    public Optional<String> getLegalOwner() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:LegalOwner",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText);
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:GeneralInfo/cmdp:Location",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map((e) -> CmdiProfileMapping.getAllText(e).stream().collect(Collectors.joining("\n")));
    }

    @Override
    public List<String> getCreators() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:Creation/cmdp:Creators/cmdp:Creator",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map(Element::getText).toList();
    }

    @Override
    public List<String> getSubjectLanguages() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:SpeechCorpusContext/cmdp:SubjectLanguages/cmdp:SubjectLanguage/cmdp:Language/cmdp:ISO639/cmdp:iso-639-3-code",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map(Element::getText).toList();
    }

    @Override
    public List<String> getLicenses() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:SpeechCorpusProfile/cmdp:Access",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map((e) -> CmdiProfileMapping.getAllText(e).stream().collect(Collectors.joining("\n")))
                        .toList();
    }
    
    
    
}
