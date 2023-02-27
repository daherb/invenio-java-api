/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 *
 * Class representing the abstract mapping between CMDI profiles and Invenio metadata
 * 
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public abstract class CmdiProfileMapping {
    
    final Document cmdiDocument;
    List<Namespace> namespaces;
    
    public CmdiProfileMapping(Document d) {
        cmdiDocument = d;
    }
    
    public void setNamespaces(List<Namespace> ns) {
        namespaces = ns;
    }

    // Gets all text from an element and its children
    public static List<String> getAllText(Element e) {
        List<String> result = new ArrayList<>(List.of(e.getTextNormalize()));
        result.addAll(e.getChildren().stream().flatMap(c -> getAllText(c).stream()).filter(s -> !s.isBlank()).collect(Collectors.toList()));
        return result;
    }
    
    public abstract Element getResourceName();
    
    public abstract Element getResourceTitle();
    
    public abstract Element getResourceClass();
    
    public abstract Element getVersion();
    
    public abstract Element getPublicationDate();
    
    public abstract Element getLegalOwner();
    
    public abstract Element getLocation();
    
    public abstract List<Element> getCreators();
    
    public abstract List<Element> getSubjectLanguages();
    
    
}
