package com.serloman.popularmovies;

import android.content.Context;

import com.serloman.popularmovies.data.FavouriteMovies;
import com.serloman.themoviedb_api.models.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class FavouriteMoviesLoader  extends MoviesLoader {
    public FavouriteMoviesLoader(Context context) {
        this(context, 1);
    }

    public FavouriteMoviesLoader(Context context, int page) {
        super(context, page);
    }

    @Override
    public List<Movie> loadInBackground() {
        FavouriteMovies fav = new FavouriteMovies(getContext());

        return fav.getFavouriteMovies();
    }
}