package com.github.vchimishuk.sort.utils;

import java.io.File;

public class FileUtils {
    /**
     * Delete file or directory. Non empty directory will be deleted
     * recursively.
     * 
     * @param file File to delete.
     */
    public static void delete(File file) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            
            if (files != null) {
                for (File f : files) {
                    if (f.isDirectory()) {
                        delete(f);
                    } else {
                        f.delete();
                    }
                }
            }
        }
        
        file.delete();
    }
}
