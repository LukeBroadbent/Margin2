package com.apps.luke.margin;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Luke on 3/16/2016.
 */
public class Global_Variables extends Application {

    DatabaseHelper db;
    List<String> entries = new LinkedList<String>();

    //Popup Dialog Types
    String POPUP_TYPE_NEWUSER = "new_user";
    String POPUP_TYPE_EXISTINGUSER = "existing_user";
    String POPUP_TYPE_EDITUSER = "edit_user";


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

    public String capitalizeNames(String name)
    {
        String returnName = "";

        String[] split = name.split(" ");
        if(split.length > 1)
        {
            for(int i = 0; i < split.length; i++)
            {
                String output = split[i].substring(0, 1).toUpperCase() + split[i].substring(1);
                //.d("Output", output);
                split[i] = output;
                //Log.d("Split[i]", split[i]);
            }

            StringBuilder builder = new StringBuilder();

            for (String string : split) {
                if (builder.length() > 0) {
                    builder.append(" ");
                }
                builder.append(string);
            }

            returnName = builder.toString();
            //Log.d("ReturnName", returnName);
            return returnName;
        }

        String output = name.substring(0, 1).toUpperCase() + name.substring(1);
        //Log.d("Output", output);
        return output;
    }

    public void test()
    {
        
    }

    /*
    public List<EventEntry_Class> orderAlphabetically(List<EventEntry_Class> unOrderedList)
    {

        if (unOrderedList.size() > 0) {
            Collections.sort(unOrderedList, new Comparator<EventEntry_Class>() {
                @Override
                public int compare(final EventEntry_Class object1, final EventEntry_Class object2) {
                    User_Class u1 = db.getUser(object1.getUser_ID());
                    User_Class u2 = db.getUser(object1.getUser_ID());

                    return object1.get.compareTo(object2.getName());
                }
            } );
        }





        return alphabeticalList;
    }
    */

    public List<EventEntry_Class> orderNumerically(List<EventEntry_Class> unOrderedList)
    {
        if (unOrderedList.size() > 0) {
            Collections.sort(unOrderedList, new Comparator<EventEntry_Class>() {
                @Override
                public int compare(final EventEntry_Class object1, final EventEntry_Class object2) {

                    if (object1.getEntry_Margin() ==
                            object2.getEntry_Margin())
                    {
                        return 0;
                    }
                    else if (object1.getEntry_Margin() <
                            object2.getEntry_Margin())
                    {
                        return -1;
                    }
                    return 1;
                }
            } );
        }

        return unOrderedList;
    }



}
