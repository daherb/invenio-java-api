/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import de.idsmannheim.lza.inveniojavaapi.ControlledVocabulary;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return getOptionalText("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:title");
    }

    @Override
    public Optional<Metadata.ResourceType> getResourceType() {
        return getOptionalText("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:type")
                .map((t) -> {
                    if (t.equals("collection")) {
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
        return getOptionalText("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:issued")
                .map((d) -> Metadata.ExtendedDateTimeFormat0.parseDateToExtended(d));
    }

    @Override
    public Optional<String> getLegalOwner() {
        return getOptionalText("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:publisher");
    }

    @Override
    public Optional<String> getLocation() {
        return Optional.empty();
    }

    @Override
    public List<String> getCreators() {
        return getTextList("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:creator");
    }

    @Override
    public List<String> getSubjectLanguages() {
        return getTextList("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:language");
    }

    @Override
    public List<String> getLicenses() {
        return getTextList("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:rights");
    }
    
    @Override
    public Map<String, String> getDescription() {
        return getLangMap("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:Description");
    }
}
