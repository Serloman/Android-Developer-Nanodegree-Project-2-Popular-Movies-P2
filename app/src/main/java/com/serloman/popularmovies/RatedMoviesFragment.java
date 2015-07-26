package com.serloman.popularmovies;

import android.os.Bundle;
import android.support.v4.content.Loader;

import com.serloman.popularmovies.movieList.BasicMovieListFragment;
import com.serloman.themoviedb_api.models.Movie;

import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class RatedMoviesFragment extends BasicMovieListFragment{

    public static RatedMoviesFragment newInstance(){
        return newInstance(2, 4);
    }

    public static RatedMoviesFragment newInstance(int spanCount, int landscapeSpanCount){
        RatedMoviesFragment fragment = new RatedMoviesFragment();

        Bundle args = new Bundle();
        args.putInt(BasicMovieListFragment.ARG_PORTRAIT_SPAN_COUNT, spanCount);
        args.putInt(BasicMovieListFragment.ARG_LANDSCAPE_SPAN_COUNT, landscapeSpanCount);
        fragment.setArguments(args);

        return fragment;
    }

    public RatedMoviesFragment() { }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new TopRatedMoviesLoader(getActivity());
    }
}
