package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 * 
 * Tests for ExternalPid class
 */
@SpringBootTest
public class ExternalPidTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void externalPidTest() throws JsonProcessingException {
        String externalPidsText = "{\n" +
                "    \"doi\": {\n" +
                "      \"identifier\": \"10.1234/rdm.5678\",\n" +
                "      \"provider\": \"datacite\",\n" +
                "      \"client\": \"datacite\"\n" +
                "    },\n" +
                "    \"concept-doi\": {\n" +
                "      \"identifier\": \"10.1234/rdm.5678\",\n" +
                "      \"provider\": \"datacite\",\n" +
                "      \"client\": \"datacite\"\n" +
                "    }\n" +
                "  }"; 
        HashMap<String,ExternalPid> pids = new HashMap<>();
        pids.put("doi", new ExternalPid("10.1234/rdm.5678", "datacite").setClient("datacite"));
        pids.put("concept-doi", new ExternalPid("10.1234/rdm.5678", "datacite").setClient("datacite"));
        HashMap<String,ExternalPid> p2 = om.readerForMapOf(ExternalPid.class).readValue(externalPidsText);
        HashMap<String,ExternalPid> p3 = om.readerForMapOf(ExternalPid.class).readValue(om.writeValueAsString(pids));
        Assertions.assertEquals(pids,p2);
        Assertions.assertEquals(pids,p3);
    }
}
