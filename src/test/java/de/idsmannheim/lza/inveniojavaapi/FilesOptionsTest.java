/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class FilesOptionsTest {
    static ObjectMapper om = new ObjectMapper();
    
    @BeforeAll
    static void init() throws IOException {
        om.findAndRegisterModules();
    }
    
    @Test
    public void filesOptionsTest() throws JsonProcessingException, IOException, IllegalArgumentException, IllegalAccessException {
        String filesOptionsText = "{\n" +
            "    \"default_preview\": \"data-0-0-metadata.cmdi\",\n" +
            "    \"enabled\": true,\n" +
            "    \"order\": []\n" +
            "  }"; 
        FilesOptions filesOptions = new FilesOptions(true)
                .addOrder(new ArrayList<>())
                .setDefaultPreview("data-0-0-metadata.cmdi");
        FilesOptions fo2 = om.readValue(filesOptionsText, FilesOptions.class);
        FilesOptions fo3 = om.readValue(om.writeValueAsString(filesOptions), FilesOptions.class);
        Assertions.assertEquals(filesOptions,fo2);
        Assertions.assertEquals(filesOptions,fo3);
    }
}
