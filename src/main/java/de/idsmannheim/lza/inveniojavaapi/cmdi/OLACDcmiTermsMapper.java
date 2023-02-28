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
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class OLACDcmiTermsMapper extends CmdiProfileMapping {

    private final List<Namespace> namespaces = List.of(
                Namespace.getNamespace("cmd", "http://www.clarin.eu/cmd")
        );
    
    public OLACDcmiTermsMapper(Document d) {
        super(d);
        setNamespaces(namespaces);
    }

    
    @Override
    public Optional<String> getResourceName() {
        return Optional.empty();
    }

    @Override
    public Optional<String> getResourceTitle() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:title",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText);
    }

    @Override
    public Optional<Metadata.ResourceType> getResourceType() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:type",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map((t) -> {
                    if (t.getText().equals("collection")) {
                        return new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.PublicationAnnotationCollection));
                    }
                    else {
                        return new Metadata.ResourceType(new ControlledVocabulary.ResourceType(ControlledVocabulary.ResourceType.EResourceType.Other));
                    }
                });
    }

    @Override
    public Optional<String> getVersion() {
        return Optional.empty();
    }

    @Override
    public Optional<Metadata.ExtendedDateTimeFormat0> getPublicationDate() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:issued",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map((d) -> {
                    return Metadata.ExtendedDateTimeFormat0.parseDateToExtended(d.getText());
                });
    }

    @Override
    public Optional<String> getLegalOwner() {
        return Optional.ofNullable(XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:publisher",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument))
                .map(Element::getText);
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.empty();
    }

    @Override
    public List<String> getCreators() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:creator",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map(Element::getText).toList();
    }

    @Override
    public List<String> getSubjectLanguages() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:language",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map(Element::getText).toList();
    }

    @Override
    public List<String> getLicenses() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:rights",
                        new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument)
                .stream().map(Element::getText).toList();
    }
    
}
