package com.serloman.themoviedb_api.calls;

import android.os.AsyncTask;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.MovieListApi;

import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public class DiscoverMoviesAsyncTask extends AsyncTask<String, Integer, List<Movie>> {

    public static final int POS_ARG_PAGE = 0;
    public static final int POS_ARG_SHORT_BY = 1;

    protected TheMovieDb_Service mService;
    protected String mApiKey;
    private MovieListCallback mMovieCallback;

    public DiscoverMoviesAsyncTask(TheMovieDb_Service service, String apiKey, MovieListCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected List<Movie> doInBackground(String... args) {
        String page = args[POS_ARG_PAGE];
        String shortBy = args[POS_ARG_SHORT_BY];

        MovieListApi moviesApi = mService.discoverMovies(page, shortBy, mApiKey);

        return moviesApi.getMovies();
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        try {
            if (movies != null)
                mMovieCallback.onMovieListDataReceived(movies);
            else
                mMovieCallback.onError(new Exception("Error getting movies"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}