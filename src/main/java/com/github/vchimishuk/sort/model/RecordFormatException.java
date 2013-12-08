package com.github.vchimishuk.sort.model;

public class RecordFormatException extends Exception {
    private static final long serialVersionUID = 1L;
    
    public RecordFormatException() {
        
    }
    
    public RecordFormatException(String msg) {
        super(msg);
    }

    public RecordFormatException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
