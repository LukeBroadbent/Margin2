package com.apps.luke.margin;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by chris on 14/04/16.
 */
public class Text_Activity extends Activity {

    //gets all numbers from the database and sends a text message to them with the input text from this screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_menu);

    }

}
