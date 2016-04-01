package com.apps.luke.margin;

import android.app.Application;
import android.content.Context;
import android.telephony.SmsManager;


/**
 * Created by Luke on 3/24/2016.
 */
public class SMS_Class extends Application
{
    SmsManager smsManager;
    Context context;

    public SMS_Class(Context c)
    {
        context = c;
    }

    public void onCreate()
    {
        super.onCreate();

        try
        {
            smsManager =  SmsManager.getDefault();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public SmsManager getSmsManager()
    {
        return smsManager;
    }


}
