package com.apps.luke.margin.Views;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apps.luke.margin.Model.Event;
import com.apps.luke.margin.R;
import com.apps.luke.margin.Utils.GlobalVariables;
import com.apps.luke.margin.Utils.GlobalVariablesClass;

import java.util.ArrayList;
import java.util.List;

public class CreateActivity extends Activity {

    Button submit, back;
    EditText eventName;
    //Spinner spinner;

    ArrayList<String> eventTypes;

    ArrayAdapter<String> a;

    //GlobalVariables gv;
    protected GlobalVariables gv;





    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_menu);

        gv = (GlobalVariables) this.getApplication();

        submit = (Button) findViewById(R.id.bSubmit);
        back = (Button) findViewById(R.id.bBack);

        eventName = (EditText) findViewById(R.id.eventName);

        //spinner = (Spinner) findViewById(R.id.spinner);

        eventTypes = new ArrayList<String>();
        eventTypes.add("Margin");
        eventTypes.add("Event 2");
        eventTypes.add("Event 3");

        //a = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, eventTypes);
        //spinner.setAdapter(a);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = eventName.getText().toString();
                Log.d("EventName", name);

                if(eventName.getText().toString().matches("")){
                    Toast.makeText(CreateActivity.this, "Enter Event Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                //String type = spinner.getSelectedItem().toString();
                String type = "Margin";
                Log.d("EventType", type);

                Event e = new Event();

                Log.d("Testing Global", GlobalVariablesClass.password);



                e.setEvent_ID(gv.getDatabase().getNextEventId());
                e.setEvent_Name(name);
                e.setEvent_Status("A");
                e.setEvent_Type(type);
                gv.getDatabase().createEvent(e);

                List<Event> newE =  gv.getDatabase().getEventsBasedOnStatus("A");

                for(int i = 0; i < newE.size(); i++)
                {
                    Log.d("Event", newE.get(i).getEvent_Name());
                    Log.d("Event ID", Integer.toString(newE.get(i).getEvent_ID()));
                }

                Toast.makeText(getApplicationContext(), "Event Created!!! =)", Toast.LENGTH_LONG).show();

                finish();
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
