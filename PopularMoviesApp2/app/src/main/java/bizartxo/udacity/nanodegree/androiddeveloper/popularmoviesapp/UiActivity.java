package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

public class UiActivity extends AppCompatActivity {

    // Textview that show error if not network connection available
    TextView tverror;

    // Recycler and adapter of MovieData objects
    RecyclerView rcmovie;
    MovieAdapter mAdapter;

    // MovieData list to store actual MovieData objects
    ArrayList<MovieData> mMovieDataList;

    // Interface with Ui Activity to event network data received
    OnMovieDataReceived onMovieDataReceived;

    // Manage clicks on Recyclerview and adapter
    MovieAdapter.ListItemClickListener onClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ui);

        // Load API KEY from strings id
        NetworkUtils.setApiKey(getString(R.string.api_key));

        onClickListener = new MovieAdapter.ListItemClickListener() {
            @Override
            public void onListItemClick(int clickedItemIndex) {

                // Get MovieData object to create new detailed activity
                fillMovieDetailActivity(clickedItemIndex);
            }
        };

        // View instances
        tverror = (TextView) findViewById(R.id.error_message);
        rcmovie = (RecyclerView) findViewById(R.id.rc_movie);

        // MovieData objects list instance
        mMovieDataList = new ArrayList<MovieData>();

        // RecyclerView with two columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(),2);

        rcmovie.setLayoutManager(gridLayoutManager);


        onMovieDataReceived = new OnMovieDataReceived(){
            @Override
            public void loadMovieData(String movieData) {
                // After received data list is updated
                updateMovieDataListFromNetwork(movieData);
            }
        };

        // Check if internet is available
        if (NetworkUtils.isNetworkAvailable(getApplicationContext()))
            // It starts with popular movies(false)
            populateMovieGrid(false);
        else
            showNetworkError();

    }

    // Populate grid selecting right sorting data
    private void populateMovieGrid(boolean top_rated){
        if (rcmovie.getVisibility()==View.INVISIBLE){
            tverror.setVisibility(View.INVISIBLE);
            rcmovie.setVisibility(View.VISIBLE);
        }

        // if not network connection from sd?
        URL url = null;
        if ( top_rated )
            url = NetworkUtils.buildUrl(top_rated);
        else
            url = NetworkUtils.buildUrl(top_rated);

        // Outside main thread obtain network data passing interface instance to notify ui activity
        AsyncMovieData asyncMovieData = new AsyncMovieData(onMovieDataReceived);

        asyncMovieData.execute(url);
    }



    // Declaring interface to manage received data when AsyncTask finish
    interface OnMovieDataReceived{
        void loadMovieData(String movieData);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ui_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if (NetworkUtils.isNetworkAvailable(getApplicationContext())){
                if (item.getTitle().equals("top rated")) {
                    populateMovieGrid(true); // false -> popular : true -> top rated
                    item.setTitle("popular");
                }
                else {
                    populateMovieGrid(false); // false -> popular : true -> top rated
                    item.setTitle("top rated");
                }
            }
            else
                showNetworkError();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // If connection error hide recyclerview and show the error
    void showNetworkError(){
        rcmovie.setVisibility(View.INVISIBLE);
        tverror.setVisibility(View.VISIBLE);

    }

    // Create the intent with all movie data to detail activity
    void fillMovieDetailActivity(int clickedItemIndex){
        MovieData movieData = mAdapter.getItem(clickedItemIndex);

        Intent i = new Intent(getApplicationContext(), MovieDetailActivity.class);
        i.putExtra("title", movieData.getTitle());
        i.putExtra("release_date", movieData.getRelease_date());
        i.putExtra("vote_average", movieData.getVote_average());
        i.putExtra("synopsis", movieData.getPlot_synopsis());
        i.putExtra("movie_poster", movieData.getMovie_poster());
        startActivity(i);
    }

    //
    void updateMovieDataListFromNetwork(String movieData){

        mMovieDataList.clear();
        try{
            JSONObject data = new JSONObject(movieData);
            JSONArray results = data.getJSONArray("results");
            for (int item = 0 ; item < results.length() ; item++){

                JSONObject localItem = results.getJSONObject(item);
                MovieData movieDataItem = new MovieData(localItem.getString("id"),
                        localItem.getString("title"),
                        localItem.getString("release_date"),
                        localItem.getString("poster_path"),
                        localItem.getString("vote_average"),
                        localItem.getString("overview"));
                mMovieDataList.add(movieDataItem);
            }
        }catch (JSONException je){
            je.printStackTrace();
        }


        mAdapter = new MovieAdapter(mMovieDataList.size(), mMovieDataList, onClickListener);
        rcmovie.setAdapter(mAdapter);
    }
}
