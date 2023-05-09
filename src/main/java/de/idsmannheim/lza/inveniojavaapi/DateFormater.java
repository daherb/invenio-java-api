/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleDateFormat used for parsing and formatting all dates in the API
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class DateFormater{

    private static DateFormater INSTANCE;
    
    private final SimpleDateFormat dateFormat;
    
    private DateFormater() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX");
    }
    
    /**
     * Get singleton instance
     * @return the date format object
     */
    public static DateFormater getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DateFormater();
        return INSTANCE;
    }
    
    /**
     * Same as SimpleDateFormater.parse
     * @param s the date to be parsed as String
     * @return the resulting Date object
     * @throws ParseException 
     */
    public Date parse(String s) throws ParseException {
        try {
            return dateFormat.parse(s);
        }
        catch (ParseException e) {
            // Try to fix the date
            return dateFormat.parse(s.replace(' ', 'T') + "+00:00");
        }
    }
    
    /**
     * Same as SimpleDateFormater.format
     * @param d the Date to be formatted
     * @return the date formatted as string
     */
    public String format(Date d) {
        return dateFormat.format(d);
    }
}
