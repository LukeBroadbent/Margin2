package com.apps.luke.margin.Views;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.apps.luke.margin.Model.Event;
import com.apps.luke.margin.Model.EventEntry;
import com.apps.luke.margin.Model.User;
import com.apps.luke.margin.R;
import com.apps.luke.margin.Utils.GlobalVariables;

import java.util.LinkedList;
import java.util.List;

public class CompleteActivity extends Activity {


    List<Event> eventList;
    List<String> eventStrings;
    ArrayAdapter<String> eventAdapter;

    Spinner events;
    Button submit, back;

    protected GlobalVariables gv;

    Event currentEvent;
    EventEntry eventWinner;
    User winningUser;

    SmsManager sms;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_menu);
        gv = (GlobalVariables) this.getApplication();
        sms =  SmsManager.getDefault();
        submit = (Button) findViewById(R.id.bSubmit);
        back = (Button) findViewById(R.id.bBack);
        events = (Spinner) findViewById(R.id.sEvents);

        eventList = gv.getDatabase().getEventsBasedOnStatus("A");

        eventStrings = new LinkedList<String>();

        for(int i = 0; i < eventList.size(); i++)
        {
            Log.d("EventList Name", eventList.get(i).getEvent_Name());
            eventStrings.add(eventList.get(i).getEvent_Name());
            Log.d("Events Size", Integer.toString(eventStrings.size()));
        }


        eventAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventStrings);
        events.setAdapter(eventAdapter);



        events.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                for (int i = 0; i < eventList.size(); i++) {
                    if (eventList.get(i).getEvent_Name().equals(events.getSelectedItem().toString())) {

                        currentEvent = eventList.get(i);
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(CompleteActivity.this);
                dialog.setTitle("Enter Final Margin");
                dialog.setContentView(R.layout.complete_popup);
                dialog.show();

                final Button ok = (Button) dialog.findViewById(R.id.bSubmit);
                final Button cancel = (Button) dialog.findViewById(R.id.bCancel);
                final EditText score = (EditText) dialog.findViewById(R.id.etScore);


                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer finalScore = Integer.parseInt(score.getText().toString());

                        if(finalScore < 0 )
                        {

                            Toast.makeText(getApplicationContext(), "Incorrect Margin Input", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Log.d("final Score", Integer.toString(finalScore));
                        Log.d("currentEvent", Integer.toString(currentEvent.getEvent_ID()));

                        eventWinner = gv.getDatabase().getWinner(currentEvent.getEvent_ID(),finalScore);
                        //Log.d("Event Winner", " - " + eventWinner.getUser_ID());


                        if(eventWinner == null) {
                            if (gv.getDatabase().finishEvent(currentEvent.getEvent_ID(), finalScore)) {
                                Log.d("Database", "FinishEvent() Success");
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "No Correct Margin, House wins", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        } else {

                            winningUser = gv.getDatabase().getUser(eventWinner.getUser_ID());
                            if (gv.getDatabase().finishEvent(currentEvent.getEvent_ID(), finalScore)) {
                                Log.d("Database", "FinishEvent() Success");

                                List<Event> newE = gv.getDatabase().getEventsBasedOnStatus("C");
                                Log.d("C status size", Integer.toString(newE.size()));

                                for (int i = 0; i < newE.size(); i++) {
                                    Log.d("Event", newE.get(i).getEvent_Name());
                                    Log.d("Event ID", Integer.toString(newE.get(i).getEvent_ID()));
                                }


                                sms.sendTextMessage(winningUser.getUser_Phone(), null, "Congratulations " + winningUser.getUser_Name() + " you have won the " + currentEvent.getEvent_Name() + " margin game", null, null);

                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Message Sent to Winner", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Log.d("Database", "Exception occurred");
                            }

                        }

                    }
                });



                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                //popup asking for final score of activity
                //on ok
                //get score from popup



                //get event entiry based on score
                //send text to user about their win

                //update event record

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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
