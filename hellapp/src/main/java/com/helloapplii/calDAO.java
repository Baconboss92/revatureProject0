package com.helloapplii;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

// I really should of listened to you Brian and done this and the database first
// I have fully reworked this three times now and it is still just a mess.
public class calDAO implements calRep {
    final private Connection conn;
    public calDAO(Connection connection){
        this.conn = connection;
    }
    @Override
    public void createFirstTable(){
        try {
            String query="CREATE TABLE calender(id SERIAL PRIMARY KEY,year int NOT NULL,month int NOT NULL,day int NOT NULL, CONSTRAINT unique_date UNIQUE(year, month, day))";
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
        }
    }
    @Override
    public void createSecondTable(){
        try {
            String query="CREATE TABLE eventTracker(id SERIAL PRIMARY KEY,eventName varchar(255) UNIQUE,eventDesc text)";
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
        }
    }
    @Override
    public void createThirdTable(){
        try {
            String query="CREATE TABLE dayToEvents (day_id int REFERENCES calender(id),event_id int REFERENCES eventTracker(id),PRIMARY KEY (day_id, event_id))";
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException e) {
        }
    }
    @Override
    public void insertRow(int month,int day,String event){
        try{
            String query=String.format("insert into calender(month,day,event) values('%s','%s','%s');",month,day,event);
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    // There is no safe guard for a day or event being inputted twice, which is bad
    @Override
    public void insertDay(Day day){
        try{
            String query=String.format("insert into calender(year,month,day) values('%s','%s','%s');",day.getYear(),day.getMonth(),day.getDay());
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
            query=String.format("select id from calender where year = '%s' and month = '%s' and day = '%s';",day.getYear(),day.getMonth(),day.getDay());
            stmt=conn.createStatement();
            ResultSet tempRS = stmt.executeQuery(query);
            while(tempRS.next()){
                day.setID(tempRS.getInt("id"));
            }
        }catch (SQLException e){
            try {
                String query=String.format("select id from calender where year = '%s' and month = '%s' and day = '%s';",day.getYear(),day.getMonth(),day.getDay());
                Statement stmt=conn.createStatement();
                ResultSet tempRS = stmt.executeQuery(query);
                while(tempRS.next()){
                    day.setID(tempRS.getInt("id"));
                }
            } catch (SQLException r) {
                System.out.println(e.getMessage());
                System.out.println(r.getMessage());
            }
        }

    }
    @Override
    public void bindDayToEvent(Day day, event eve){
        try{
            String query=String.format("insert into dayToEvents(day_id,event_id) values('%s','%s');",day.getID(),eve.getID());
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    @Override
    public void insertEvent(event eve){
        try{
            String query=String.format("insert into eventTracker(eventName,eventDesc) values('%s','%s');",eve.getEventName(),eve.getEventDesc());
            Statement stmt=conn.createStatement();
            stmt.executeUpdate(query);
            query=String.format("select id from eventTracker where eventName = '%s' and eventDesc = '%s';",eve.getEventName(),eve.getEventDesc());
            stmt=conn.createStatement();
            ResultSet tempRS = stmt.executeQuery(query);
            while(tempRS.next()){
                eve.setID(tempRS.getInt("id"));
            }
        }catch (SQLException e){
            try {
                String query=String.format("select id from eventTracker where eventName = '%s' and eventDesc = '%s';",eve.getEventName(),eve.getEventDesc());
                Statement stmt=conn.createStatement();
                ResultSet tempRS = stmt.executeQuery(query);
                while(tempRS.next()){
                    eve.setID(tempRS.getInt("id"));
                }
            } catch (SQLException r) {
                System.out.println(e.getMessage());
                System.out.println(r.getMessage());
            }
        }

    }
    @Override
    public List<String> getEvents(int year, int month, int day){
        List<String> tempA = new ArrayList<>();
        try{
            String query=String.format("SELECT eventTracker.eventName FROM eventTracker INNER JOIN dayToEvents ON eventTracker.id = dayToEvents.event_id INNER JOIN calender ON dayToEvents.day_id = calender.id WHERE calender.year = '%s' AND calender.month = '%s' AND calender.day = '%s'",year,month,day);
            Statement stmt=conn.createStatement();
            ResultSet tempRS = stmt.executeQuery(query);
            while(tempRS.next()){
                tempA.add(tempRS.getString("eventName"));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return tempA;
    }
}