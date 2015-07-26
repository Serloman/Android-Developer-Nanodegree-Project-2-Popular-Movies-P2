package com.serloman.popularmovies;

import android.content.Context;

import com.serloman.themoviedb_api.models.Movie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 21/07/2015.
 */
public class TopRatedMoviesLoader extends MoviesLoader {
    public TopRatedMoviesLoader(Context context) {
        this(context, 1);
    }

    public TopRatedMoviesLoader(Context context, int page) {
        super(context, page);
    }

    @Override
    public List<Movie> loadInBackground() {
        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getContext());
        try {
            return api.getTopRatedMovies(getPage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }
}
