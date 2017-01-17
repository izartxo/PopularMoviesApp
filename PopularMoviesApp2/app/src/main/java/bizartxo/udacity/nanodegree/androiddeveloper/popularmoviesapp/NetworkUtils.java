package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by izartxo on 1/16/17.
 */

public class NetworkUtils {

    final static String MOVIEDB_BASE_URL_IMAGE =
            "http://image.tmdb.org/t/p/";

    final static String IMAGE_SIZE_185 = "w185";
    final static String IMAGE_SIZE_342 = "w342";
    final static String IMAGE_SIZE_500 = "w500";

    final static String MOVIEDB_BASE_URL = "http://api.themoviedb.org";

    final static String POPULAR_PATH = "/3/movie/popular";
    final static String TOPRATED_PATH = "/3/movie/top_rated";

    static String mApiKey = "";

    /**
     * Builds the URL used to query MovieDB.
     *
     * @param top_rated true popular false
     * @return The URL to use to query the weather server.
     */
    public static URL buildUrl(boolean top_rated) {
        Uri builtUri = null;
        if (top_rated) {
            builtUri = Uri.parse(MOVIEDB_BASE_URL + TOPRATED_PATH).buildUpon()
                    .appendQueryParameter("api_key", mApiKey)
                    .build();
        }else{
            builtUri = Uri.parse(MOVIEDB_BASE_URL + POPULAR_PATH).buildUpon()
                    .appendQueryParameter("api_key", mApiKey)
                    .build();
        }
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    // Method to check Internet connection
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    // Set APIKEY to work with it
    public static void setApiKey(String apikey){
        mApiKey = apikey;
    }
}
