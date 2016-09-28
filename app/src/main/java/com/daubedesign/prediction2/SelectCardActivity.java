package com.daubedesign.prediction2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import edu.cmu.pocketsphinx.demo.R;

public class SelectCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);
        Toolbar instructionsToolbar = (Toolbar) findViewById(R.id.select_card_toolbar);
        setSupportActionBar(instructionsToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        GridView cardSelectionGrid = (GridView) findViewById(R.id.card_selection_grid);
        cardSelectionGrid.setAdapter(new ImageAdapter(this));

        cardSelectionGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Toast.makeText(SelectCardActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
