package com.serloman.themoviedb_api.calls;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.MovieListApi;

import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public class TopRatedMoviesAsyncTask extends DiscoverMoviesAsyncTask {

    public TopRatedMoviesAsyncTask(TheMovieDb_Service service, String apiKey, MovieListCallback movieCallback){
        super(service, apiKey, movieCallback);
    }

    protected List<Movie> doInBackground(String... args) {
        String page = args[POS_ARG_PAGE];

        MovieListApi moviesApi = mService.topRatedMovies(page, mApiKey);

        return moviesApi.getMovies();
    }

}