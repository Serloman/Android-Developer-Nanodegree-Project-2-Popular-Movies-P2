package com.serloman.popularmovies.movieDetails;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.models.Movie;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MovieDetailsActivity extends AppCompatActivity {

    public static final String ARG_MOVIE_DATA = "ARG_MOVIE_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);

        if(isFullMode())
            initFullMovieFragment();
        else
            initMovieFragment();
    }

    private void initMovieFragment(){
        MovieDetailsFragment movieFragment = MovieDetailsFragment.newInstance(getMovieData());

        getSupportFragmentManager().beginTransaction().replace(R.id.container, movieFragment).commit();
    }

    private void initFullMovieFragment(){
        FullMovieDetailsFragment movieFragment = FullMovieDetailsFragment.newInstance(getMovieData());

        getSupportFragmentManager().beginTransaction().replace(R.id.containerFull, movieFragment).commit();
    }

    private Movie getMovieData(){
        return this.getIntent().getExtras().getParcelable(ARG_MOVIE_DATA);
    }

    private boolean isFullMode(){
        return findViewById(R.id.containerFull)!=null;
    }
}
