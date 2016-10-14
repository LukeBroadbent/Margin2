package com.apps.luke.margin.Utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

/**
 * Created by Luke on 12/14/2015.
 */
public class GlobalVariablesClass extends Application
{
    public static String username = "hfc2016";
    public static String password = "hfc2016";

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    //public static DBHelper getDatabaseHelper() {
    //    return dbHelper;
    // }



    public GlobalVariablesClass()
    {
        super();

        //db = new DBHelper(this.getApplicationContext());
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }


}
