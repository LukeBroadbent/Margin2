package com.apps.luke.margin.Utils;

import android.app.Application;
import android.content.Context;
import android.telephony.SmsManager;


/**
 * Created by Luke on 3/24/2016.
 */
public class Sms extends Application
{
    SmsManager smsManager;
    Context context;

    public Sms(Context c)
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
