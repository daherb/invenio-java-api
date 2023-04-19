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
        String startMonth = "";
        String startDay = "";
        String endYear = "";
        String endMonth = "";
        String endDay = "";
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
        if (!startMonth.isBlank())
            date.addStartMonth(startMonth);
        if (!startDay.isBlank()) 
            date.addStartDay(startDay);
        if (!endYear.isBlank()) 
            date.addEndYear(endYear);
        if (!endMonth.isBlank())
            date.addEndMonth(endMonth);
        if (!endDay.isBlank())
            date.addEndDay(endDay);
        return date;
    }
}
