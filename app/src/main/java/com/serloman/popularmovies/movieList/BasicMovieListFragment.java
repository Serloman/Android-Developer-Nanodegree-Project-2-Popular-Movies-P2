package com.serloman.popularmovies.movieList;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.serloman.popularmovies.EndlessScrollListener;
import com.serloman.popularmovies.EndlessScrollListener.OnEndlessScrollListener;
import com.serloman.popularmovies.MoviesLoader;
import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.calls.MovieListCallback;
import com.serloman.themoviedb_api.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public abstract class BasicMovieListFragment extends Fragment implements MovieListCallback, LoaderManager.LoaderCallbacks<List<Movie>>, MoviesAdapter.MovieSelectedListener, OnEndlessScrollListener {

    public interface OpenMovieListener{
        void openMovie(Movie movie);
    }

    public static final String ARG_PORTRAIT_SPAN_COUNT = "ARG_PORTRAIT_SPAN_COUNT";
    public static final String ARG_LANDSCAPE_SPAN_COUNT = "ARG_LANDSCAPE_SPAN_COUNT";
    public static final int LOADER_ID = 0;

    public BasicMovieListFragment(){ }

    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;
    private OpenMovieListener mListener;
    private EndlessScrollListener mEndlessScrollListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_grid_fragment, container, false);

        initRecyclerGridView(rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            mListener = (OpenMovieListener)activity;
        }catch (ClassCastException ex){
            throw new ClassCastException(activity.toString() + " must implement BasicMovieListFragment.OpenMovieListener interface");
        }
    }

    private void initRecyclerGridView(View rootView){
        mEndlessScrollListener = new EndlessScrollListener(this);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.movieGridRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), getSpanColumn()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new MovieDecoration(getSpanColumn(), 20));
        mRecyclerView.addOnScrollListener(mEndlessScrollListener);

        mMoviesAdapter = new MoviesAdapter(getActivity(), new ArrayList<Movie>(), this);
        mRecyclerView.setAdapter(mMoviesAdapter);
    }

    private int getPortraitSpanCount(){
        return this.getArguments().getInt(ARG_PORTRAIT_SPAN_COUNT, 2);
    }

    private int getLandscapeSpanCount(){
        return this.getArguments().getInt(ARG_LANDSCAPE_SPAN_COUNT, 4);
    }

    private int getSpanColumn(){
        if(getResources().getConfiguration().orientation==Configuration.ORIENTATION_LANDSCAPE)
            return getLandscapeSpanCount();
        return getPortraitSpanCount();
    }

    @Override
    public void onLoadPage(int numPage) {
        Loader loader = getLoaderManager().getLoader(LOADER_ID);
        MoviesLoader moviesLoader = (MoviesLoader)loader;

        getView().findViewById(R.id.movieGridNextPageLoading).setVisibility(View.VISIBLE);
        moviesLoader.loadPage(numPage);
    }

    @Override
    public void onMovieListDataReceived(List<Movie> movies) {
        if(movies.size()<1)
            onError(null);

        mMoviesAdapter.addMoreMovies(movies);

        getView().findViewById(R.id.movieGridProgressBar).setVisibility(View.GONE);
        getView().findViewById(R.id.movieGridNextPageLoading).setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception ex) {
        mEndlessScrollListener.pageLoadFailed();
        Toast.makeText(getActivity(), getActivity().getString(R.string.notify_user_network_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMovieSelected(Movie movie) {
        mListener.openMovie(movie);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        onMovieListDataReceived(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
