package com.apps.luke.margin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Luke on 3/16/2016.
 */
public class Global_Variables extends Application {

    DatabaseHelper db;
    List<String> entries = new LinkedList<String>();

    public void onCreate()
    {
        super.onCreate();

        // Initialize the singletons so their instances
        // are bound to the application process.
        createDatabase(getApplicationContext());
        fillEntries();
    }

    public void createDatabase(Context c)
    {
        db = new DatabaseHelper(c);
    }

    public void setDb(DatabaseHelper dbhelper) {
        this.db = dbhelper;
    }

    public DatabaseHelper getDatabase()
    {
        return db;
    }

    public void fillEntries()
    {
        entries.add("1");
        entries.add("2");
        entries.add("3");
        entries.add("4");
        entries.add("5");
        entries.add("6");
        entries.add("7");
        entries.add("8");
        entries.add("9");
        entries.add("10");
    }

    public List<String> getEntries()
    {
        return entries;
    }

    public List<Integer> convertStringListToIntList(List<String> sEntries)
    {
        List<Integer> entries = new LinkedList<Integer>();

        Log.d("sEntries Size", Integer.toString(sEntries.size()));

        for(String entry : sEntries)
        {
            entries.add(Integer.parseInt(entry));
        }

        return entries;
    }

    public List<Integer> removeSelectedMargins(List<Integer> marginList, List<Integer> selectedMargins) {
        List<Integer> finalMarginsList = new LinkedList<Integer>();

        if (selectedMargins != null)
        {
            for(int i = 0; i < selectedMargins.size(); i++)
            {
                Log.d("Current Selected Margin", Integer.toString(selectedMargins.get(i)));

                for(int x = 0; x < marginList.size(); x++)
                {
                    Log.d("Current Margin List", Integer.toString(marginList.get(x)));

                    if(selectedMargins.get(i) == marginList.get(x))
                    {
                        Log.d("Equal","Equal");
                        marginList.remove(x);
                    }

                    //if(selectedMargins.get(i) != marginList.get(x))
                    //{
                    //    Log.d("Margins","Not Equal add to list");
                    //    finalMarginsList.add(marginList.get(x));
                    //}
                   // Log.d("Margins", "Equal");
                }

            }

            Log.d("marginList Size", "Size should be " + Integer.toString(10 - selectedMargins.size()));
            Log.d("marginList Size", "Size is " + Integer.toString(marginList.size()));

            return marginList;

        }

        return marginList;
    }

}