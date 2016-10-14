package com.apps.luke.margin.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.apps.luke.margin.Model.Event;
import com.apps.luke.margin.Model.EventEntry;
import com.apps.luke.margin.Model.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by Luke on 12/1/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DBHelper";

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "FundraisingDatabase";

    // Table Names
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_EVENTENTRY = "entry";
    private static final String TABLE_USERS = "users";

    // Common column names
    private static final String EVENT_ID = "event_id";
    private static final String USER_ID = "user_id";

    // EVENTS Table - column names
    private static final String EVENT_TYPE= "event_type";
    private static final String EVENT_NAME = "event_name";
    private static final String EVENT_STATUS= "event_status";
    private static final String EVENT_FINAL_MARGIN = "event_final_margin";

    // EVENTENTRY Table - column names
    private static final String ENTRY_ID = "entry_id";
    private static final String ENTRY_MARGIN = "entry_margin";

    // USERS Table - column names
    private static final String USER_NAME = "user_name";
    private static final String USER_PHONE = "user_phone";

    // Table Create Statements
    // Todo table create statement
    private static final String CREATE_TABLE_EVENTS = "CREATE TABLE "
            + TABLE_EVENTS + "("
            + EVENT_ID + " INTEGER PRIMARY KEY,"
            + EVENT_TYPE + " TEXT,"
            + EVENT_NAME + " TEXT,"
            + EVENT_STATUS + " TEXT,"
            + EVENT_FINAL_MARGIN + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_ENTRY = "CREATE TABLE "
            + TABLE_EVENTENTRY + "("
            + EVENT_ID + " INTEGER,"
            + ENTRY_ID + " INTEGER PRIMARY KEY,"
            + USER_ID + " INTEGER,"
            + ENTRY_MARGIN + " INTEGER"
            + ")";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE "
            + TABLE_USERS + "("
            + USER_ID + " INTEGER PRIMARY KEY,"
            + USER_NAME + " TEXT,"
            + USER_PHONE + " TEXT"
            + ")";


    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("Table Users",CREATE_TABLE_USERS);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_EVENTS);
        db.execSQL(CREATE_TABLE_ENTRY);
        db.execSQL(CREATE_TABLE_USERS);

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTENTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // create new tables
        onCreate(db);
    }

    public long createEvent(Event event) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(EVENT_ID, event.getEvent_ID());
        values.put(EVENT_TYPE, event.getEvent_Type());
        values.put(EVENT_NAME, event.getEvent_Name());
        values.put(EVENT_STATUS, event.getEvent_Status());
        values.put(EVENT_FINAL_MARGIN, event.getEvent_Final_Margin());

        // insert row
        long event_id = db.insert(TABLE_EVENTS, null, values);

        return event_id;
    }

    public long createUser(User u)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_ID, u.getUser_ID());
        values.put(USER_NAME, u.getUser_Name());
        values.put(USER_PHONE, u.getUser_Phone());

        // insert row
        long user_insert = db.insert(TABLE_USERS, null, values);

        return user_insert;
    }




    public void createEventEntries(List<EventEntry> eventEntries) {
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i < eventEntries.size(); i++)
        {
            ContentValues values = new ContentValues();
            values.put(EVENT_ID, eventEntries.get(i).getEvent_ID());
            values.put(ENTRY_ID, getNextEventEntryId());
            values.put(USER_ID, eventEntries.get(i).getUser_ID());
            values.put(ENTRY_MARGIN, eventEntries.get(i).getEntry_Margin());

            // insert row

            Log.d("values", values.toString());
            db.insert(TABLE_EVENTENTRY, null, values);
        }


        //return event_id;
    }


    //Fetching
    public List<Event> getEventsBasedOnStatus(String event_status)
    {
        Log.d("Event Status",event_status);

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS + " WHERE "
                + EVENT_STATUS + " = '" + event_status + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<Event> eList = new ArrayList<Event>();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    Log.d("Found Some", " With A Status");

                    Event e = new Event();
                    e.setEvent_ID(c.getInt(0));
                    e.setEvent_Type((c.getString(1)));
                    e.setEvent_Name((c.getString(2)));
                    e.setEvent_Status((c.getString(3)));
                    e.setEvent_Final_Margin((c.getInt(4)));
                    eList.add(e);
                } while (c.moveToNext());
            }

        }

        return eList;

    }

    public List<User> searchUser(String input)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        List<User> tempList = new LinkedList<User>();
        //String Contains_Direct =
        String selectQuery = "";

        if (input.length() != 0)
        {
            input = "%" + input + "%";
        }

        selectQuery = " select * from " + TABLE_USERS + " where user_name like  '" + input + "' or user_phone like '" + input + "'";


        Cursor c = db.rawQuery(selectQuery, null);
        User user = null;
        if (c.moveToFirst())
        {
            do
            {
                user = new User();
                user.setUser_ID(c.getInt(0));
                user.setUser_Name(c.getString(1));
                user.setUser_Phone(c.getString(2));

                tempList.add(user);
            }
            while(c.moveToNext());
        }
        return tempList;
    }

    public User getUser(int user_id)
    {
        User returnUser = new User();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS + " WHERE "
                + USER_ID + " = '" + user_id + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Log.d("Move to first", Boolean.toString(c.moveToFirst()));

        if( c != null && c.moveToFirst() )
        {
            returnUser.setUser_ID(c.getInt(0));
            returnUser.setUser_Name(c.getString(1));
            returnUser.setUser_Phone(c.getString(2));
        }

        c.close();

        return returnUser;
    }

    public EventEntry getWinner(int event_id, int eventScore)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTENTRY + " WHERE "
                + ENTRY_MARGIN + " = '" + eventScore + "' AND " + EVENT_ID + " = '" + event_id + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        Log.d("Move to first", Boolean.toString(c.moveToFirst()));

        EventEntry winnerEntry = new EventEntry();

        if( c != null && c.moveToFirst() )
        {
            Log.d("Found Entry Margin","FOUND FOUND FOUND");

            winnerEntry.setEvent_ID(c.getInt(0));
            winnerEntry.setEntry_ID(c.getInt(1));
            winnerEntry.setUser_ID(c.getInt(2));
            winnerEntry.setEntry_Margin(c.getInt(3));

        }

        if(!c.moveToFirst())
        {
            winnerEntry = null;
        }
        return winnerEntry;

    }

    public List<Integer> getAllEventMargins(int event_Id)
    {
        List<Integer> selectedMargins = new LinkedList<Integer>();

        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTENTRY + " WHERE "
                + EVENT_ID + " = '" + event_Id + "'";

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    selectedMargins.add(c.getInt(3));
                } while (c.moveToNext());
            }
            else
            {
                selectedMargins = null;
            }
        }
        return selectedMargins;
    }


    public boolean finishEvent(int event_Id, int event_finalScore)
    {
        String status =  "C";
        try
        {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(EVENT_STATUS,status); //These Fields should be your String values of actual column names
            cv.put(EVENT_FINAL_MARGIN,event_finalScore);
            db.update(TABLE_EVENTS, cv, EVENT_ID + "=" + event_Id, null);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public void updateUserDetails(User u)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME,u.getUser_Name());                                 //These Fields should be your String values of actual column names
        cv.put(USER_PHONE,u.getUser_Phone());
        db.update(TABLE_USERS, cv, USER_ID + "=" + u.getUser_ID(), null);
    }

    public void updateAllUserNames(List<User> u)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();
        for(int i = 0; i < u.size(); i++)
        {
            cv.put(USER_NAME,u.get(i).getUser_Name());                                 //These Fields should be your String values of actual column names
            cv.put(USER_PHONE,u.get(i).getUser_Phone());
            db.update(TABLE_USERS, cv, USER_ID + "=" + u.get(i).getUser_ID(), null);
        }

    }

    public List<EventEntry> getEventEntriesBasedOnEvent(int event_ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTENTRY + " WHERE "
                + EVENT_ID + " = '" + event_ID + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<EventEntry> eList = new ArrayList<EventEntry>();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    //Log.d("Found Some", " With A Status");

                    EventEntry e = new EventEntry();
                    e.setEvent_ID(c.getInt(0));
                    e.setEvent_ID(c.getInt(1));
                    e.setUser_ID(c.getInt(2));
                    e.setEntry_Margin(c.getInt(3));
                    eList.add(e);
                } while (c.moveToNext());
            }

        }

        return eList;
    }

    public List<EventEntry> getEntriesOfEvent(int event_id)
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTENTRY + " WHERE "
                + EVENT_ID + " = '" + event_id + "'";

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<EventEntry> eList = new ArrayList<EventEntry>();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {

                    EventEntry e = new EventEntry();
                    e.setEvent_ID(c.getInt(0));
                    e.setEntry_ID(c.getInt(1));
                    e.setUser_ID(c.getInt(2));
                    e.setEntry_Margin(c.getInt(3));
                    eList.add(e);
                } while (c.moveToNext());
            }
        }

        return eList;

    }

    public List<Event> getAllEvents()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_EVENTS;//

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<Event> eList = new ArrayList<Event>();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    //Log.d("Found Some", " With A Status");

                    Event e = new Event();
                    e.setEvent_ID(c.getInt(0));
                    e.setEvent_Type((c.getString(1)));
                    e.setEvent_Name((c.getString(2)));
                    e.setEvent_Status((c.getString(3)));
                    e.setEvent_Final_Margin(c.getInt(4));
                    eList.add(e);
                } while (c.moveToNext());
            }

        }

        return eList;
    }

    public List<User> getAllUsers()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_USERS;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        List<User> eList = new ArrayList<User>();

        if (c != null)
        {
            if (c.moveToFirst()) {
                do {
                    Log.d("Found Some", " With A Status");

                    User u = new User();
                    u.setUser_ID(c.getInt(0));
                    u.setUser_Name(c.getString(1));
                    u.setUser_Phone(c.getString(2));
                    eList.add(u);
                } while (c.moveToNext());
            }

        }

        return eList;
    }

    public int getNextEventId()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT MAX(" + EVENT_ID + ")FROM " + TABLE_EVENTS;

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        Event e = new Event();
        e.setEvent_ID(c.getInt(0));


        return e.getEvent_ID() + 1;

    }

    public int getNextEventEntryId()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT MAX(" + ENTRY_ID + ")FROM " + TABLE_EVENTENTRY;

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        EventEntry e = new EventEntry();
        e.setEntry_ID(c.getInt(0));

        Log.d("Next Entry Id", Integer.toString(e.getEntry_ID() + 1));

        return e.getEntry_ID() + 1;

    }

    public int getNextUserId()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT MAX(" + USER_ID + ")FROM " + TABLE_USERS;

        Cursor c = db.rawQuery(query, null);

        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.setUser_ID(c.getInt(0));


        return u.getUser_ID() + 1;
    }



}
