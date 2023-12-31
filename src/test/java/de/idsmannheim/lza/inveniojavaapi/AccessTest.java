package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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
 * Tests for Access class
 */
@SpringBootTest
public class AccessTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void accessTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#access
        String accessText = "{\n" +
                "    \"record\": \"public\",\n" +
                "    \"files\": \"public\",\n" +
                "    \"embargo\": {\n" +
                "      \"active\": false\n" +
                "    }\n" +
                "  }"; 
        Access access = new Access(Access.AccessType.Public, Access.AccessType.Public).setEmbargo(new Access.Embargo());
        Access a2 = om.readValue(accessText, Access.class);
        Access a3 = om.readValue(om.writeValueAsString(access), Access.class);
        Assertions.assertEquals(access,a2);
        Assertions.assertEquals(access,a3);
    }
}
