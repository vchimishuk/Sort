package com.github.vchimishuk.sort.model;

import java.text.ParseException;

import com.github.vchimishuk.sort.date.Iso8601DateParser;

public class Record implements Comparable<Record> {
    private static final char FIELDS_SEPARATOR = ' ';
    
    private long time;
    
    private String data;
    
    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + (int) (time ^ (time >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Record other = (Record) obj;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        if (time != other.time)
            return false;
        return true;
    }
    
    @Override
    public String toString() {
        return data;
    }
    
    @Override
    public int compareTo(Record other) {
        return Long.compare(time, other.getTime());
    }
    
    public static Record newInstance(String data) throws RecordFormatException {
        int i = data.indexOf(FIELDS_SEPARATOR);
        
        if (i == -1) {
            throw new RecordFormatException("Invalid format. Three comma separated fields expected, only one found.");
        }
        
        int j = data.indexOf(FIELDS_SEPARATOR, i + 1);
        
        if (j == -1) {
            throw new RecordFormatException("Invalid format. Three comma separated fields expected, only two found");
        }
        
        String date = data.substring(i, j);
        
        Record record = new Record();
        record.setData(data);

        try {
            record.setTime(Iso8601DateParser.parse(date));
        } catch (ParseException e) {
            throw new RecordFormatException(String.format("Illegal time format '%s', ISO8601 expected. %s",
                    date, e.getMessage()), e);
        }
        
        return record;
    }
}
