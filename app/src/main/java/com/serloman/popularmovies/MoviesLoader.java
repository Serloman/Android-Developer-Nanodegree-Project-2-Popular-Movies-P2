package com.serloman.popularmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.serloman.themoviedb_api.TheMovieDb_Api;
import com.serloman.themoviedb_api.models.Movie;

import java.util.List;

/**
 * Created by Serloman on 21/07/2015.
 */
public abstract class MoviesLoader extends AsyncTaskLoader<List<Movie>> {

    private int mPage;

    public MoviesLoader(Context context) {
        this(context, 1);
    }

    public MoviesLoader(Context context, int page) {
        super(context);

        this.mPage = page;

        onContentChanged();
    }

    public int getPage(){
        return mPage;
    }

    public void nextPage(){
        loadPage(mPage++);
    }

    public void loadPage(int page){
        this.mPage = page;

        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
