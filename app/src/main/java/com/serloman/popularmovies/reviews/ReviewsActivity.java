package com.serloman.popularmovies.reviews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.serloman.popularmovies.R;
import com.serloman.popularmovies.cast.CastFragment;
import com.serloman.themoviedb_api.models.Movie;

/**
 * Created by Serloman on 29/07/2015.
 */
public class ReviewsActivity extends AppCompatActivity {

    public final static String ARG_MOVIE = "ARG_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.reviews_activity);

        init();
    }

    private void init(){
        initToolbar();
        initReviews();
    }

    private void initToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.castToolbar);

        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initReviews(){
        ReviewsFragment fragment = ReviewsFragment.newInstance(getMovie());
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
    }

    private Movie getMovie(){
        return getIntent().getExtras().getParcelable(ARG_MOVIE);
    }
}
