package com.apps.luke.margin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class Admin_Activity extends Activity {

    Button bCreate, bComplete, bEdit, bPast, bBack;

    Intent iCreate, iComplete, iEdit, iPast;

    protected Global_Variables gv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_menu);

        gv = (Global_Variables) this.getApplication();

        bCreate = (Button) findViewById(R.id.bCreate);
        bComplete = (Button) findViewById(R.id.bComplete);
        bEdit = (Button) findViewById(R.id.bEventEntries);
        bPast = (Button) findViewById(R.id.bPast);
        bBack = (Button) findViewById(R.id.bBack);

        iCreate = new Intent(this, Create_Activity.class);
        iComplete = new Intent(this, Complete_Activity.class);
        iEdit = new Intent(this, Edit_Activity.class);
        iPast = new Intent(this, Past_Activity.class);

        bCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(iCreate);
            }

        });

        bComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Event_Class> events = gv.getDatabase().getEventsBasedOnStatus("A");
                if(events.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Events to Complete", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(iComplete);
            }

        });

        bEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Event_Class> events = gv.getDatabase().getEventsBasedOnStatus("A");
                if(events.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Active Events", Toast.LENGTH_SHORT).show();
                    return;
                }

                startActivity(iEdit);
            }

        });

        bPast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Event_Class> events = gv.getDatabase().getEventsBasedOnStatus("C");
                if(events.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Completed Events", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(iPast);
            }

        });

        bBack.setOnClickListener(new View.OnClickListener() {
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
