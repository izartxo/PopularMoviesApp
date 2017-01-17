package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by izartxo on 1/16/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private int mNumberItems;
    Context context;
    ArrayList<MovieData> mMovieDataList;

    private ListItemClickListener mOnClickListener;

    public MovieAdapter(int numberOfItems, ArrayList<MovieData> moviedatalist, ListItemClickListener listener){
        mNumberItems = numberOfItems;
        mMovieDataList = moviedatalist;
        mOnClickListener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_rv_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentInmediately = false;

        View view  = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentInmediately);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view, mMovieDataList.get(viewType));
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(mMovieDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


    //
    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imageView; // Poster on RecyclerView
        TextView textView; // Title

        public MovieViewHolder(View itemView){
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        void bind(MovieData moviedata){
            //  Get poster
            String picasourl = NetworkUtils.MOVIEDB_BASE_URL_IMAGE + NetworkUtils.IMAGE_SIZE_185 + moviedata.getMovie_poster();
            Picasso.with(context).load(picasourl).into(imageView);

            textView.setText(moviedata.getTitle());
            textView.setVisibility(View.INVISIBLE);
            // Few seconds to show the poster before title appears
            textView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setVisibility(View.VISIBLE);
                }
            }, 3000);

        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }

    //
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    //

    public MovieData getItem(int position){
        return mMovieDataList.get(position);
    }
}

