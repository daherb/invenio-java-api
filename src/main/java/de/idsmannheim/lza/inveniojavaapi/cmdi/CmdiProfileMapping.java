/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.cmdi;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.idsmannheim.lza.inveniojavaapi.Metadata;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamSource;
import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.SaxonApiException;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.Xslt30Transformer;
import org.jdom2.Document;
import org.jdom2.transform.JDOMSource;

/**
 *
 * Class representing the abstract mapping between CMDI profiles and Invenio metadata
 * 
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public abstract class CmdiProfileMapping {
    protected final Document cmdiDocument;
    protected InputStream xslStream;
    
    public CmdiProfileMapping(Document d) {
        this.cmdiDocument = d;
    }
    
    public Metadata map() throws SaxonApiException, IOException {
        // Saxon magic (https://www.saxonica.com/documentation12/index.html#!using-xsl/embedding/s9api-transformation) 
        // for transforming document using the XSLT referenced by
        // xslStream (coming from derived classes) 
        Processor processor = new Processor();
        Xslt30Transformer transformer = processor.newXsltCompiler().compile(new StreamSource(xslStream)).load30();
        // Using a stream to collect the transformation output
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        Serializer out = processor.newSerializer(outStream);
        // Using text method instead of json method because using json leads to
        // an error
        out.setOutputProperty(Serializer.Property.METHOD, "text");
        out.setOutputProperty(Serializer.Property.INDENT, "yes");
        transformer.transform(new JDOMSource(cmdiDocument), out);
        // Convert the json string to Metadata object
        ObjectMapper om = new ObjectMapper().findAndRegisterModules();
        return om.readValue(outStream.toByteArray(), Metadata.class);
    }
    
    private static final Logger LOG = Logger.getLogger(CmdiProfileMapping.class.getName());
}
