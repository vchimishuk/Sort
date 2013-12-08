package com.github.vchimishuk.sort.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Iso8601DateParser {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    public static long parse(String date) throws ParseException {
        return DATE_FORMAT.parse(date).getTime();
    }
}
