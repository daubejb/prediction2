/* ====================================================================
 * Copyright (c) 2014 Alpha Cephei Inc.  All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALPHA CEPHEI INC. ``AS IS'' AND
 * ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL CARNEGIE MELLON UNIVERSITY
 * NOR ITS EMPLOYEES BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * ====================================================================
 */

package com.daubedesign.prediction2;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import edu.cmu.pocketsphinx.Assets;
import edu.cmu.pocketsphinx.Hypothesis;
import edu.cmu.pocketsphinx.RecognitionListener;
import edu.cmu.pocketsphinx.SpeechRecognizer;
import edu.cmu.pocketsphinx.SpeechRecognizerSetup;
import edu.cmu.pocketsphinx.demo.R;

import static android.widget.Toast.makeText;

public class Prediction2Activity extends AppCompatActivity implements
        RecognitionListener {

    /* Playing card picture holder */
    private ImageView cardImageView;
    /* Named searches allow to quickly reconfigure the decoder */
    private static final String KWS_SEARCH = "wakeup";
    private static final String MENU_SEARCH = "menu";

    /* Keyword we are looking for to activate menu */
    private static final String KEYPHRASE = "but you selected";

    /* Used to handle permission request */
    private static final int PERMISSIONS_REQUEST_RECORD_AUDIO = 1;

    private SpeechRecognizer recognizer;
    private HashMap<String, Integer> captions;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        // Prepare the data for UI
        captions = new HashMap<String, Integer>();
        captions.put(KWS_SEARCH, R.string.kws_caption);
        captions.put(MENU_SEARCH, R.string.menu_caption);
        setContentView(R.layout.main);
        Toolbar predictionToolbar = (Toolbar) findViewById(R.id.prediction_toolbar);
        setSupportActionBar(predictionToolbar);
        getSupportActionBar().setTitle(R.string.prediction_activity_title);
        ((TextView) findViewById(R.id.caption_text))
                .setText("Preparing the recognizer");
        cardImageView = (ImageView) findViewById(R.id.card_image_view);
        cardImageView.setImageResource(R.drawable.playing_card_back);
        // Check if user has given permission to record audio
        int permissionCheck = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
        if (permissionCheck == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, PERMISSIONS_REQUEST_RECORD_AUDIO);
            return;
        }
        runRecognizerSetup();
    }

    private void runRecognizerSetup() {
        // Recognizer initialization is a time-consuming and it involves IO,
        // so we execute it in async task
        new AsyncTask<Void, Void, Exception>() {
            @Override
            protected Exception doInBackground(Void... params) {
                try {
                    Assets assets = new Assets(Prediction2Activity.this);
                    File assetDir = assets.syncAssets();
                    setupRecognizer(assetDir);
                } catch (IOException e) {
                    return e;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Exception result) {
                if (result != null) {
                    ((TextView) findViewById(R.id.caption_text))
                            .setText("Failed to init recognizer " + result);
                } else {
                    switchSearch(KWS_SEARCH);
                }
            }
        }.execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST_RECORD_AUDIO) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                runRecognizerSetup();
            } else {
                finish();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (recognizer != null) {
            recognizer.cancel();
            recognizer.shutdown();
        }
    }

    /**
     * In partial result we get quick updates about current hypothesis. In
     * keyword spotting mode we can react here, in other modes we need to wait
     * for final result in onResult.
     */
    @Override
    public void onPartialResult(Hypothesis hypothesis) {
        if (hypothesis == null)
            return;

        String text = hypothesis.getHypstr();
        if (text.equals(KEYPHRASE))
            switchSearch(MENU_SEARCH);
        else
            ((TextView) findViewById(R.id.result_text)).setText(text);
    }

    /**
     * This callback is called when we stop the recognizer.
     */
    @Override
    public void onResult(Hypothesis hypothesis) {
        ((TextView) findViewById(R.id.result_text)).setText("");
        if (hypothesis != null) {
            String text = hypothesis.getHypstr();
            makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();

            if (text.contains("ace") && text.contains("spades"))        { cardImageView.setImageResource(R.drawable.spades_a); }
            else if (text.contains("two") && text.contains("spades"))   { cardImageView.setImageResource(R.drawable.spades_2); }
            else if (text.contains("three") && text.contains("spades")) { cardImageView.setImageResource(R.drawable.spades_3); }
            else if (text.contains("four") && text.contains("spades"))  { cardImageView.setImageResource(R.drawable.spades_4); }
            else if (text.contains("five") && text.contains("spades"))  { cardImageView.setImageResource(R.drawable.spades_5); }
            else if (text.contains("six") && text.contains("spades"))   { cardImageView.setImageResource(R.drawable.spades_6); }
            else if (text.contains("seven") && text.contains("spades")) { cardImageView.setImageResource(R.drawable.spades_7); }
            else if (text.contains("eight") && text.contains("spades")) { cardImageView.setImageResource(R.drawable.spades_8); }
            else if (text.contains("nine") && text.contains("spades"))  { cardImageView.setImageResource(R.drawable.spades_9); }
            else if (text.contains("ten") && text.contains("spades"))   { cardImageView.setImageResource(R.drawable.spades_10); }
            else if (text.contains("jack") && text.contains("spades"))  { cardImageView.setImageResource(R.drawable.spades_j); }
            else if (text.contains("queen") && text.contains("spades")) { cardImageView.setImageResource(R.drawable.spades_q); }
            else if (text.contains("king") && text.contains("spades"))  { cardImageView.setImageResource(R.drawable.spades_k); }
            else if (text.contains("ace") && text.contains("clubs"))    { cardImageView.setImageResource(R.drawable.clubs_a); }
            else if (text.contains("two") && text.contains("clubs"))    { cardImageView.setImageResource(R.drawable.clubs_2); }
            else if (text.contains("three") && text.contains("clubs"))  { cardImageView.setImageResource(R.drawable.clubs_3); }
            else if (text.contains("four") && text.contains("clubs"))   { cardImageView.setImageResource(R.drawable.clubs_4); }
            else if (text.contains("five") && text.contains("clubs"))   { cardImageView.setImageResource(R.drawable.clubs_5); }
            else if (text.contains("six") && text.contains("clubs"))    { cardImageView.setImageResource(R.drawable.clubs_6); }
            else if (text.contains("seven") && text.contains("clubs"))  { cardImageView.setImageResource(R.drawable.clubs_7); }
            else if (text.contains("eight") && text.contains("clubs"))  { cardImageView.setImageResource(R.drawable.clubs_8); }
            else if (text.contains("nine") && text.contains("clubs"))   { cardImageView.setImageResource(R.drawable.clubs_9); }
            else if (text.contains("ten") && text.contains("clubs"))    { cardImageView.setImageResource(R.drawable.clubs_10); }
            else if (text.contains("jack") && text.contains("clubs"))   { cardImageView.setImageResource(R.drawable.clubs_j); }
            else if (text.contains("queen") && text.contains("clubs"))  { cardImageView.setImageResource(R.drawable.clubs_q); }
            else if (text.contains("king") && text.contains("clubs"))   { cardImageView.setImageResource(R.drawable.clubs_k); }
            else if (text.contains("ace") && text.contains("hearts"))   { cardImageView.setImageResource(R.drawable.hearts_a); }
            else if (text.contains("two") && text.contains("hearts"))   { cardImageView.setImageResource(R.drawable.hearts_2); }
            else if (text.contains("three") && text.contains("hearts")) { cardImageView.setImageResource(R.drawable.hearts_3); }
            else if (text.contains("four") && text.contains("hearts"))  { cardImageView.setImageResource(R.drawable.hearts_4); }
            else if (text.contains("five") && text.contains("hearts"))  { cardImageView.setImageResource(R.drawable.hearts_5); }
            else if (text.contains("six") && text.contains("hearts"))   { cardImageView.setImageResource(R.drawable.hearts_6); }
            else if (text.contains("seven") && text.contains("hearts")) { cardImageView.setImageResource(R.drawable.hearts_7); }
            else if (text.contains("eight") && text.contains("hearts")) { cardImageView.setImageResource(R.drawable.hearts_8); }
            else if (text.contains("nine") && text.contains("hearts"))  { cardImageView.setImageResource(R.drawable.hearts_9); }
            else if (text.contains("ten") && text.contains("hearts"))   { cardImageView.setImageResource(R.drawable.hearts_10); }
            else if (text.contains("jack") && text.contains("hearts"))  { cardImageView.setImageResource(R.drawable.hearts_j); }
            else if (text.contains("queen") && text.contains("hearts")) { cardImageView.setImageResource(R.drawable.hearts_q); }
            else if (text.contains("king") && text.contains("hearts"))  { cardImageView.setImageResource(R.drawable.hearts_k); }
            else if (text.contains("ace") && text.contains("diamonds")) { cardImageView.setImageResource(R.drawable.diamonds_a); }
            else if (text.contains("two") && text.contains("diamonds")) { cardImageView.setImageResource(R.drawable.diamonds_2); }
            else if (text.contains("three") && text.contains("diamonds"))   { cardImageView.setImageResource(R.drawable.diamonds_3); }
            else if (text.contains("four") && text.contains("diamonds"))    { cardImageView.setImageResource(R.drawable.diamonds_4); }
            else if (text.contains("five") && text.contains("diamonds"))    { cardImageView.setImageResource(R.drawable.diamonds_5); }
            else if (text.contains("six") && text.contains("diamonds"))     { cardImageView.setImageResource(R.drawable.diamonds_6); }
            else if (text.contains("seven") && text.contains("diamonds"))   { cardImageView.setImageResource(R.drawable.diamonds_7); }
            else if (text.contains("eight") && text.contains("diamonds"))   { cardImageView.setImageResource(R.drawable.diamonds_8); }
            else if (text.contains("nine") && text.contains("diamonds"))    { cardImageView.setImageResource(R.drawable.diamonds_9); }
            else if (text.contains("ten") && text.contains("diamonds"))     { cardImageView.setImageResource(R.drawable.diamonds_10); }
            else if (text.contains("jack") && text.contains("diamonds"))    { cardImageView.setImageResource(R.drawable.diamonds_j); }
            else if (text.contains("queen") && text.contains("diamonds"))   { cardImageView.setImageResource(R.drawable.diamonds_q); }
            else if (text.contains("king") && text.contains("diamonds"))    { cardImageView.setImageResource(R.drawable.diamonds_k); }
        }
    }

    @Override
    public void onBeginningOfSpeech() {
    }

    /**
     * We stop recognizer here to get a final result
     */
    @Override
    public void onEndOfSpeech() {
        if (!recognizer.getSearchName().equals(KWS_SEARCH))
            switchSearch(KWS_SEARCH);
    }

    private void switchSearch(String searchName) {
        recognizer.stop();

        // If we are not spotting, start listening with timeout (10000 ms or 10 seconds).
        if (searchName.equals(KWS_SEARCH))
            recognizer.startListening(searchName);
        else
            recognizer.startListening(searchName, 30000);

        String caption = getResources().getString(captions.get(searchName));
        ((TextView) findViewById(R.id.caption_text)).setText(caption);
    }

    private void setupRecognizer(File assetsDir) throws IOException {
        // The recognizer can be configured to perform multiple searches
        // of different kind and switch between them

        recognizer = SpeechRecognizerSetup.defaultSetup()
                .setAcousticModel(new File(assetsDir, "en-us-ptm"))
                .setDictionary(new File(assetsDir, "cmudict-en-us.dict"))

                .setRawLogDir(assetsDir) // To disable logging of raw audio comment out this call (takes a lot of space on the device)
                .setKeywordThreshold(1e-45f) // Threshold to tune for keyphrase to balance between false alarms and misses
                .setBoolean("-allphone_ci", true)  // Use context-independent phonetic search, context-dependent is too slow for mobile


                .getRecognizer();
        recognizer.addListener(this);

        /** In your application you might not need to add all those searches.
         * They are added here for demonstration. You can leave just one.
         */

        // Create keyword-activation search.
        recognizer.addKeyphraseSearch(KWS_SEARCH, KEYPHRASE);

        // Create grammar-based search for selection between demos
        File menuGrammar = new File(assetsDir, "menu.gram");
        recognizer.addGrammarSearch(MENU_SEARCH, menuGrammar);

    }

    @Override
    public void onError(Exception error) {
        ((TextView) findViewById(R.id.caption_text)).setText(error.getMessage());
    }

    @Override
    public void onTimeout() {
        switchSearch(KWS_SEARCH);
    }
}
