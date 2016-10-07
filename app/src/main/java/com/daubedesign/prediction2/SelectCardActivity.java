package com.daubedesign.prediction2;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import edu.cmu.pocketsphinx.demo.R;

import static com.daubedesign.prediction2.Prediction2Activity.settings;


public class SelectCardActivity extends AppCompatActivity {

    public static boolean cardHasBeenClicked = false;
    private View previousCard = (View) null;
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

                v.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorAccent));

//                Toast.makeText(SelectCardActivity.this, "" + position,
//                        Toast.LENGTH_SHORT).show();
                if (cardHasBeenClicked) {
                    previousCard.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                }

                previousCard = v;
                switch (position) {

                    case 0: settings.edit().putInt("selectedCard", R.drawable.clubs_a).commit(); cardHasBeenClicked = true; break;
                    case 1: settings.edit().putInt("selectedCard", R.drawable.hearts_a).commit(); cardHasBeenClicked = true; break;
                    case 2: settings.edit().putInt("selectedCard", R.drawable.spades_a).commit(); cardHasBeenClicked = true; break;
                    case 3: settings.edit().putInt("selectedCard", R.drawable.diamonds_a).commit(); cardHasBeenClicked = true; break;
                    case 4: settings.edit().putInt("selectedCard", R.drawable.clubs_2).commit(); cardHasBeenClicked = true; break;
                    case 5: settings.edit().putInt("selectedCard", R.drawable.hearts_2).commit(); cardHasBeenClicked = true; break;
                    case 6: settings.edit().putInt("selectedCard", R.drawable.spades_2).commit(); cardHasBeenClicked = true; break;
                    case 7: settings.edit().putInt("selectedCard", R.drawable.diamonds_2).commit(); cardHasBeenClicked = true; break;
                    case 8: settings.edit().putInt("selectedCard", R.drawable.clubs_3).commit(); cardHasBeenClicked = true; break;
                    case 9: settings.edit().putInt("selectedCard", R.drawable.hearts_3).commit(); cardHasBeenClicked = true; break;
                    case 10: settings.edit().putInt("selectedCard", R.drawable.spades_3).commit(); cardHasBeenClicked = true; break;
                    case 11: settings.edit().putInt("selectedCard", R.drawable.diamonds_3).commit(); cardHasBeenClicked = true; break;
                    case 12: settings.edit().putInt("selectedCard", R.drawable.clubs_4).commit(); cardHasBeenClicked = true; break;
                    case 13: settings.edit().putInt("selectedCard", R.drawable.hearts_4).commit(); cardHasBeenClicked = true; break;
                    case 14: settings.edit().putInt("selectedCard", R.drawable.spades_4).commit(); cardHasBeenClicked = true; break;
                    case 15: settings.edit().putInt("selectedCard", R.drawable.diamonds_4).commit(); cardHasBeenClicked = true; break;
                    case 16: settings.edit().putInt("selectedCard", R.drawable.clubs_5).commit(); cardHasBeenClicked = true; break;
                    case 17: settings.edit().putInt("selectedCard", R.drawable.hearts_5).commit(); cardHasBeenClicked = true; break;
                    case 18: settings.edit().putInt("selectedCard", R.drawable.spades_5).commit(); cardHasBeenClicked = true; break;
                    case 19: settings.edit().putInt("selectedCard", R.drawable.diamonds_5).commit(); cardHasBeenClicked = true; break;
                    case 20: settings.edit().putInt("selectedCard", R.drawable.clubs_6).commit(); cardHasBeenClicked = true; break;
                    case 21: settings.edit().putInt("selectedCard", R.drawable.hearts_6).commit(); cardHasBeenClicked = true; break;
                    case 22: settings.edit().putInt("selectedCard", R.drawable.spades_6).commit(); cardHasBeenClicked = true; break;
                    case 23: settings.edit().putInt("selectedCard", R.drawable.diamonds_6).commit(); cardHasBeenClicked = true; break;
                    case 24: settings.edit().putInt("selectedCard", R.drawable.clubs_7).commit(); cardHasBeenClicked = true; break;
                    case 25: settings.edit().putInt("selectedCard", R.drawable.hearts_7).commit(); cardHasBeenClicked = true; break;
                    case 26: settings.edit().putInt("selectedCard", R.drawable.spades_7).commit(); cardHasBeenClicked = true; break;
                    case 27: settings.edit().putInt("selectedCard", R.drawable.diamonds_7).commit(); cardHasBeenClicked = true; break;
                    case 28: settings.edit().putInt("selectedCard", R.drawable.clubs_8).commit(); cardHasBeenClicked = true; break;
                    case 29: settings.edit().putInt("selectedCard", R.drawable.hearts_8).commit(); cardHasBeenClicked = true; break;
                    case 30: settings.edit().putInt("selectedCard", R.drawable.spades_8).commit(); cardHasBeenClicked = true; break;
                    case 31: settings.edit().putInt("selectedCard", R.drawable.diamonds_8).commit(); cardHasBeenClicked = true; break;
                    case 32: settings.edit().putInt("selectedCard", R.drawable.clubs_9).commit(); cardHasBeenClicked = true; break;
                    case 33: settings.edit().putInt("selectedCard", R.drawable.hearts_9).commit(); cardHasBeenClicked = true; break;
                    case 34: settings.edit().putInt("selectedCard", R.drawable.spades_9).commit(); cardHasBeenClicked = true; break;
                    case 35: settings.edit().putInt("selectedCard", R.drawable.diamonds_9).commit(); cardHasBeenClicked = true; break;
                    case 36: settings.edit().putInt("selectedCard", R.drawable.clubs_10).commit(); cardHasBeenClicked = true; break;
                    case 37: settings.edit().putInt("selectedCard", R.drawable.hearts_10).commit(); cardHasBeenClicked = true; break;
                    case 38: settings.edit().putInt("selectedCard", R.drawable.spades_10).commit(); cardHasBeenClicked = true; break;
                    case 39: settings.edit().putInt("selectedCard", R.drawable.diamonds_10).commit(); cardHasBeenClicked = true; break;
                    case 40: settings.edit().putInt("selectedCard", R.drawable.clubs_j).commit(); cardHasBeenClicked = true; break;
                    case 41: settings.edit().putInt("selectedCard", R.drawable.hearts_j).commit(); cardHasBeenClicked = true; break;
                    case 42: settings.edit().putInt("selectedCard", R.drawable.spades_j).commit(); cardHasBeenClicked = true; break;
                    case 43: settings.edit().putInt("selectedCard", R.drawable.diamonds_j).commit(); cardHasBeenClicked = true; break;
                    case 44: settings.edit().putInt("selectedCard", R.drawable.clubs_q).commit(); cardHasBeenClicked = true; break;
                    case 45: settings.edit().putInt("selectedCard", R.drawable.hearts_q).commit(); cardHasBeenClicked = true; break;
                    case 46: settings.edit().putInt("selectedCard", R.drawable.spades_q).commit(); cardHasBeenClicked = true; break;
                    case 47: settings.edit().putInt("selectedCard", R.drawable.diamonds_q).commit(); cardHasBeenClicked = true; break;
                    case 48: settings.edit().putInt("selectedCard", R.drawable.clubs_k).commit(); cardHasBeenClicked = true; break;
                    case 49: settings.edit().putInt("selectedCard", R.drawable.hearts_k).commit(); cardHasBeenClicked = true; break;
                    case 50: settings.edit().putInt("selectedCard", R.drawable.spades_k).commit(); cardHasBeenClicked = true; break;
                    case 51: settings.edit().putInt("selectedCard", R.drawable.diamonds_k).commit(); cardHasBeenClicked = true; break;
                    default: settings.edit().putInt("selectedCard", R.drawable.playing_card_back).commit();

                }

            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
