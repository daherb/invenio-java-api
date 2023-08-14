/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import org.jdom2.Document;
/**
 * Mapper for the CMDI CollectionProfile
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class CollectionProfileMapper extends CmdiProfileMapping {

    public CollectionProfileMapper(Document d) {
        super(d);
        // Update the global XSL
        xslStream = this.getClass().getClassLoader().getResourceAsStream("Collections2InvenioMD.xsl");
    }
}
