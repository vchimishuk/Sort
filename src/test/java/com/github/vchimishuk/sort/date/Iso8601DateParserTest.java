package com.github.vchimishuk.sort.date;

import java.text.ParseException;

import static org.junit.Assert.*;

import org.junit.Test;

public class Iso8601DateParserTest {
    @Test
    public void parseTest() throws ParseException {
        assertEquals(1274528145000L, Iso8601DateParser.parse("2010-05-22T14:35:45"));
        assertEquals(908660735000L, Iso8601DateParser.parse("1998-10-18T00:45:35"));
        assertEquals(1648584513000L, Iso8601DateParser.parse("2022-03-29T23:08:33"));
        assertEquals(1675203208000L, Iso8601DateParser.parse("2023-02-01T00:13:28"));
        assertEquals(1415455723000L, Iso8601DateParser.parse("2014-11-08T16:08:43"));
    }
    
    @Test(expected=ParseException.class)
    public void exceptionTest() throws ParseException {
        Iso8601DateParser.parse("foo");
    }
}
