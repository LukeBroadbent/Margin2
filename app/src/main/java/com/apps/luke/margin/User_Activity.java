package com.apps.luke.margin;

 import android.app.Activity;
 import android.app.Dialog;
 import android.app.usage.UsageEvents;
 import android.content.Context;
 import android.os.Bundle;
 import android.provider.Settings;
 import android.telephony.SmsManager;
 import android.text.Editable;
 import android.text.TextWatcher;
 import android.util.Log;
 import android.view.Menu;
 import android.view.MenuItem;
 import android.view.View;
 import android.widget.Adapter;
 import android.widget.AdapterView;
 import android.widget.ArrayAdapter;
 import android.widget.Button;
 import android.widget.EditText;
 import android.widget.ListView;
 import android.widget.Spinner;
 import android.widget.TextView;
 import android.widget.Toast;

 import java.util.ArrayList;
 import java.util.LinkedList;
 import java.util.List;
 import java.util.Random;

public class User_Activity extends Activity {

    Button bSubmit, bBack, bNew, bExisting;
    EditText etName, etPhone;
    Spinner sEvents, sMargins;
    TextView tvName, tvPhone, tvRemaining;


    List<Event_Class> eventList;
    List<String> eventStrings;
    List<EventEntry_Class> eventEntries;
    List<String> marginSelections;

    ArrayAdapter<String> eventAdapter;
    ArrayAdapter<String> entriesAdapter;
    ArrayAdapter<String> usersAdapter;

    //DatabaseHelper db;
    protected Global_Variables gv;

    SmsManager sms;

    final Context context = this;

