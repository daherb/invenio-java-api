/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi.deserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import de.idsmannheim.lza.inveniojavaapi.Metadata.ExtendedDateTimeFormat0;
import java.io.IOException;

/**
 *
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class ExtendedDateTimeFormat0Deserializer extends StdDeserializer<ExtendedDateTimeFormat0> {

    public ExtendedDateTimeFormat0Deserializer() {
        this(null);
    }
    public ExtendedDateTimeFormat0Deserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ExtendedDateTimeFormat0 deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String start;
        String end;
        String startYear;
        String startMonth = null;
        String startDay = null;
        String endYear = null;
        String endMonth = null;
        String endDay = null;
        if (node.asText().contains("/")) {
            start = node.asText().split("/")[0];
            if (node.asText().split("/").length > 1) {
                end = node.asText().split("/")[1];
                String[] endSplit = end.split("-");
                endYear = endSplit[0];
                if (endSplit.length > 1)
                    endMonth = endSplit[1];
                if (endSplit.length == 3) 
                    endDay = endSplit[2];
            }
            else {
                endYear = "";
            }
        }
        else 
            start = node.asText();
        String[] startSplit = start.split("-");
        startYear = startSplit[0];
        if (startSplit.length > 1 )
            startMonth = startSplit[1];
        if (startSplit.length == 3)
            startDay = startSplit[2];
        ExtendedDateTimeFormat0 date = new ExtendedDateTimeFormat0(startYear);
        if (startMonth != null)
            date.addStartMonth(startMonth);
        if (startDay != null) 
            date.addStartDay(startDay);
        if (endYear != null) 
            date.addEndYear(endYear);
        if (endMonth != null)
            date.addEndMonth(endMonth);
        if (endDay != null)
            date.addEndDay(endDay);
        return date;
    }
}
