package com.serloman.popularmovies;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.serloman.themoviedb_api.TheMovieDb_Api;
import com.serloman.themoviedb_api.models.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 21/07/2015.
 */
public class DiscoverMoviesLoader extends MoviesLoader {

    private TheMovieDb_Api.Short_By mShortBy;

    public DiscoverMoviesLoader(Context context, TheMovieDb_Api.Short_By shortBy) {
        this(context, 1, shortBy);
    }

    public DiscoverMoviesLoader(Context context, int page, TheMovieDb_Api.Short_By shortBy) {
        super(context, page);

        this.mShortBy = shortBy;

        onContentChanged();
    }

    @Override
    public List<Movie> loadInBackground() {
        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getContext());

        try {
            return api.discoverMovies(getPage(), mShortBy);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
