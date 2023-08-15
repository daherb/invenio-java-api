/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import de.idsmannheim.lza.inveniojavaapi.cmdi.CmdiProfileMapping;
import de.idsmannheim.lza.inveniojavaapi.cmdi.CollectionProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.TextCorpusProfileMapper;
import de.idsmannheim.lza.xmlmagic.MimeType;
import de.idsmannheim.lza.xmlmagic.XmlMagic;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import net.sf.saxon.s9api.SaxonApiException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.ElementFilter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPathBuilder;
import org.jdom2.xpath.XPathFactory;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class for loading CMDI data and exporting to Invenio Metadata using CmdiProfileMapping
 * 
 */
public class CMDI {
    
    /***
     * Reads a CMDI file as a JDOM Document
     * @param cmdiFile the CMDI file
     * @return the document
     * @throws JDOMException
     * @throws IOException 
     */
    public static Document readCmdiFile(File cmdiFile) throws JDOMException, IOException {
        return new SAXBuilder().build(cmdiFile);
    }
    /**
     * Create a CMDI metadata mapping suitable for the metadata file
     * @param cmdiFile the file to be mapped
     * @return the CMDI metadata mapping
     * @throws JDOMException
     * @throws IOException 
     */
    private static CmdiProfileMapping createCmdiProfileMapping(Document cmdiDocument) throws JDOMException, IOException {
        XmlMagic magic = new XmlMagic(cmdiDocument);
        for (MimeType mt : magic.getMimeTypes()) {
            // Find the CMDI mime type
            if (mt.getSubtype().equals("x-cmdi")) {
                switch (mt.getParameters().get("profile")) {
                    case "clarin.eu:cr1:p_1559563375778" -> {
                        return new TextCorpusProfileMapper(cmdiDocument);
                    }
                    case "clarin.eu:cr1:p_1659015263839" -> {
                        return new CollectionProfileMapper(cmdiDocument);
                    }
//                    case "clarin.eu:cr1:p_1527668176128" -> {
//                        return new SpeechCorpusProfileMapper(cmdiDocument);
//                    }
//                    case "clarin.eu:cr1:p_1366895758244" -> {
//                        return new OLACDcmiTermsMapper(document);
//                    }
                    default -> throw new IOException("Unsupported CMDI profile " + mt.getParameters().get("profile"));
                }
            }
        }
        throw new IOException("Unrecognized CMDI file or profile");
    }
    
    /***
     * Converts CMDI metadata into Invenio metadata
     * @param cmdiDocument the CMDI document
     * @return the Invenio metadata
     * @throws IllegalArgumentException
     * @throws IOException
     * @throws SaxonApiException
     * @throws JDOMException 
     */
    public static Metadata convertCmdiMetadata(Document cmdiDocument) throws IllegalArgumentException, IOException, SaxonApiException, JDOMException {
        CmdiProfileMapping mapper = createCmdiProfileMapping(cmdiDocument);
        return mapper.map();
    }
    
    /***
     * Gets the self link of a CMDI document
     * @param cmdiDocument the document
     * @return the link
     * @throws JDOMException
     * @throws IOException
     */
    public static Optional<String> getSelfLink(Document cmdiDocument) throws JDOMException, IOException {
        Element selfLink = (Element) new XPathBuilder("//*[local-name()='MdSelfLink']",new ElementFilter())
                .compileWith(XPathFactory.instance())
                .evaluateFirst(cmdiDocument);
        if (selfLink != null && !selfLink.getText().isBlank()) {
            return Optional.of(selfLink.getTextNormalize());
        }
        else {
            return Optional.empty();
        }
    }
}
