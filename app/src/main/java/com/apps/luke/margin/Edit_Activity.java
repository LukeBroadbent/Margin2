package com.apps.luke.margin;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

public class Edit_Activity extends Activity {

    Spinner sEvents;
    TableLayout tableDisplay;

    List<Event_Class> eventList;
    List<String> eventStrings;
    ArrayAdapter<String> eventAdapter;
    Event_Class currentEvent;

    Button back;

    TextView tvMargin, tvUserName;

    List<EventEntry_Class> entries;
    List<EventEntry_Class> numericallySorted;
    List<EventEntry_Class> alphabeticallySorted;

    protected Global_Variables gv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_menu);

        gv = (Global_Variables) this.getApplication();

        back = (Button) findViewById(R.id.bBack);

        sEvents = (Spinner) findViewById(R.id.sEvents);
        //table = (TableLayout) findViewById(R.id.tlTable);


        tableDisplay = (TableLayout) findViewById(R.id.tableDisplay);
        tableDisplay.setStretchAllColumns(true);
        tableDisplay.setPadding(5, 5, 5, 5);


        tvUserName = (TextView) findViewById(R.id.tvUserName);
        tvMargin = (TextView) findViewById(R.id.tvMargin);

        eventList = gv.getDatabase().getEventsBasedOnStatus("A");

        eventStrings = new LinkedList<String>();

        for(int i = 0; i < eventList.size(); i++)
        {
            eventStrings.add(eventList.get(i).getEvent_Name());
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
                        tableDisplay.removeAllViews();
                        entries = null;

                        tvUserName.setBackgroundColor(Color.WHITE);
                        tvMargin.setBackgroundColor(Color.WHITE);
                        entries = gv.getDatabase().getEntriesOfEvent(currentEvent.getEvent_ID());
                        //numericallySorted = gv.orderNumerically(entries);
                        //alphabeticallySorted = gv.orderAlphabetically(entries);



                        //tvMargin.setBackgroundColor(Color.parseColor("#D3D3D3"));

                        /*
                        for(int x=0;x<numericallySorted.size();x++)
                        {
                            Log.d("Margin", Integer.toString(numericallySorted.get(x).getEntry_Margin()));
                        }
                        */

                        Log.d("Entries Size", Integer.toString(entries.size()));
                        fillTable(gv.orderNumerically(entries));
                        tvMargin.setBackgroundColor(Color.parseColor("#D3D3D3"));


                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //tvMargin = new TextView(Edit_Activity.this);
        tvMargin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Margin","Pressed");
                tableDisplay.removeAllViews();
                fillTable(gv.orderNumerically(entries));
                tvMargin.setBackgroundColor(Color.parseColor("#D3D3D3"));
                tvUserName.setBackgroundColor(Color.WHITE);

            }
        });

        //tvUserName = new TextView(Edit_Activity.this);
        tvUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Username","Pressed");
                tableDisplay.removeAllViews();
                //buildTable();

                tvMargin.setBackgroundColor(Color.WHITE);
                tvUserName.setBackgroundColor(Color.parseColor("#D3D3D3"));
                //List<EventEntry_Class> entries = gv.getDatabase().getEntriesOfEvent(currentEvent.getEvent_ID());
                //List<EventEntry_Class> alphabeticallySorted = gv.orderAlphabetically(entries);
                //List<EventEntry_Class> alphabeticallySorted = gv.orderAlphabetically(entries);
                fillTable(gv.orderAlphabetically(entries));

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

    /*
    public void buildTable()
    {
        TableRow trHeader = new TableRow(Edit_Activity.this);

        TextView tvEntryId = new TextView(Edit_Activity.this);
        tvEntryId.setText("Entry Number");
        tvEntryId.setTextColor(Color.BLACK);
        tvEntryId.setTypeface(Typeface.DEFAULT_BOLD);
        tvEntryId.setGravity(Gravity.CENTER_HORIZONTAL);

        tvUserName = new TextView(Edit_Activity.this);
        tvUserName.setText("Name");
        tvUserName.setTextColor(Color.BLACK);
        tvUserName.setTypeface(Typeface.DEFAULT_BOLD);
        tvUserName.setGravity(Gravity.CENTER_HORIZONTAL);


        tvMargin = new TextView(Edit_Activity.this);
        tvMargin.setText("Margin");
        tvMargin.setTextColor(Color.BLACK);
        tvMargin.setTypeface(Typeface.DEFAULT_BOLD);
        tvMargin.setGravity(Gravity.CENTER_HORIZONTAL);

        trHeader.addView(tvEntryId);
        trHeader.addView(tvUserName);
        trHeader.addView(tvMargin);

        table.addView(trHeader, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        /*
        for(int i = 0; i < entries.size(); i++)
        {
            TableRow tr = new TableRow(Edit_Activity.this);

            TextView tvEId = new TextView(Edit_Activity.this);
            //tvEId.setText(Integer.toString(entries.get(i).getEntry_ID()));
            tvEId.setText(Integer.toString(i+1));
            tvEId.setTextColor(Color.BLACK);
            tvEId.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvEId);

            TextView tvUN = new TextView(Edit_Activity.this);
            User_Class u = gv.getDatabase().getUser(entries.get(i).getUser_ID());
            tvUN.setText(u.getUser_Name());
            tvUN.setTextColor(Color.BLACK);
            tvUN.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvUN);

            TextView tvMarg = new TextView(Edit_Activity.this);
            tvMarg.setText(Integer.toString(entries.get(i).getEntry_Margin()));
            tvMarg.setTextColor(Color.BLACK);
            tvMarg.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvMarg);

            table.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }


    }
*/

    public void fillTable(List<EventEntry_Class> entries)
    {
        for(int i = 0; i < entries.size(); i++)
        {
            TableRow tr = new TableRow(Edit_Activity.this);

            TextView tvEId = new TextView(Edit_Activity.this);
            //tvEId.setText(Integer.toString(entries.get(i).getEntry_ID()));
            tvEId.setText(Integer.toString(i+1));
            tvEId.setTextColor(Color.BLACK);
            tvEId.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvEId);

            TextView tvUN = new TextView(Edit_Activity.this);
            User_Class u = gv.getDatabase().getUser(entries.get(i).getUser_ID());
            tvUN.setText(u.getUser_Name());
            tvUN.setTextColor(Color.BLACK);
            tvUN.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvUN);

            TextView tvMarg = new TextView(Edit_Activity.this);
            tvMarg.setText(Integer.toString(entries.get(i).getEntry_Margin()));
            tvMarg.setTextColor(Color.BLACK);
            tvMarg.setGravity(Gravity.CENTER_HORIZONTAL);
            tr.addView(tvMarg);

            tableDisplay.addView(tr, new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }
}
