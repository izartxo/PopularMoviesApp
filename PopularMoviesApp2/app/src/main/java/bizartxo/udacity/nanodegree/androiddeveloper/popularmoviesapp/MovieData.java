package bizartxo.udacity.nanodegree.androiddeveloper.popularmoviesapp;

/**
 * Created by izartxo on 1/16/17.
 */

public class MovieData {

    String id; // id
    String title; // title
    String release_date; // release_date
    String movie_poster; // poster_path
    String vote_average; // vote_average
    String plot_synopsis; // overview

    MovieData(){
        id = "";
        title = "";
        release_date = "";
        movie_poster = "";
        vote_average = "";
        plot_synopsis = "";
    }

    MovieData(String lid, String ltitle, String lrelease_date, String lmovie_poster, String lvote_average, String lplot_synopsis){
        id = lid;
        title = ltitle;
        release_date = lrelease_date;
        movie_poster = lmovie_poster;
        vote_average = lvote_average;
        plot_synopsis = lplot_synopsis;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getMovie_poster() {
        return movie_poster;
    }

    public void setMovie_poster(String movie_poster) {
        this.movie_poster = movie_poster;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getPlot_synopsis() {
        return plot_synopsis;
    }

    public void setPlot_synopsis(String plot_synopsis) {
        this.plot_synopsis = plot_synopsis;
    }
}
