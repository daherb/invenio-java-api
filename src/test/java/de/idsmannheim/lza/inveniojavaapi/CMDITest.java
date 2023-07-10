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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
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
        Assertions.assertEquals(Optional.empty(), cpm.getLegalOwner());
        Assertions.assertEquals(List.of("CC-BY", "CC-BY-NC", "CC-0", "Proprietory", "Restricted", "Other"), cpm.getLicenses());
        Assertions.assertEquals(Optional.of("118318106X\n" +
                "GND\n" +
                "156585213\n" +
                "VIAF\n" +
                "n80067129\n" +
                "LCCN")
                ,cpm.getLocation());
        Assertions.assertEquals(Optional.of("2022"), cpm.getPublicationDate());
        Assertions.assertEquals(Optional.of("DeReKo"), cpm.getResourceName());
        Assertions.assertEquals(Optional.of("Deutsches Referenzkorpus"), cpm.getResourceTitle());
        Assertions.assertEquals(Optional.of("https://hdl.handle.net/NOTYET"), cpm.getSelfLink());
        Assertions.assertEquals(List.of("deu"), cpm.getSubjectLanguages());
        Assertions.assertEquals(Optional.empty(),cpm.getVersion());
        OLACDcmiTermsMapper odtm = new OLACDcmiTermsMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/disko.cmdi")));
        description = new HashMap<>();
        description.put("en", "DISKO is a corpus of written learners language in an academic setting.");
        description.put("de", "Bei DISKO handelt es sich um ein schriftliches Lernerkorpus, das in mehrere Subkorpora " +
                "gegliedert ist. Alle Subkorpora enthalten Texte, die in diagnostischen Verfahren zur Feststellung der " +
                "sogenannten „sprachlichen Studierfähigkeit“ erhoben wurden. Details zu den Subkorpora finden sich im " +
                "Korpushandbuch (Muntschick et al. 2020).");
        Assertions.assertEquals(description, odtm.getDescription()); 
        Assertions.assertEquals(Optional.of("Universität Leipzig: Herder-Institut; Julius-Maximilians-Universität Würzburg: Pädagogische Psychologie"), odtm.getLegalOwner());
        Assertions.assertEquals(List.of("http://repos.ids-mannheim.de/corpora/DISKO/data/DISKO_Lizenz.txt"), odtm.getLicenses());
        Assertions.assertEquals(Optional.empty(),odtm.getLocation());
        Assertions.assertEquals(Optional.of("2022-07-25"), odtm.getPublicationDate());
        Assertions.assertEquals(Optional.empty(), odtm.getResourceName());
        Assertions.assertEquals(Optional.of("Deutsch im Studium: Lernerkorpus (DISKO)"), odtm.getResourceTitle());
        Assertions.assertEquals(Optional.of("http://hdl.handle.net/10932/00-0534-6404-3CE0-0001-3"), odtm.getSelfLink());
        Assertions.assertEquals(List.of("deu"), odtm.getSubjectLanguages());
        Assertions.assertEquals(Optional.empty(),odtm.getVersion());
        SpeechCorpusProfileMapper scpm = new SpeechCorpusProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/HMAT.cmdi")));
        description = new HashMap<>();
        description.put("de", "Das Hamburg Maptask Corpus wurde zwischen Oktober 2009 und September 2010 im Projekt Z2 " +
                "\"Computergestützte Erfassungs- und Analysemethoden multilingualer Daten am SFB \"Mehrsprachigkeit\" an " + 
                "der Universität Hamburg erstellt. Im Juni 2013 wurde das Korpus um zwei Aufnahmen mit Video erweitert. " +
                "Hauptmotivation für die Erstellung des Korpus war das Bereitstellen von Datensätzen zum Testen und Demonstrieren " + 
                "der Funktionalitäten des EXMARaLDA-Systems, insbesondere mit Blick auf das Annotieren und Teilen von Daten. Als " + 
                "Elizitations-Experiment für die Aufnahmen wurden die Map Tasks aus dem \"Deutsch Heute\"-Korpus verwendet. Die Map " +
                "Tasks wurden mit 25 DeutschlernerInnen mit unterschiedlich fortgeschrittenen Deutschkenntnissen und einer L1-Sprecherin " +
                "duchgeführt. Die Erstsprachen der SprecherInnen decken ein breites Spektrum an Sprachen ab, das von romanischen " +
                "sprachen (Französisch, Galizisch, Spanisch) über slawische Sprachen (Russisch, Polnisch, Bulgarisch) zu iranischen " + 
                "Sprachen (Farsi/Dari) reicht und auch nicht-indoeuropäische Sprachen (Türkisch, Arabisch, Chinesisch, Japanisch, Thai, " +
                "Vietnamesisch) umfasst. Da die SprecherInnen über studentische Hilfskräfte rekrutiert wurden, sind sie größtenteils " +
                "zwischen 17 und 40 Jahren alt und haben ein höheres Bildungsniveau. Frühere Versionen des Korpus wurden am Hamburger " +
                "Zentrum für Sprachkorpora (HZSK) archiviert und sind auch über das Zentrum für Nachhaltiges Forschungsdatenmanagement " + 
                "der Universität Hamburg verfügbar.");
        description.put("en", "The Hamburg Map Task Corpus was created between October 2009 and September 2010 in the project Z2 ‚Computer " +
                "assisted methods for the creation and analysis of multilingual data’ of the Research Centre on Multilingualism at the " +
                "University of Hamburg. In June 2013, the corpus was extended with two recordings on video. The main motivation for creating " +
                "the corpus was to provide a set of data for testing and demonstrating the capabilities of the EXMARaLDA system, in " +
                "particular with respect to annotation and data sharing. The map task designed for the corpus \"Deutsch Heute\" was chosen " + 
                "as the basic experiment for the corpus. The map task was performed by 25 learners of German with varying proficiency and " +
                "on L1 speaker. The speakers’ L1 cover a broad spectrum of languages, including Romance languages (French, Galician, Spanish), " +
                "Slavic languages (Russian, Polish, Bulgarian), Iranian languages (Farsi/Dari) and diverse languages from Non-Indo-European " +
                "families (Turkish, Arabic, Chinese, Japanese, Thai, Vietnamese). Since speakers were selected and contacted by student " + 
                "assistants in the project, most of them are between 17 and 40 years old and have a higher education. Earlier versions of the " +
                "corpus were archived at the Hamburger Zentrum für Sprachkorpora and are also available via the Zentrum für Nachhaltiges " + 
                "Forschungsdatenmanagement at the University of Hamburg");
        Assertions.assertEquals(description, scpm.getDescription());
        Assertions.assertEquals(Optional.of("Institut für Deutsche Sprache"), scpm.getLegalOwner());
        Assertions.assertEquals(List.of("Anhören von Aufnahmeausschnitten in DGD, Archiv für Gesprochenes Deutsch, http://dgd.ids-mannheim.de, agd@ids-mannheim.de"), scpm.getLicenses());
        Assertions.assertEquals(Optional.of("Postfach 10 16 21, D-68016 Mannheim, +49 (0)621 1581 0, agd@ids-mannheim.de, DE"),scpm.getLocation());
        Assertions.assertEquals(Optional.of("17-05-2021"), scpm.getPublicationDate());
        Assertions.assertEquals(Optional.of("Hamburg Maptask Corpus (HaMaTaC)"), scpm.getResourceName());
        Assertions.assertEquals(Optional.of("Hamburg Maptask Corpus (HaMaTaC)"), scpm.getResourceTitle());
        Assertions.assertEquals(Optional.empty(), scpm.getSelfLink());
        Assertions.assertEquals(List.of("deu"), scpm.getSubjectLanguages());
        Assertions.assertEquals(Optional.of("Erstes Release in der DGD (vorher: HZSK)"),scpm.getVersion());
        TextCorpusProfileMapper tcpm = new TextCorpusProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/a00.cmdi")));
        description = new HashMap<>();
        Assertions.assertEquals(description, tcpm.getDescription());
        Assertions.assertEquals(Optional.of("Institut für Deutsche Sprache"), tcpm.getLegalOwner());
        Assertions.assertEquals(List.of("unknown, QAO-NC"), tcpm.getLicenses());
        Assertions.assertEquals(Optional.of("Postfach 10 16 21, D-68016 Mannheim\n" +
                                            "+49 (0)621 1581 0\n" +
                                            "Deutschland\n" +
                                            "DE"),tcpm.getLocation());
        Assertions.assertEquals(Optional.empty(), tcpm.getPublicationDate());
        Assertions.assertEquals(Optional.of("A00"), tcpm.getResourceName());
        Assertions.assertEquals(Optional.of("St. Galler Tagblatt 2000"), tcpm.getResourceTitle());
        Assertions.assertEquals(Optional.empty(), tcpm.getSelfLink());
        Assertions.assertEquals(List.of("deu"), tcpm.getSubjectLanguages());
        Assertions.assertEquals(Optional.empty(),tcpm.getVersion());
    }
    
    // TODO Fix test
    @Test
    public void cmdiTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException, JDOMException {
        Metadata metadata = CMDI.readCmdiMetadata(new CollectionProfileMapper(new SAXBuilder().build(ClassLoader.getSystemResourceAsStream("cmdi/DeReKo.cmdi"))));
        Metadata m2 = om.readValue(ClassLoader.getSystemResourceAsStream("DeReKo_Invenio.json"),Metadata.class);
        for (Field f : Metadata.class.getDeclaredFields()) {
            // TODO fix all these problematic fields
            if (!f.getName().equals("rights") && !f.getName().equals("contributors") && !f.getName().equals("dates") && !f.getName().equals("publisher") 
                    && !f.getName().equals("alternativeIdentifiers") && !f.getName().equals("formats") && !f.getName().equals("locations") 
                    && !f.getName().equals("fundingReferences")) {
                f.setAccessible(true);
                Assertions.assertEquals(f.get(m2), f.get(metadata),f.getName());
            }
        }
        // LOG.info(om.enable(SerializationFeature.INDENT_OUTPUT).writeValueAsString(metadata));
    }
    private static final Logger LOG = Logger.getLogger(CMDITest.class.getName());
}
