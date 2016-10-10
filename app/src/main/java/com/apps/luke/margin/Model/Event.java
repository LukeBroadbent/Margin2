package com.apps.luke.margin.Model;

/**
 * Created by Luke on 11/30/2015.
 */
public class Event
{
    int Event_ID;
    String Event_Type;
    String Event_Name;
    int Event_Final_Margin;
    String Event_Status;

    public Event(int event_ID, String event_Type, String event_Name, int final_margin, String event_Active) {
        Event_ID = event_ID;
        Event_Type = event_Type;
        Event_Name = event_Name;
        Event_Final_Margin = final_margin;
        Event_Status = event_Active;
    }

    public Event() {

    }


    public String getEvent_Type() {
        return Event_Type;
    }

    public String getEvent_Name() {
        return Event_Name;
    }

    public int getEvent_Final_Margin() {
        return Event_Final_Margin;
    }

    public String getEvent_Status() {
        return Event_Status;
    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public void setEvent_ID(int event_ID) {
        Event_ID = event_ID;
    }

    public void setEvent_Type(String event_Type) {
        Event_Type = event_Type;
    }

    public void setEvent_Name(String event_Name) {
        Event_Name = event_Name;
    }

    public void setEvent_Final_Margin(int event_final_margin) {
        Event_Final_Margin = event_final_margin;
    }

    public void setEvent_Status(String event_Status) {
        Event_Status = event_Status;
    }

}
