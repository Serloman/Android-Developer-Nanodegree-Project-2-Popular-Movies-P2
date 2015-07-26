package com.serloman.popularmovies;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.serloman.popularmovies.movieList.BasicMovieListFragment;
import com.serloman.themoviedb_api.models.Movie;

import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class PopularMoviesFragment extends BasicMovieListFragment implements LoaderManager.LoaderCallbacks<List<Movie>>{

    public static PopularMoviesFragment newInstance(){
        return newInstance(2, 4);
    }

    public static PopularMoviesFragment newInstance(int portraitSpanCount, int landscapeSpanCount){
        PopularMoviesFragment fragment = new PopularMoviesFragment();

        Bundle args = new Bundle();
        args.putInt(BasicMovieListFragment.ARG_PORTRAIT_SPAN_COUNT, portraitSpanCount);
        args.putInt(BasicMovieListFragment.ARG_LANDSCAPE_SPAN_COUNT, landscapeSpanCount);
        fragment.setArguments(args);

        return fragment;
    }

    public PopularMoviesFragment() { }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new PopularMoviesLoader(getActivity());
    }

}
