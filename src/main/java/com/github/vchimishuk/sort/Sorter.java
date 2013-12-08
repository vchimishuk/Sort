package com.github.vchimishuk.sort;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;

import com.github.vchimishuk.sort.io.LinesReader;
import com.github.vchimishuk.sort.model.Record;
import com.github.vchimishuk.sort.model.RecordFormatException;
import com.github.vchimishuk.sort.utils.FileUtils;

public class Sorter {
    private static final int CHUNK_SIZE = 500000;
    
    private static final String CACHE_DIR = ".sortCache";
    
    private static final String LINES_SEPARATOR = String.format("%n");
    
    private File cacheDir;

    private String inputFilename;
    
    private String outputFilename;
    
    public Sorter(String inputFilename, String outputFilename) {
        this.inputFilename = inputFilename;
        this.outputFilename = outputFilename;
        
        cacheDir = new File(new File(outputFilename).getParent(), CACHE_DIR);
    }
    
    public void sort() throws IOException, RecordFormatException {
        if (cacheDir.exists()) {
            FileUtils.delete(cacheDir);
        }
        cacheDir.mkdir();
        
        try {
            LinesReader input = new LinesReader(new FileReader(inputFilename));
            
            try {
                split(input);
            } finally {
                try {
                    input.close();
                } catch (IOException e) {
                    // Ignore close errors.
                }
            }
            
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFilename));
            
            try {
                merge(out);
            } finally {
                try {
                    out.close();
                } catch (IOException e) {
                    // Ignore.
                }
            }
        } finally {
            FileUtils.delete(cacheDir);
        }
    }
    
    /** 
     * Split source file to the sorted chunk files.
     * 
     * @param reader
     */
    private void split(LinesReader reader) throws IOException, RecordFormatException {
        int n = 0;
        // Use Object[] here to reduce memory coping.
        // Some cells can be null.
        Object[] lines = new Object[CHUNK_SIZE];
        int nullIndex; // First null cell index.
        
        do {
            reader.readLines(lines);
            nullIndex = 0;
            
            for (int i = 0; i < lines.length; i++) {
                if (lines[i] == null) {
                    break;
                }
                
                lines[i] = Record.newInstance((String) lines[i]);
                nullIndex = i + 1;
            }
            
            if (nullIndex == 0) {
                break;
            }
            
            Arrays.sort(lines, 0, nullIndex);

            Writer writer = new BufferedWriter(new FileWriter(new File(cacheDir, "0_" + n)));
            
            try {
                for (Object o : lines) {
                    if (o == null) {
                        break;
                    }
                    
                    writer.write(((Record) o).getData());
                    writer.write(LINES_SEPARATOR);
                }
            } finally {
                try {
                    writer.close();
                } catch (IOException e) {
                    // Ignore close errors.
                }
            }
            
            n++;
        } while (nullIndex != 0);
    }
    
    private void merge(BufferedWriter out) throws RecordFormatException, IOException {
        File[] files = cacheDir.listFiles();
        BufferedReader[] parts = new BufferedReader[files.length];
        Record[] heads = new Record[files.length];
        
        try {
            for (int i = 0; i < files.length; i++) {
                parts[i] = new BufferedReader(new FileReader(files[i]));
                heads[i] = Record.newInstance(parts[i].readLine());
            }
            
            int activeHeads = heads.length;

            // TODO: Optimize merge when only one head left.
            while (activeHeads > 0) {
                int minHead = min(heads);
                
                out.write(heads[minHead].getData());
                out.write(LINES_SEPARATOR);
                
                String line = parts[minHead].readLine();
                
                if (line != null) {
                    heads[minHead] = Record.newInstance(line);
                } else {
                    heads[minHead] = null;
                    activeHeads--;
                }
            }
        } finally {
            for (BufferedReader r : parts) {
                if (r != null) {
                    try {
                        r.close();
                    } catch (IOException e) {
                        // Ignore.
                    }
                }
            }
        }
    }
    
    private int min(Record[] records) {
        int min = -1;
        
        for (int i = 0; i < records.length; i++) {
            if (records[i] != null) {
                if (min == -1 || records[i].getTime() < records[min].getTime()) {
                    min = i;
                }
            }
        }
        
        return min;
    }
}
