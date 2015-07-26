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

    public final static int POS_ARG_ID_MOVIE = 0;
    public final static int POS_ARG_PAGE = 1;

    private TheMovieDb_Service mService;
    private String mApiKey;
    private MovieReviewsCallback mMovieCallback;

    public MovieReviewsAsyncTask(TheMovieDb_Service service, String apiKey, MovieReviewsCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected List<ReviewMovie> doInBackground(String... args) {
        String idMovie = args[POS_ARG_ID_MOVIE];
        String page = args[POS_ARG_PAGE];

        ReviewMovieList movieVideos = mService.reviewsMovie(idMovie, page, mApiKey);

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