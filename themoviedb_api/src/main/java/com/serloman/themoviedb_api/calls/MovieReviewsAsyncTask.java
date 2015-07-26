package com.serloman.themoviedb_api.calls;

import android.os.AsyncTask;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.MovieVideos;
import com.serloman.themoviedb_api.models.ReviewMovie;
import com.serloman.themoviedb_api.models.ReviewMovieList;
import com.serloman.themoviedb_api.models.VideoMovie;

import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public class MovieReviewsAsyncTask extends AsyncTask<String, Integer, List<ReviewMovie>> {

    private TheMovieDb_Service mService;
    private String mApiKey;
    private MovieReviewsCallback mMovieCallback;

    public MovieReviewsAsyncTask(TheMovieDb_Service service, String apiKey, MovieReviewsCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected List<ReviewMovie> doInBackground(String... args) {
        String idMovie = args[0];

        ReviewMovieList movieVideos = mService.reviewsMovie(idMovie, mApiKey);

        return movieVideos.getReviews();
    }

    @Override
    protected void onPostExecute(List<ReviewMovie> reviews) {
        try {
            if (reviews != null)
                mMovieCallback.onMovieReviewsDataReceived(reviews);
            else
                mMovieCallback.onError(new Exception("Error getting reviews"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}