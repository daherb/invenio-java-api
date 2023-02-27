/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import java.util.HashMap;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.filter.ElementFilter;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class TextCorpusProfileMapper extends CmdiProfileMapping {

    private List<Namespace> namespaces = List.of(
                Namespace.getNamespace("cmd1", "http://www.clarin.eu/cmd/1"),
                Namespace.getNamespace("cmdp", "http://www.clarin.eu/cmd/1/profiles/clarin.eu:cr1:p_1559563375778")
        );
    
    public TextCorpusProfileMapper(Document d) {
        super(d);
        setNamespaces(namespaces);
    }

    
    @Override
    public Element getResourceName() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceName",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getResourceTitle() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceTitle",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getResourceClass() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:ResourceClass",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getVersion() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Version",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getPublicationDate() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:PublicationDate",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getLegalOwner() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:LegalOwner",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getLocation() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:GeneralInfo/cmdp:Location",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public List<Element> getCreators() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:Creation/cmdp:Creators/cmdp:Creator",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
    }

    @Override
    public List<Element> getSubjectLanguages() {
        return XPathFactory.instance()
                .compile("/cmd1:CMD/cmd1:Components/cmdp:TextCorpusProfile/cmdp:TextCorpusContext/cmdp:SubjectLanguages/cmdp:SubjectLanguage/cmdp:Language/cmdp:ISO639/cmdp:iso-639-3-code",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
    }
    
    
    
}
