package com.example.sf.backend.entities;

import java.time.LocalDate;
import java.time.LocalTime;
/**
 * Created by PascalRoose on 11-Jan-18.
 */

public class Afspraak

{
    private int calID;
    private LocalDate date;
    private LocalTime time;
    private Baxter baxter;

    public Afspraak(int calID, LocalDate date, LocalTime time, Baxter baxter) {
        this.calID = calID;
        this.date = date;
        this.time = time;
        this.baxter = baxter;
    }
    public void print() {
        System.out.println("- calID: " + calID + ", date: " + date.toString() + ", time: " + time.toString());
        baxter.print();
    }
    public int getCalID() {
        return calID;
    }
    public LocalDate getDate() {
        return date;
    }
    public LocalTime getTime() {
        return time;
    }
    public Baxter getBaxter() {
        return baxter;
    }

}
