package com.example.menukorvet.utils;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String URL_ROOT = "https://canteen.korvet-jsc.ru/driver.php",

                                PARAMS_LINK = "_link",
                                PARAMS_EXEC = "_exec",
                                PARAMS_SHORT = "_short",

                                LINK = "canteen",
                                EXEC = "web_list";

    private enum Short {
        //имена _0, _1, _2
        INDEXES {
            @Override
            String get() {
                return "1";
            }
        },

        //имена name, zena, abk
        NAMES {
            @Override
            String get() {
                return "2";
            }
        };

        abstract String get();
    }

    private static URL buildURL(Short sh) {

        URL url = null;

        Uri uri = Uri.parse(URL_ROOT).buildUpon()
                  .appendQueryParameter(PARAMS_LINK, LINK)
                  .appendQueryParameter(PARAMS_EXEC, EXEC)
                  .appendQueryParameter(PARAMS_SHORT, sh.get())
                  .build();

        try {
            url = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static JSONObject loadData() {

        URL url = buildURL(Short.NAMES);
        JSONObject result = null;

        try {
            result = new LoadDataTask().execute(url).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }

    private static class LoadDataTask extends AsyncTask<URL, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(URL... urls) {

            JSONObject result = null;

            if (Objects.nonNull(urls) && urls.length > 0) {

                HttpURLConnection httpURLConnection = null;
                StringBuilder builder = new StringBuilder();

                try {
                    httpURLConnection = (HttpURLConnection) urls[0].openConnection();
                    InputStream stream = httpURLConnection.getInputStream();
                    InputStreamReader reader = new InputStreamReader(stream);
                    BufferedReader buffer = new BufferedReader(reader);

                    String line;

                    while (Objects.nonNull(line = buffer.readLine())) {
                        builder.append(line).append("\n");
                    }

                    result = new JSONObject(builder.toString());

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                finally {
                    if (Objects.nonNull(httpURLConnection)) {
                        httpURLConnection.disconnect();
                    }
                }
            }

            return result;
        }
    }


}
