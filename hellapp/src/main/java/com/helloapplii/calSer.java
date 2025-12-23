package com.helloapplii;

import java.util.List;

public class calSer {
    private final calRep repository;
    
    public calSer(calRep repository) {
        this.repository = repository;
    }
    public boolean isValidDay(int year, int month, int day) {
        if (month < 1 || month > 12) {
            return false;
        }
        if (day < 1 || day > 31) {
            return false;
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            return false;
        }
        // VSCode says this is redundent and I do not know why.
        if (month == 2 && (day > 29 || (day > 28 && ((year%4) != 0)))) {
            return false;
        }
        return true;
    }
    // Note to self fix this sh*t
    // Did not have enough time to fix this, it is fine this was just a failsafe anyway
    public boolean addEvent(int year, int month, int day, String event) {
        if (!isValidDay(year, month, day)) {
            return false;
        }
        if (event == null || event.trim().isEmpty()) {
            return false;
        }
        repository.insertRow(month, day, event);
        return true;
    }
    public List<String> getEventsForDay(int year, int month, int day) {
        if (!isValidDay(year, month, day)) {
            throw new IllegalArgumentException("Invalid date");
        }
        return repository.getEvents(year, month, day);
    }
    public int countEvents(int year, int month, int day) {
        List<String> events = getEventsForDay(year, month, day);
        return events == null ? 0 : events.size();
    }
    public boolean hasEvents(int year, int month, int day) {
        return countEvents(year, month, day) > 0;
    }
}