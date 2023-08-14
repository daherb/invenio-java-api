/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import de.idsmannheim.lza.inveniojavaapi.cmdi.CmdiProfileMapping;
import java.io.IOException;
import java.util.logging.Logger;
import net.sf.saxon.s9api.SaxonApiException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Class for loading CMDI data and exporting to Invenio Metadata using CmdiProfileMapping
 * 
 */
public class CMDI {
    
    public static Metadata readCmdiMetadata(CmdiProfileMapping mapper) throws IllegalArgumentException, IOException, SaxonApiException {
        return mapper.map();
    }
    
}
