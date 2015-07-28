package com.serloman.popularmovies.movieDetails;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serloman.popularmovies.R;
import com.serloman.popularmovies.models.ParcelableDiscoverMovie;
import com.serloman.popularmovies.reviews.ReviewsFragment;
import com.serloman.themoviedb_api.models.Movie;

/**
 * Created by Serloman on 28/07/2015.
 */
public class FullMovieDetailsFragment extends Fragment {

    public final static String ARG_MOVIE = "ARG_MOVIE";

    public static FullMovieDetailsFragment newInstance(Movie movie){
        FullMovieDetailsFragment fragment = new FullMovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, new ParcelableDiscoverMovie(movie));
        fragment.setArguments(args);

        return fragment;
    }

    public FullMovieDetailsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.full_movie_fragment, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initDetails();
        initReviews();
    }

    private void initDetails(){
        MovieDetailsFragment details = MovieDetailsFragment.newInstance(getMovie());
        updateFragment(details, R.id.fullMovieDetails);
    }

    private void initReviews(){
        ReviewsFragment reviews = ReviewsFragment.newInstance(getMovie());
        updateFragment(reviews, R.id.fullMovieReviews);
    }

    private void updateFragment(Fragment fragment, int layoutId){
        getChildFragmentManager().beginTransaction().replace(layoutId, fragment).commit();
    }

    private Movie getMovie(){
        return getArguments().getParcelable(ARG_MOVIE);
    }
}
