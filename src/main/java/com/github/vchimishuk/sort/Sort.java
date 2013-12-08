package com.github.vchimishuk.sort;

import java.io.IOException;

import com.github.vchimishuk.sort.model.RecordFormatException;

public class Sort {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("USAGE: java -jar sort.java INPUT_FILE OUTPUT_FILE");
            System.exit(1);
        }
        
        Sorter sorter = new Sorter(args[0], args[1]);
        try {
            sorter.sort();
        } catch (IOException | RecordFormatException e) {
            System.err.println("ERROR: " + e.getMessage());
            System.exit(2);
        }
    }
}
