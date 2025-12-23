package com.helloapplii;

public class event {
    private int id;
    final private String eventName;
    final private String eventDesc;
    public event(String eventName, String eventDesc) {
        this.eventName = eventName;
        this.eventDesc = eventDesc;
    }
    public void setID(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public String getEventName(){
        return eventName;
    }
    public String getEventDesc(){
        return eventDesc;
    }
}