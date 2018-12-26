package rkasliwa.cmu.edu.textanalytics;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.charts.SeriesLabel;
import com.hookedonplay.decoviewlib.events.DecoEvent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //ac
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * The click listener will need a reference to this object, so that upon successfully finding a picture from Flickr, it
         * can callback to this object with the resulting picture Bitmap.  The "this" of the OnClick will be the OnClickListener, not
         * this InterestingPicture.
         */
        final MainActivity ma = this;


        //analyze text button
        Button analyzeButton = findViewById(R.id.analyze);
        // Add a listener to the analyze button
        analyzeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    String input = ((EditText) findViewById(R.id.analyzeText)).getText().toString();
                    GetAnalytics at = new GetAnalytics();
                    at.search(input, ma);
                    // Done asynchronously in another thread.  It calls ip.pictureReady() in this thread when complete.

                }
        });
    }
}
