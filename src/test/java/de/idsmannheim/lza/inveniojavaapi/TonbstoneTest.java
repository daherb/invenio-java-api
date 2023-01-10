package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
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
public class TonbstoneTest {
    
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        //om.registerModule(new Jdk8Module());
        om.findAndRegisterModules();
    }
    
    @Test
    public void tombstoneTest() throws JsonProcessingException {
        String tombstoneStringText = "{\n" +
                "    \"reason\": \"Spam record, removed by InvenioRDM staff.\",\n" +
                "    \"category\": \"spam_manual\",\n" +
                "    \"removed_by\": {\"user\": 1},\n" +
                "    \"timestamp\": \"2020-09-01T12:02:00Z\"\n" +
                "  }"; 
        Calendar timestamp = Calendar.getInstance(TimeZone.getTimeZone("Z"));
        timestamp.set(2020,8,1,12,2,0);
        Tombstone tombstone = new Tombstone("Spam record, removed by InvenioRDM staff.",
                "spam_manual",
                new Tombstone.User(1),
                timestamp.getTime());
        Tombstone t2 = om.readValue(tombstoneStringText, Tombstone.class);
        Tombstone t3 = om.readValue(om.writeValueAsString(tombstone), Tombstone.class);
        Assertions.assertEquals(tombstone,t2);
        Assertions.assertEquals(tombstone,t3);
    }
}
