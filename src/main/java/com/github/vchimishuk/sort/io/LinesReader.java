package com.github.vchimishuk.sort.io;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;

public class LinesReader implements Closeable {
    private BufferedReader reader;
    
    public LinesReader(Reader reader) {
        this.reader = new BufferedReader(reader);
    }
    
    public LinesReader(BufferedReader reader) throws FileNotFoundException {
        this.reader = reader;
    }
    
    /**
     * Reads maximum lines.length lines from file and fill lines
     * array with result (strings). If there are no lines.length lines lines array
     * will contain nulls at the end of array.
     * 
     * @param lines
     * @throws IOException
     */
    public void readLines(Object[] lines) throws IOException {
        for (int i = 0; i < lines.length; i++) {
            String line = reader.readLine();
            
            if (line == null) {
                Arrays.fill(lines, i, lines.length, null);
                break;
            }
            
            lines[i] = line;
        }
    }
    
    @Override
    public void close() throws IOException {
        reader.close();
    }
}
