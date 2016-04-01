package com.apps.luke.margin;

import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Luke on 11/30/2015.
 */
public class EventEntry_Class
{
    int Event_ID;
    int Entry_ID;
    int User_ID;
    int Entry_Margin;


    public EventEntry_Class(int event_ID, int user_ID, int entry_Margin) {
        Event_ID = event_ID;
        User_ID = user_ID;
        Entry_Margin = entry_Margin;
    }

    public EventEntry_Class()
    {

    }

    public int getEvent_ID() {
        return Event_ID;
    }

    public void setEvent_ID(int event_ID) {
        Event_ID = event_ID;
    }

    public int getEntry_ID() {
        return Entry_ID;
    }

    public void setEntry_ID(int entry_ID) {
        Entry_ID = entry_ID;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public int getEntry_Margin() {
        return Entry_Margin;
    }

    public void setEntry_Margin(int entry_Margin) {
        Entry_Margin = entry_Margin;
    }
}