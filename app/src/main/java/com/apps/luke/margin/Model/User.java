package com.apps.luke.margin.Model;

/**
 * Created by Luke on 12/16/2015.
 */
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

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int user_ID) {
        User_ID = user_ID;
    }

    public String getUser_Name() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        User_Name = user_Name;
    }

    public String getUser_Phone() {
        return User_Phone;
    }

    public void setUser_Phone(String user_Phone) {
        User_Phone = user_Phone;
    }

    public String toString()
    {
        String s = User_Name + " " + User_Phone;
        return s;
    }
}
