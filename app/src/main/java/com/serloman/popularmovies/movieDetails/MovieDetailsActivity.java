package com.serloman.popularmovies.movieDetails;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;

import com.serloman.popularmovies.R;
import com.serloman.popularmovies.reviews.ReviewsActivity;
import com.serloman.popularmovies.movieDetails.MovieDetailsFragment.MovieDetailsListener;
import com.serloman.themoviedb_api.models.Movie;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity implements MovieDetailsListener{

    public static final String ARG_MOVIE_DATA = "ARG_MOVIE_DATA";

    private static final String STATE_DEFAULT_MOVIE = "STATE_DEFAULT_MOVIE";
    private static final String STATE_FULL_MOVIE = "STATE_FULL_MOVIE";

    private boolean initDefaultMovie;
    private boolean initFullMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        initDefaultMovie = false;
        initFullMovie = false;

        if(savedInstanceState!=null){
            initDefaultMovie = savedInstanceState.getBoolean(STATE_DEFAULT_MOVIE, false);
            initFullMovie = savedInstanceState.getBoolean(STATE_FULL_MOVIE, false);
        }

        if(isFullMode())
            initFullMovieFragment();
        else
            initMovieFragment();
    }

    private void initMovieFragment(){
        if(initDefaultMovie)
            return;

        MovieDetailsFragment movieFragment = MovieDetailsFragment.newInstance(getMovieData());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, movieFragment).commit();

        initDefaultMovie = true;
    }

    private void initFullMovieFragment(){
        if(initFullMovie)
            return;

        FullMovieDetailsFragment movieFragment = FullMovieDetailsFragment.newInstance(getMovieData());
        getSupportFragmentManager().beginTransaction().replace(R.id.containerFull, movieFragment).commit();

        initFullMovie = true;
    }

    private Movie getMovieData(){
        return this.getIntent().getExtras().getParcelable(ARG_MOVIE_DATA);
    }

    private boolean isFullMode(){
        return findViewById(R.id.containerFull)!=null;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean(STATE_DEFAULT_MOVIE, initDefaultMovie);
        outState.putBoolean(STATE_FULL_MOVIE, initFullMovie);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onScoreClicked(Movie movie) {
//        boolean fullMode = isFullMode();
//        if(!fullMode || (fullMode && !isLandscape()))
            openReviewsActivity(movie);
    }

    private void openReviewsActivity(Movie movie){
        Intent intent = new Intent(this, ReviewsActivity.class);
        intent.putExtra(ReviewsActivity.ARG_MOVIE, (Parcelable) movie);
        startActivity(intent);
    }

    private boolean isLandscape(){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }
}
