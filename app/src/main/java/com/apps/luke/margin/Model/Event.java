package com.apps.luke.margin.Model;

import lombok.Data;

/**
 * Created by Luke on 11/30/2015.
 */

@Data
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
}
