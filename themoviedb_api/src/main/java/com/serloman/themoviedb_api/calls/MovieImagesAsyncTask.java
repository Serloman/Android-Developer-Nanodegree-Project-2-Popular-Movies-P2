package com.serloman.themoviedb_api.calls;

import android.os.AsyncTask;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.MovieImages;

/**
 * Created by Serloman on 22/07/2015.
 */
public class MovieImagesAsyncTask extends AsyncTask<String, Integer, MovieImages> {

    private TheMovieDb_Service mService;
    private String mApiKey;
    private MovieImagesCallback mMovieCallback;

    public MovieImagesAsyncTask(TheMovieDb_Service service, String apiKey, MovieImagesCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected MovieImages doInBackground(String... args) {
        String idMovie = args[0];

        return mService.mediaMovie(idMovie, mApiKey);
    }

    @Override
    protected void onPostExecute(MovieImages movieMedia) {
        try {
            if (movieMedia != null)
                mMovieCallback.onMovieMediaDataReceived(movieMedia);
            else
                mMovieCallback.onError(new Exception("Error getting images"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}