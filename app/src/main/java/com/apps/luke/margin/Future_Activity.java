package com.apps.luke.margin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by chris on 30/03/16.
 */
public class Future_Activity extends Activity
{
    Button bBack;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.future_menu);


        bBack = (Button) findViewById(R.id.bBack);
        iv = (ImageView) findViewById(R.id.imageView);


        bBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }
}
