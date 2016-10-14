package com.apps.luke.margin.Model;

import android.app.usage.UsageEvents;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import lombok.Data;

/**
 * Created by Luke on 11/30/2015.
 */

@Data
public class EventEntry
{
    int Event_ID;
    int Entry_ID;
    int User_ID;
    int Entry_Margin;

    public EventEntry(int event_ID, int user_ID, int entry_Margin) {
        Event_ID = event_ID;
        User_ID = user_ID;
        Entry_Margin = entry_Margin;
    }

    public EventEntry()
    {

    }
}