package com.mak.pcr.dbentities;

public class Batch {

    public String batchCode, timings, days, faculty_id;

    public Batch(){

    }

    public Batch(String batchCode, String timings, String days){
        this.batchCode = batchCode;
        this.timings = timings;
        this.days = days;
    }
}
