/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
    
    /**
     * Gets the name of the resource. Used as alternative title in Invenio
     * @return the resource name if it exists
     */
    public abstract Optional<String> getResourceName();
    
    /**
     * Gets the title of the resource. Used as the title in Invenio
     * @return the title if it exists
     */
    public abstract Optional<String> getResourceTitle();
    
    /**
     * Gets the resource type or class. Used as the resource type in invenio
     * @return the resource type if it exists
     */
    public abstract Optional<Metadata.ResourceType> getResourceType();
    
    /**
     * Gets the version. Used as version in Invenio
     * @return the version as string if it exists
     */
    public abstract Optional<String> getVersion();
    
    /**
     * Gets the publication date. Used as publication date in Invenio
     * @return the publication date if it exists
     */
    public abstract Optional<Metadata.ExtendedDateTimeFormat0> getPublicationDate();
    
    /**
     * Gets the legal owner of the resource. Used as the rights holder creators in Invenio
     * @return the legal owner if it exists
     */
    public abstract Optional<String> getLegalOwner();
    
    /**
     * Gets the location. Used as a location in Invenio
     * @return the location if it exists
     */
    public abstract Optional<String> getLocation();
    
    /**
     * Gets the list of creators. Used as creators in Invenio
     * @return potentially empty list of creators
     */
    public abstract List<String> getCreators();
    
    /**
     * Gets the list of subject languages. Used as languages in Invenio
     * @return potentially empty list of languages
     */
    public abstract List<String> getSubjectLanguages();

    /**
     * Gets the list of licenses. Used as rights in Invenio
     * @return potentially empty list of licenses
     */
    public abstract List<String> getLicenses();
    
}
