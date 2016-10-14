package com.apps.luke.margin.Views;

import android.app.Activity;
import android.os.Bundle;

import com.apps.luke.margin.R;

/**
 * Created by chris on 14/04/16.
 */
public class TextActivity extends Activity {

    //gets all numbers from the database and sends a text message to them with the input text from this screen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_menu);

    }

}
