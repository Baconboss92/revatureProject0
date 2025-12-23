package com.helloapplii;

import java.util.List;

// Why did that Junit tutorial have me make a interface again? Is this actually needed?
public interface calRep {
    void createFirstTable();
    void createSecondTable();
    void createThirdTable();
    void insertRow(int month, int day, String event);
    void insertDay(Day day);
    void bindDayToEvent(Day day, event eve);
    void unbindDayToEvent(Day day, event eve);
    void insertEvent(event eve);
    List<String> getEvents(int year, int month, int day);
}
