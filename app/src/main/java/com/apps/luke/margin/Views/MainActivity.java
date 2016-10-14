package com.apps.luke.margin.Views;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.apps.luke.margin.Model.Event;
import com.apps.luke.margin.R;
import com.apps.luke.margin.Utils.GlobalVariables;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();

    protected GlobalVariables gv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        gv = (GlobalVariables) this.getApplication();

        //Backup Database
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
    }

    @OnClick(R.id.bEnter)
    public void enter() {
        List<Event> events = gv.getDatabase().getEventsBasedOnStatus("A");
        if(events.isEmpty()) {
            Toast.makeText(getApplicationContext(), "No Active Events", Toast.LENGTH_SHORT).show();
            return;
        }
        startActivity(new Intent(this, UserActivity.class));
    }

    @OnClick(R.id.bLogin)
    public void login(){
        startActivity(new Intent(this, AdminActivity.class));
    }

    @OnClick(R.id.bExit)
    public void exit(){finish();}


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
