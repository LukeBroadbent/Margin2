package com.apps.luke.margin;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends Activity{

    Button bLogin, bEnter, bExit;

    EditText etLogin, etPassword;

    Intent iAdmin, iEnter, iFuture;

    protected Global_Variables gv;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gv = (Global_Variables) this.getApplication();

        //gv.capitalizeAllUserNames();


        try {
            File sd = Environment.getExternalStorageDirectory();

            Log.d("External Storage", Environment.getExternalStorageDirectory().getPath());

            //File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "/data/data/" + getPackageName() + "/databases/FundraisingDatabase";
                //String currentDBPath = "//data//{com.app.luke.margin}//databases//{FundraisingDatabase}";
                //data/data/<Your-Application-Package-Name>/databases/<your-database-name>
                Log.d("currentPath", currentDBPath);
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                currentDateTimeString = currentDateTimeString.replaceAll(" ", "");
                currentDateTimeString = currentDateTimeString.replaceAll(":","");
                currentDateTimeString = currentDateTimeString.replaceAll("/","");
                Log.d("Current time", currentDateTimeString);


                String backupDBPath = "/marginbackups/" + "hfcmargin-" + currentDateTimeString + ".db";

                Log.d("Backup Path", " " + backupDBPath);

                File currentDB = new File(currentDBPath);

                Log.d("currentDB", Boolean.toString(currentDB.exists()));

                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            }
        } catch (Exception e) {

        }

        bLogin = (Button) findViewById(R.id.bLogin);
        bEnter = (Button) findViewById(R.id.bEnter);
        bExit = (Button) findViewById(R.id.bExit);

        etLogin = (EditText) findViewById(R.id.etUser);
        etPassword = (EditText) findViewById(R.id.etPassword);

        iAdmin = new Intent(this, Admin_Activity.class);
        iEnter = new Intent(this, User_Activity.class);
        iFuture = new Intent(this, Future_Activity.class);


        Log.d("Testing Global", GlobalVariables_Class.password);


        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String login;
                //String pass;

                startActivity(iAdmin);
                /*
                Log.d("Testing Global", GlobalVariables_Class.password);

                if (etLogin.getText() != null && etPassword.getText() != null) {
                    login = etLogin.getText().toString();
                    pass = etPassword.getText().toString();

                    if (login.equals("") || pass.equals("")) {
                        Toast.makeText(getApplicationContext(), "Incorrect Login Details", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (!login.equals(GlobalVariables_Class.username) || !pass.equals(GlobalVariables_Class.password)) {
                        Toast.makeText(getApplicationContext(), "Incorrect Login Details", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (login.equals(GlobalVariables_Class.username) && pass.equals(GlobalVariables_Class.password)) {
                        startActivity(iAdmin);
                        etLogin.setText("");
                        etPassword.setText("");
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Incorrect Login Details", Toast.LENGTH_LONG).show();
                    return;
                }
                */
            }

        });

        bEnter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                List<Event_Class> events = gv.getDatabase().getEventsBasedOnStatus("A");
                if(events.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "No Active Events", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(iEnter);
            }

        });

        bEnter.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                startActivity(iFuture);
                return true;
            }
        });

        bExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
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
