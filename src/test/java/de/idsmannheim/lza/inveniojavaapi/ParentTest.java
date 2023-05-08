package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
 * Tests for Parent class
 */
@SpringBootTest
public class ParentTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void parentTest() throws JsonProcessingException {
        // Taken from https://inveniordm.docs.cern.ch/reference/metadata/#parent
        String accessText = "{\n" +
                "    \"id\": \"fghij-12345\",\n" +
                "    \"access\": {\n" +
                "      \"owned_by\": [\n" +
                "        {\n" +
                "          \"user\": 2\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  }"; 
        Parent parent = new Parent("fghij-12345", 
                new Parent.Access(new ArrayList<>(Collections.singletonList(new Parent.Owner(2)))));
        Parent p2 = om.readValue(accessText, Parent.class);
        Parent p3 = om.readValue(om.writeValueAsString(parent), Parent.class);
        Assertions.assertEquals(parent,p2);
        Assertions.assertEquals(parent,p3);
    }
}
