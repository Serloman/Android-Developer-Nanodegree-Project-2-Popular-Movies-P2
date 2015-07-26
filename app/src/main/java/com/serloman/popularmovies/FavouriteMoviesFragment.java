package com.serloman.popularmovies;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.serloman.popularmovies.data.FavouriteMovies;
import com.serloman.popularmovies.movieList.BasicMovieListFragment;
import com.serloman.themoviedb_api.models.Movie;

import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class FavouriteMoviesFragment extends BasicMovieListFragment implements LoaderManager.LoaderCallbacks<List<Movie>> {

    public static FavouriteMoviesFragment newInstance() {
        return newInstance(2, 4);
    }

    public static FavouriteMoviesFragment newInstance(int portraitSpanCount, int landscapeSpanCount) {
        FavouriteMoviesFragment fragment = new FavouriteMoviesFragment();

        Bundle args = new Bundle();
        args.putInt(BasicMovieListFragment.ARG_PORTRAIT_SPAN_COUNT, portraitSpanCount);
        args.putInt(BasicMovieListFragment.ARG_LANDSCAPE_SPAN_COUNT, landscapeSpanCount);
        fragment.setArguments(args);

        return fragment;
    }

    public FavouriteMoviesFragment() {

    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return new FavouriteMoviesLoader(getActivity());
    }

    @Override
    public void onLoadPage(int numPage) {

    }

    @Override
    public void onError(Exception ex) {

    }
}