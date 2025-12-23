package com.helloapplii;

public class Day {
    private int id;
    final private int year;
    final private int month;
    final private int day;
    public Day(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public int getYear(){
        return year;
    }
    public int getMonth(){
        return month;
    }
    public int getDay(){
        return day;
    }
}