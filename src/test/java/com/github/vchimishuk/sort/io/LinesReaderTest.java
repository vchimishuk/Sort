package com.github.vchimishuk.sort.io;

import java.io.IOException;
import java.io.StringReader;
import static org.junit.Assert.*;

import org.junit.Test;

public class LinesReaderTest {
    private static final String LS = String.format("%n");

    @Test
    public void readLinesTest() throws IOException {
        String input = "000" + LS
                + "111" + LS
                + "222" + LS
                + "333" + LS
                + "444" + LS
                + "555" + LS
                + "666" + LS
                + "777" + LS
                + "888" + LS
                + "999" + LS;
        LinesReader reader = new LinesReader(new StringReader(input));
        Object[] lines = new Object[4];
        
        reader.readLines(lines);
        assertEquals("000", lines[0]);
        assertEquals("111", lines[1]);
        assertEquals("222", lines[2]);
        assertEquals("333", lines[3]);
        
        reader.readLines(lines);
        assertEquals("444", lines[0]);
        assertEquals("555", lines[1]);
        assertEquals("666", lines[2]);
        assertEquals("777", lines[3]);
        
        reader.readLines(lines);
        assertEquals("888", lines[0]);
        assertEquals("999",  lines[1]);
        assertNull(lines[2]);
        assertNull(lines[3]);
        
        reader.close();
    }
}
