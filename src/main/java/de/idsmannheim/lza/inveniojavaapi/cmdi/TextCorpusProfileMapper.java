/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import org.jdom2.Document;

/**
 * Mapper for the CMDI TextCorpusProfile
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class TextCorpusProfileMapper extends CmdiProfileMapping {

    public TextCorpusProfileMapper(Document d) {
        super(d);
        // Update the global XSL
        xslStream = this.getClass().getClassLoader().getResourceAsStream("TextCorpusProfile2InvenioMD.xsl");
    }

}
