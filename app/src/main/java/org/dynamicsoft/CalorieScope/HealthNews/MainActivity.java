package org.dynamicsoft.CalorieScope.HealthNews;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private ImageView articlePicture;

    // URL to get news JSON
    private static String url = "https://newsapi.org/v2/top-headlines?category=health&country=us&apiKey=48954d80af324c2fa16b6cb19c2ef6bd";

    ArrayList<HashMap<String, String>> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list);
        articlePicture = findViewById(R.id.articleImage);

        new Getnews().execute();
    }

    /**
     * Async task class to get json by making HTTP call
     */
    private class Getnews extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray articles = jsonObj.getJSONArray("articles");

                    // looping through All news
                    for (int i = 0; i < articles.length(); i++) {
                        JSONObject c = articles.getJSONObject(i);

                        String title = c.getString("title");
                        String urlToImage = c.getString("urlToImage");
                        String description = c.getString("description");
                        String url = c.getString("url");

                        // Phone node is JSON Object

                        String author = c.getString("author");

                        // tmp hash map for single newsitem
                        HashMap<String, String> newsitem = new HashMap<>();

                        // adding each child node to HashMap key => value
                        newsitem.put("title", title);
                        newsitem.put("urlToImage", urlToImage);
                        newsitem.put("description", description);
                        newsitem.put("url", url);
                     //   newsitem.put("author",author);

                        // adding newsitem to newsitem list
                        newsList.add(newsitem);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            /**
             * Updating parsed JSON data into ListView
             * */
            ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, newsList,
                    R.layout.list_item, new String[]{"title", "urlToImage",
                    "description","url"}, new int[]{R.id.title,
                    R.id.articleImage, R.id.description, R.id.url});

            lv.setAdapter(adapter);
        }
    }
}