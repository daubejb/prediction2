package com.daubedesign.prediction2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import edu.cmu.pocketsphinx.demo.R;

public class SelectCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);
        Toolbar instructionsToolbar = (Toolbar) findViewById(R.id.select_card_toolbar);
        setSupportActionBar(instructionsToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