    Event_Class currentEvent;
    User_Class currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_menu);

        gv = (Global_Variables) this.getApplication();

        bSubmit = (Button) findViewById(R.id.bSubmit);
        bBack = (Button) findViewById(R.id.bBack);
        bNew = (Button) findViewById(R.id.bNew);
        bExisting = (Button) findViewById(R.id.bExisting);

        tvName = (TextView) findViewById(R.id.tvName);
        tvPhone = (TextView) findViewById(R.id.tvPhone);
        tvRemaining = (TextView) findViewById(R.id.tvRemaining);

        //etName = (EditText) findViewById(R.id.etName);
        //etPhone = (EditText) findViewById(R.id.etPhone);

        sEvents = (Spinner) findViewById(R.id.sEvents);
        sMargins = (Spinner) findViewById(R.id.sEntries);

        //db = new DatabaseHelper(getApplicationContext());

        eventList =  gv.getDatabase().getEventsBasedOnStatus("A");

        sms =  SmsManager.getDefault();


        Log.d("eventsList.size",Integer.toString(eventList.size()));

        eventStrings = new LinkedList<String>();

        for(int i = 0; i < eventList.size(); i++)
        {
            Log.d("EventList Name", eventList.get(i).getEvent_Name());
            eventStrings.add(eventList.get(i).getEvent_Name());
            Log.d("Events Size", Integer.toString(eventStrings.size()));
        }


        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventStrings);
        sEvents.setAdapter(eventAdapter);

        sEvents.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //int eventID = 0;
                for (int i = 0; i < eventList.size(); i++) {
                    if (eventList.get(i).getEvent_Name().equals(sEvents.getSelectedItem().toString())) {

                        currentEvent = eventList.get(i);
                        //eventID = eventList.get(i).getEvent_ID();
                    }
                }



                eventEntries = gv.getDatabase().getEventEntriesBasedOnEvent(currentEvent.getEvent_ID());

                String remainingEntries = "";

                if(eventEntries.size() != 0)
                {
                    remainingEntries = "Events (Entries Left - " + Integer.toString(100 - eventEntries.size()) + ")";
                }
                else
                {
                    remainingEntries = "Events (Entries Left - 100)";
                }

                tvRemaining.setText(remainingEntries);


                marginSelections = fillList();

                /*
                if (eventEntries.size() != 0) {
                    for (int i = 0; i < eventEntries.size(); i++) {
                        String entry = Integer.toString(eventEntries.get(i).getEntry_Margin());

                        for (int x = 0; x < marginSelections.size(); x++) {
                            if (marginSelections.get(x).equals(entry)) {
                                marginSelections.remove(x);
                            }
                        }
                    }
                }
*/

                entriesAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, gv.getEntries());
                sMargins.setAdapter(entriesAdapter);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        /*

        if(!sEvents.getSelectedItem().toString().equals(""))
        {
            eventEntries = db.getEventEntriesBasedOnEvent(sEvents.getSelectedItem().toString());
            marginSelections = fillList();


            if(eventEntries.size() != 0)
            {
               for(int i = 0; i < eventEntries.size(); i++)
               {
                   String entry = Integer.toString(eventEntries.get(i).getEntry_Margin());

                   for(int x = 0; x < marginSelections.size(); x++)
                   {
                       if(marginSelections.get(x).equals(entry))
                       {
                           marginSelections.remove(x);
                       }
                   }
               }
            }

            marginAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, marginSelections);
            sMargins.setAdapter(eventAdapter);
        }

        */

        bNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.new_user);
                dialog.setTitle("Add New User");
                dialog.show();

                Button ok = (Button) dialog.findViewById(R.id.bConfirm);
                Button cancel = (Button) dialog.findViewById(R.id.bCancel);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        EditText name = (EditText) dialog.findViewById(R.id.etName);
                        EditText number = (EditText) dialog.findViewById(R.id.etNumber);

                        String username = name.getText().toString();
                        String usernumber = number.getText().toString();

                        Log.d("Name", username);
                        Log.d("Number", usernumber);

                        if(name.getText().toString().equals("") || number.getText().toString().equals(""))
                        {
                            Toast.makeText(getApplicationContext(), "Enter Details Correctly", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (username.equals("")) {
                            Toast.makeText(getApplicationContext(), "Enter Your Name Correctly", Toast.LENGTH_LONG).show();
                            return;
                        }

                        if (usernumber.length() != 10 || usernumber.equals("")) {
                            Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
                            return;
                        }
                        //0417 070 553

                        //Captialize Names


                        User_Class user = new User_Class();

                        //set user_id with get next user id method in database helper

                        user.setUser_ID(gv.getDatabase().getNextUserId());
                        user.setUser_Name(gv.capitalizeNames(username));
                        user.setUser_Phone(usernumber);


                        Log.d("Before adding to DB", "Before");
                        Log.d("user id", Integer.toString(user.getUser_ID()));

                        gv.getDatabase().createUser(user);

                        currentUser = user;

                        tvName.setText(gv.capitalizeNames(username));
                        tvPhone.setText(usernumber);

                        //Create new user object
                        //Send user object to database adding stuff here


                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }

        });

        bSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //build event entry
                //submit to database

                if(tvName.getText().toString().equals("") || tvPhone.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "You must select a user", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(tvPhone.getText().toString().equals("0438539689"))
                {
                    Toast.makeText(User_Activity.this, "I hope you lose your money krackers", Toast.LENGTH_SHORT).show();
                }
                if(tvPhone.getText().toString().equals("0422684645"))
                {
                    Toast.makeText(User_Activity.this, "HOCHI MIN FAT, get a haircut", Toast.LENGTH_SHORT).show();
                }
                if(tvPhone.getText().toString().equals("0450964601"))
                {
                    Toast.makeText(User_Activity.this, "Oi Jake!!!, its time to grab the fire extinguisher", Toast.LENGTH_SHORT).show();
                }
                if(tvPhone.getText().toString().equals("0430113210"))
                {
                    Toast.makeText(User_Activity.this, "You are a hack hollywood", Toast.LENGTH_SHORT).show();
                }
                if(tvPhone.getText().toString().equals("0425373163"))
                {
                    Toast.makeText(User_Activity.this, "Queeeeeefin!!!", Toast.LENGTH_SHORT).show();
                }
                if(tvPhone.getText().toString().equals("0425373163"))
                {
                }
                if(tvPhone.getText().toString().equals("0425373163"))
                {
                }
                if(tvPhone.getText().toString().equals("0425373163"))
                {
                }



                int entriesCount = Integer.parseInt(sMargins.getSelectedItem().toString());
                Log.d("entriesCount", Integer.toString(entriesCount));

                List<Integer> selectedMargins = gv.getDatabase().getAllEventMargins(currentEvent.getEvent_ID());

                /*
                if(selectedMargins != null)
                {
                    for(int i = 0; i < selectedMargins.size(); i++)
                    {
                        Log.d("Selected Margins List", Integer.toString(selectedMargins.get(i)));
                    }
                }
                */


                List<Integer> margins = gv.convertStringListToIntList(marginSelections);
                 /*
                for(int i = 0; i < margins.size(); i++)
                {
                    Log.d("Margins List", Integer.toString(margins.get(i)));
                }
                */

                List<Integer> finalMargins = gv.removeSelectedMargins(margins, selectedMargins);
                /*
                for(int i = 0; i < finalMargins.size(); i++)
                {
                    Log.d("Final Margins List", Integer.toString(finalMargins.get(i)));
                }
                */

                List<EventEntry_Class> eventEntries = new LinkedList<EventEntry_Class>();

                Log.d("MARGINS", Integer.toString(finalMargins.size()));

                if(finalMargins.size() == 0)
                {
                    Toast.makeText(getApplicationContext(), currentEvent.getEvent_Name() + " is full", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(entriesCount > finalMargins.size())
                {
                    Toast.makeText(getApplicationContext(), "Too Many Entries, Only " + Integer.toString(margins.size()) + " entry(s) left", Toast.LENGTH_SHORT).show();
                    return;
                }

                String smsString = "Thank you " + currentUser.getUser_Name() + " for entering " + currentEvent.getEvent_Name() + " Margin game, Your margin selections are ";

                for(int i = 0; i < finalMargins.size(); i++)
                {
                    Log.d("Margins Left Over", Integer.toString(finalMargins.get(i)));
                }

                for (int i = 0; i < entriesCount; i++) {
                    Log.d("iterator", Integer.toString(i));
                    Random rander = new Random();
                    int index = rander.nextInt(finalMargins.size()); //gets random index
                    Log.d("Index", Integer.toString(index));

                    int randomNumber = finalMargins.get(index); //gets the margin from the random index
                    Log.d("randomNumber", Integer.toString(randomNumber));

                    Log.d("Margins List Size b4", Integer.toString(finalMargins.size()));
                    finalMargins.remove(index);
                    Log.d("Margins List Size af", Integer.toString(finalMargins.size()));

                    EventEntry_Class e = new EventEntry_Class();
                    e.setEntry_Margin(randomNumber);
                    e.setEvent_ID(currentEvent.getEvent_ID());
                    e.setUser_ID(currentUser.getUser_ID());

                    eventEntries.add(e);

                    smsString = smsString + Integer.toString(e.getEntry_Margin()) + ", ";



                }

                gv.getDatabase().createEventEntries(eventEntries);

                Log.d("SMS String", smsString);

                if(smsString != null && smsString.length() > 0 && smsString.charAt(smsString.length()-2)==',')
                {
                    smsString = smsString.substring(0, smsString.length()-2);
                }

                sms.sendTextMessage(currentUser.getUser_Phone(), null, smsString, null, null);

                Toast.makeText(getApplicationContext(), "Entries have been submitted", Toast.LENGTH_SHORT).show();

                //Send Text Message to user.

                finish();
            }
        });

        bExisting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //get all users
                final List<User_Class> usersList = gv.getDatabase().getAllUsers();
                ArrayList<String> defaultUserStrings = usersToStringList(usersList);


                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.existing_users);
                dialog.setTitle("Search for Existing User");
                dialog.show();

                final ListView username = (ListView) dialog.findViewById(R.id.lvUsers);
                //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, defaultUserStrings);
                //username.setAdapter(adapter);

                final EditText search = (EditText) dialog.findViewById(R.id.etSearch);

                search.addTextChangedListener(new TextWatcher() {
                    //Displays Results of Customer Search


                    public void afterTextChanged(Editable s) {

                    }

                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    public void onTextChanged(CharSequence cs, int start, int before, int count) {
                        ArrayList<String> userStrings = new ArrayList<String>();
                        ArrayAdapter<String> adapter;
                        List<User_Class> tempUserList;

                        //Checks if list is full then clears the list
                        if (userStrings != null) {
                            userStrings.clear();
                        }
                        //Checks if list is full then clears the list
                        if (userStrings != null) {
                            userStrings.clear();
                        }

                        //gets updated user input from searchbox and adds
                        String tempInput = search.getText().toString();
                        tempUserList = gv.getDatabase().searchUser(tempInput);

                        //Fills CustomerList with strings to be displayed of customers
                        userStrings = usersToStringList(tempUserList);

                        //Displays List of customers in listview via the adapter


                        adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, userStrings);
                        username.setAdapter(adapter);

                        //adding functionality to items displayed in the listview
                        username.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            public void onItemClick(AdapterView<?> myAdapter, View myView, int myItemInt, long mylng) {
                                String selectedFromList = (String) (username.getItemAtPosition(myItemInt));

                                String[] breakdown = selectedFromList.split(" ");

                                //Sets TextView to show a customer has been selected



                                //Sets customer object to the customer selected by the user

                                for(int i = 0; i < usersList.size(); i++)
                                {
                                    if(usersList.get(i).toString().equals(selectedFromList))
                                    {
                                        currentUser = usersList.get(i);
                                    }
                                }

                                tvName.setText(currentUser.getUser_Name());
                                tvPhone.setText(currentUser.getUser_Phone());

                                dialog.dismiss();
                            }
                        });
                    }

                });


                Button cancel = (Button) dialog.findViewById(R.id.bCancel);
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });

            }
        });

        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public ArrayList<String> usersToStringList(List<User_Class> users)
    {
        ArrayList<String> list = new ArrayList<String>();

        for(int i = 0; i <users.size(); i++)
        {
            list.add(users.get(i).toString());
        }

        return list;
    }

    public List<String> fillList()
    {
        List<String> numberList = new LinkedList<String>();
        for(int i = 1; i < 101; i++)
        {
            String number = Integer.toString(i);
            numberList.add(number);
        }

        return numberList;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
