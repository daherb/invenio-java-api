/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.idsmannheim.lza.inveniojavaapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SimpleDateFormat used for parsing and formatting all dates in the API
 * @author Herbert Lange <lange@ids-mannheim.de>
 */
public class DateFormater{

    private static DateFormater INSTANCE;
    
    private final List<SimpleDateFormat> dateFormat = new ArrayList();
    
    private DateFormater() {
        
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSXXX"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSSSSXXX"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ssXXX"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss.SSSSSS"));
        dateFormat.add(new SimpleDateFormat("yyyy-MM-dd' 'HH:mm:ss"));
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
        for (SimpleDateFormat formater : dateFormat) {
            try {
                return formater.parse(s);
            }
            catch (ParseException e) {
                // Do nothing, try the next format
            }
        }
        throw new ParseException("Unparsable date: "+ s, 0);
    }
    
    /**
     * Same as SimpleDateFormater.format for the first format in the list of possible formats
     * @param d the Date to be formatted
     * @return the date formatted as string
     */
    public String format(Date d) {
        return dateFormat.get(0).format(d);
    }
}
