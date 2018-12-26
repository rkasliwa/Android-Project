package rkasliwa.cmu.edu.textanalytics;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class GetAnalytics extends AppCompatActivity {
    MainActivity ma = null;

    public void search(String input, MainActivity ma) {
        Log.d("execute", "search called");

        this.ma = ma;
        new AsyncAnalyzeText().execute(input);
    }

    private class AsyncAnalyzeText extends AsyncTask<String, Void, JSONObject> {
        protected JSONObject doInBackground(String... urls) {
            JSONObject searchRes = null;

            try {
                searchRes = search(urls[0]);
                Log.d("execute", searchRes.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return searchRes;
        }

        protected void onPostExecute(JSONObject analysis) {
            System.out.println(analysis);
            try {
                String language = (String) analysis.get("Language");

                TextView langText = (TextView) findViewById(R.id.language);
                langText.setText(language);
                langText.setVisibility(View.VISIBLE);

                JSONArray phrases = (JSONArray) analysis.get("Phrases");
                ArrayList<String> phra = new ArrayList<>();

                for (int i = 0; i < phrases.length(); i++) {
                    phra.add(phrases.get(i).toString());
                }

                double score = (Double) analysis.get("Sentiment");

                String mood = (String) analysis.get("Mood");



                setTextViews(language, phra);
                setGraphView(score);
                setMoodView(mood);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        public void setTextViews(String language, ArrayList phrases){

            /*TextView langText = (TextView) findViewById(R.id.languageText);
            langText.setText(language);
            langText.setVisibility(View.VISIBLE);*/

            /*String phras = "";
            for(int i = 0; i < phrases.size()-1; i++){
                phras = phras + phrases.get(i) + ",";
            }
            phras = phras + phrases.get(phrases.size());
            TextView p = findViewById(R.id.phra);
            p.setText(phras);
            p.setVisibility(View.VISIBLE);*/
        }

        public void setMoodView(String mood){
            /*String moodIs = "You are currently feeling " + mood;
            TextView m = findViewById(R.id.mood);
            m.setText(moodIs);

            ImageView imageView = (ImageView)findViewById(R.id.image);
            if(mood == "Extremely Sad"){
                imageView.setImageResource(R.drawable.extremely_sad);
            }
            else if(mood == "Sad"){
                imageView.setImageResource(R.drawable.sad);
            }
            else if(mood == "Neutral"){
                imageView.setImageResource(R.drawable.neutral);
            }
            else if(mood == "Happy"){
                imageView.setImageResource(R.drawable.happy);
            }
            else{
                imageView.setImageResource(R.drawable.extremely_happy);
            }
            imageView.setVisibility(View.VISIBLE);*/
        }

        public void setGraphView(double score){
        /*
        //stackoverflow code:

            DecoView arcView = findViewById(R.id.dynamicArcView);
            float sco = (float) score;
            // Create background track
            arcView.addSeries(new SeriesItem.Builder(Color.argb(255, 218, 218, 218))
                    .setRange(0, 100, 100)
                    .setInitialVisibility(false)
                    .setLineWidth(32f)
                    .build());

            //Create data series track
            SeriesItem seriesItem1 = new SeriesItem.Builder(Color.argb(255, 64, 196, 0))
                    .setRange(0, 100, 0)
                    .setSeriesLabel(new SeriesLabel.Builder("Sentiment is: %.0f%%")
                            .setColorBack(Color.argb(218, 0, 0, 0))
                            .setColorText(Color.argb(255, 255, 255, 255))
                            .build())
                    .build();

            //.setLineWidth(32f)
            int series1Index = arcView.addSeries(seriesItem1);

            arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                    .setDelay(1000)
                    .setDuration(2000)
                    .build());

            arcView.addEvent(new DecoEvent.Builder(sco).setIndex(series1Index).setDelay(4000).build());
            */
        }

        private JSONObject search(String searchTerm) throws MalformedURLException, JSONException {


            Log.d("execute", "GetAnalytics search called");
            String textAnalytics = "";
            String response = "";
            try {
                URL openUrl = new URL("https://docker-fkbgzcxjmn.now.sh/GetTextInsights?input=" + searchTerm);
                HttpURLConnection connection = (HttpURLConnection) openUrl.openConnection();

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((textAnalytics = reader.readLine()) != null) {
                    response = response + textAnalytics;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            JSONObject jresponse = new JSONObject(response);
            Log.d("execute", jresponse.toString());
            return jresponse;
        }

    }
}
