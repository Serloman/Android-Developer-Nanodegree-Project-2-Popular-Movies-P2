package com.serloman.themoviedb_api.calls;

import android.os.AsyncTask;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.CreditsMovie;
import com.serloman.themoviedb_api.models.CreditsMovieApi;
import com.serloman.themoviedb_api.models.MovieImages;

/**
 * Created by Serloman on 22/07/2015.
 */
public class MovieCreditsAsyncTask extends AsyncTask<String, Integer, CreditsMovie> {

    private TheMovieDb_Service mService;
    private String mApiKey;
    private MovieCreditsCallback mMovieCallback;

    public MovieCreditsAsyncTask(TheMovieDb_Service service, String apiKey, MovieCreditsCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected CreditsMovie doInBackground(String... args) {
        String idMovie = args[0];

        return mService.creditsMovie(idMovie, mApiKey);
    }

    @Override
    protected void onPostExecute(CreditsMovie creditsMovie) {
        try{
            if(creditsMovie!=null)
                mMovieCallback.onMovieCreditsDataReceived(creditsMovie);
            else
                mMovieCallback.onError(new Exception("Error getting credits"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}