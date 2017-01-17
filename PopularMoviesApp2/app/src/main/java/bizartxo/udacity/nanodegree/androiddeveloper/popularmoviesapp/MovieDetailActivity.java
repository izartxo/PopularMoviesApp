package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by izartxo on 1/16/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private TextView tvtitle, tvreleasedate, tvvoteaverage, tvsynopsis, tverror;
    private ImageView ivposter, ivheader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // View instances
        tverror = (TextView) findViewById(R.id.tverror);
        tvtitle = (TextView) findViewById(R.id.tvtitle);
        tvreleasedate = (TextView) findViewById(R.id.tvreleasedate);
        tvvoteaverage = (TextView) findViewById(R.id.tvvoteaverage);
        tvsynopsis = (TextView) findViewById(R.id.tvsynopsis);
        ivposter = (ImageView) findViewById(R.id.ivposter);
        ivheader = (ImageView) findViewById(R.id.ivheader);

        Intent intent = getIntent();

        fillViewsMovieDetailedData(intent);

    }

    void fillViewsMovieDetailedData(Intent intent){
        if (NetworkUtils.isNetworkAvailable(getApplicationContext())) {
            if (intent.hasExtra("title")) {
                // Show views if they are invisible because of previous error
                showDefaultView();

                String title = intent.getStringExtra("title");
                String release_date = intent.getStringExtra("release_date");
                String vote_average = intent.getStringExtra("vote_average");
                String synopsis = intent.getStringExtra("synopsis");
                String movie_poster = intent.getStringExtra("movie_poster");


                tvtitle.setText(title);

                tvreleasedate.append(release_date);

                tvvoteaverage.append(vote_average);

                tvsynopsis.append("\n" + synopsis);

                Picasso.with(getApplicationContext()).load(NetworkUtils.MOVIEDB_BASE_URL_IMAGE + NetworkUtils.IMAGE_SIZE_342 + movie_poster).fit().centerCrop().into(ivheader);
                Picasso.with(getApplicationContext()).load(NetworkUtils.MOVIEDB_BASE_URL_IMAGE + NetworkUtils.IMAGE_SIZE_342 + movie_poster).into(ivposter);

            } else {
                // No extras!
                showDefaultError(getString(R.string.error_message_default));
            }
        } else {
            showDefaultError(getString(R.string.error_message_network));
        }
    }

    void showDefaultError(String error){

        tvtitle.setVisibility(View.INVISIBLE);
        tvreleasedate.setVisibility(View.INVISIBLE);
        tvsynopsis.setVisibility(View.INVISIBLE);
        tvvoteaverage.setVisibility(View.INVISIBLE);
        ivheader.setVisibility(View.INVISIBLE);
        ivposter.setVisibility(View.INVISIBLE);

        tverror.setText(error);
        tverror.setVisibility(View.VISIBLE);
    }

    void showDefaultView(){
        tverror.setVisibility(View.GONE);

        tvtitle.setVisibility(View.VISIBLE);
        tvreleasedate.setVisibility(View.VISIBLE);
        tvsynopsis.setVisibility(View.VISIBLE);
        tvvoteaverage.setVisibility(View.VISIBLE);
        ivheader.setVisibility(View.VISIBLE);
        ivposter.setVisibility(View.VISIBLE);
    }
}
