/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import de.idsmannheim.lza.inveniojavaapi.cmdi.CollectionProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.TextCorpusProfileMapper;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import net.sf.saxon.s9api.SaxonApiException;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class CMDITest {
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
        om.enable(SerializationFeature.INDENT_OUTPUT);
    }
    
    // @Test
    public void cmdiMapperTest() throws JDOMException, IOException, SaxonApiException, IllegalArgumentException, IllegalAccessException {
        CollectionProfileMapper cpm = new CollectionProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/DeReKo.cmdi")));
        Metadata derekoMetadata = cpm.map();
        LOG.info(om.writeValueAsString(derekoMetadata));
        for (Field f : Metadata.class.getDeclaredFields()) {
            if (!f.getName().equals("locations")) {
                Assertions.assertNotNull(f.get(derekoMetadata), f.getName());
            }
        }
//        TextCorpusProfileMapper tpm = new TextCorpusProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/nganw.cmdi")));
//        TextCorpusProfileMapper tpm = new TextCorpusProfileMapper(new SAXBuilder().build(new File("/tmp/cmdi/dereko/DeReKo-2017-II/I5/kic.cmdi")));
        TextCorpusProfileMapper tpm = new TextCorpusProfileMapper(new SAXBuilder().build(new File("/tmp/cmdi/dereko/DeReKo-2019-II/I5_otherArchives/db.cmdi")));
        Metadata nganwMetadata = tpm.map();
        for (Field f : Metadata.class.getDeclaredFields()) {
            if (!f.getName().equals("locations")) {
                Assertions.assertNotNull(f.get(nganwMetadata), f.getName());
            }
        }
    }
    
    // @Test
    public void testAllCmdisInDir() throws IOException {
        //Path path = Path.of("/tmp/cmdi/collection");
        final List<File> failed = new ArrayList<>();
        Path path = Path.of("/tmp/cmdi/dereko");
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path t, BasicFileAttributes bfa) throws IOException {
                if (t.toString().endsWith(".cmdi")) {
                    LOG.log(Level.INFO, "{0}; Failed: {1}", new Object[]{t.toString(), failed.size()});
                    try {
                        LOG.info(om.writeValueAsString(CMDI.convertCmdiMetadata(CMDI.readCmdiFile(t.toFile()))));
                    } catch (IllegalArgumentException | SaxonApiException | JDOMException ex) {
                        Assertions.fail(t.toString(),ex);
                        //failed.add(t.toFile());
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        LOG.info(failed.stream().map(File::toString).collect(Collectors.joining("\n")));
    }
//    // TODO Fix test
//    @Test
//    public void cmdiTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException, JDOMException {
//        Metadata metadata = CMDI.readCmdiMetadata(new CollectionProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/DeReKo.cmdi"))));
//        Metadata m2 = om.readValue(ClassLoader.getSystemResourceAsStream("DeReKo_Invenio.json"),Metadata.class);
//        for (Field f : Metadata.class.getDeclaredFields()) {
//            // TODO fix all these problematic fields
//            if (!f.getName().equals("rights") && !f.getName().equals("contributors") && !f.getName().equals("dates") && !f.getName().equals("publisher") 
//                    && !f.getName().equals("alternativeIdentifiers") && !f.getName().equals("formats") && !f.getName().equals("locations") 
//                    // && !f.getName().equals("fundingReferences") 
//                    //&& !f.getName().equals("creators") 
//                    //&& !f.getName().equals("additionalTitles")
//                    ) {
//                f.setAccessible(true);
//                Assertions.assertEquals(f.get(m2), f.get(metadata),f.getName());
//            }
//        }
//        // LOG.info(om.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(metadata));
//    }
    private static final Logger LOG = Logger.getLogger(CMDITest.class.getName());
}
