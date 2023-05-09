/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.CollectionProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.OLACDcmiTermsMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.SpeechCorpusProfileMapper;
import de.idsmannheim.lza.inveniojavaapi.cmdi.TextCorpusProfileMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
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
    }
    
    @Test
    public void cmdiMapperTest() throws JDOMException, IOException {
        CollectionProfileMapper cpm = new CollectionProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/DeReKo.cmdi")));
        Assertions.assertEquals(new ArrayList<>(List.of("Leibniz-Institut für Deutsche Sprache (IDS)")),cpm.getCreators());
        HashMap<String, String> description = new HashMap<>();
        description.put("de", "Die Korpora geschriebener Gegenwartssprache des IDS - bilden mit 53 Milliarden Wörtern " +
                "(Stand 08.03.2022) die weltweit größte linguistisch motivierte Sammlung elektronischer Korpora mit "+ 
                "geschriebenen deutschsprachigen Texten aus der Gegenwart und der neueren Vergangenheit. - sind über COSMAS II "+
                "und KorAP kostenlos abfragbar - enthalten belletristische, wissenschaftliche und populärwissenschaftliche Texte, " +
                "eine große Zahl von Zeitungstexten sowie eine breite Palette weiterer Textarten und werden kontinuierlich " +
                "weiterentwickelt. - werden im Hinblick auf Umfang, Variabilität, Qualität und Aktualität akquiriert und " +
                "erlauben in der Nutzungsphase über COSMAS II und v.a. KorAP die Komposition virtueller Korpora, die repräsentativ " +
                "oder auf spezielle Aufgabenstellungen zugeschnitten sind. - enthalten ausschließlich urheberrechtlich abgesichertes Material.");
        Assertions.assertEquals(description, cpm.getDescription());
        // Assertions.assertEquals(Optional.empty(), cpm.getLegalOwner());
        Assertions.assertEquals(List.of("CC-BY", "CC-BY-NC", "CC-0", "Proprietory", "Restricted", "Other"), cpm.getLicenses());
        // Assertions.assertEquals(Optional.empty(),cpm.getLocation());
        Assertions.assertEquals(Optional.of(new Metadata.ExtendedDateTimeFormat0("2023")), cpm.getPublicationDate());
        Assertions.assertEquals(Optional.of("DeReKo"), cpm.getResourceName());
        Assertions.assertEquals(Optional.of("Deutsches Referenzkorpus"), cpm.getResourceTitle());
        Assertions.assertEquals(Optional.of("https://hdl.handle.net/NOTYET"), cpm.getSelfLink());
        Assertions.assertEquals(List.of("deu"), cpm.getSubjectLanguages());
        Assertions.assertEquals(Optional.empty(),cpm.getVersion());
        OLACDcmiTermsMapper odtm = new OLACDcmiTermsMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/disko.cmdi")));
        SpeechCorpusProfileMapper scpm = new SpeechCorpusProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/a00.cmdi")));
        TextCorpusProfileMapper tcpm = new TextCorpusProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/HMAT.cmdi")));
    }
    
    @Test
    public void cmdiTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException {
//        CMDI.readCmdiMetadata(mapper)
    }
}
