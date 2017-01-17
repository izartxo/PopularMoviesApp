package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

/**
 * Created by izartxo on 1/16/17.
 */

public class AsyncMovieData extends AsyncTask<URL, Void, String> {

    String movieDBSearchResults = "";
    UiActivity.OnMovieDataReceived gateway;

    AsyncMovieData(UiActivity.OnMovieDataReceived onMovieDataReceived){
        gateway = onMovieDataReceived;
    }

    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];

        try {
            movieDBSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movieDBSearchResults;

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("----------------->", "Data: " + movieDBSearchResults.toString());
        gateway.loadMovieData(s);

    }



}

