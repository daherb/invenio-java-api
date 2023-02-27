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
public class OLACDcmiTermsMapper extends CmdiProfileMapping {

    private List<Namespace> namespaces = List.of(
                Namespace.getNamespace("cmd", "http://www.clarin.eu/cmd")
        );
    
    public OLACDcmiTermsMapper(Document d) {
        super(d);
        setNamespaces(namespaces);
    }

    
    @Override
    public Element getResourceName() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Element getResourceTitle() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:title",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getResourceClass() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:type",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getVersion() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Element getPublicationDate() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:issued",
                new ElementFilter(), new HashMap<>(), namespaces).evaluateFirst(cmdiDocument);
    }

    @Override
    public Element getLegalOwner() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Element getLocation() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Element> getCreators() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:creator",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
    }

    @Override
    public List<Element> getSubjectLanguages() {
        return XPathFactory.instance()
                .compile("/cmd:CMD/cmd:Components/cmd:OLAC-DcmiTerms-ref/cmd:language",
                new ElementFilter(), new HashMap<>(), namespaces).evaluate(cmdiDocument);
    }
    
    
    
}
