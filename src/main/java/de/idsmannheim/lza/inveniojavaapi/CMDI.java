/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import de.idsmannheim.lza.inveniojavaapi.cmdi.CmdiProfileMapping;
import de.idsmannheim.lza.inveniojavaapi.cmdi.CollectionProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.SpeechCorpusProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.TextCorpusProfileMapper;
import de.idsmannheim.lza.xmlmagic.MimeType;
import de.idsmannheim.lza.xmlmagic.XmlMagic;
import java.io.File;
import java.io.IOException;
import net.sf.saxon.s9api.SaxonApiException;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class for loading CMDI data and exporting to Invenio Metadata using CmdiProfileMapping
 * 
 */
public class CMDI {
    
        /**
     * Create a CMDI metadata mapping suitable for the metadata file
     * @param metadataFile the file to be mapped
     * @return the CMDI metadata mapping
     * @throws JDOMException
     * @throws IOException 
     */
    public static CmdiProfileMapping createCmdiProfileMapping(File metadataFile) throws JDOMException, IOException {
        // Read the CMDI file
        Document document = new SAXBuilder().build(metadataFile);
        XmlMagic magic = new XmlMagic(document);
        for (MimeType mt : magic.getMimeTypes()) {
            // Find the CMDI mime type
            if (mt.getSubtype().equals("x-cmdi")) {
                switch (mt.getParameters().get("profile")) {
                    case "clarin.eu:cr1:p_1527668176128" -> {
                        return new SpeechCorpusProfileMapper(document);
                    }
                    case "clarin.eu:cr1:p_1559563375778" -> {
                        return new TextCorpusProfileMapper(document);
                    }
                    case "clarin.eu:cr1:p_1659015263839" -> {
                        return new CollectionProfileMapper(document);
                    }
                    default -> throw new IOException("Unsupported CMDI profile " + mt.getParameters().get("profile"));
                }
                // Speech corpus profile
                // Text corpus profile
                // OLAC DCMI terms
//                    case "clarin.eu:cr1:p_1366895758244":
//                        return new OLACDcmiTermsMapper(document);
                            }
        }
        throw new IOException("Unrecognized CMDI file or profile");
    }
    
    public static Metadata readCmdiMetadata(File cmdiFile) throws IllegalArgumentException, IOException, SaxonApiException, JDOMException {
        CmdiProfileMapping mapper = createCmdiProfileMapping(cmdiFile);
        return mapper.map();
    }
    
}
