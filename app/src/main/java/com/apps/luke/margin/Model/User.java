package com.apps.luke.margin.Model;

import lombok.Data;

/**
 * Created by Luke on 12/16/2015.
 */

@Data
public class User
{
    int User_ID;
    String User_Name;
    String User_Phone;

    public User(int user_ID, String user_Name, String user_Phone) {
        User_ID = user_ID;
        User_Name = user_Name;
        User_Phone = user_Phone;
    }

    public User()
    {

    }

    public String toString()
    {
        String s = User_Name + " " + User_Phone;
        return s;
    }
}
