package com.serloman.themoviedb_api.calls;

import android.os.AsyncTask;

import com.serloman.themoviedb_api.TheMovieDb_Service;
import com.serloman.themoviedb_api.models.MovieImages;
import com.serloman.themoviedb_api.models.MovieVideos;
import com.serloman.themoviedb_api.models.VideoMovie;

import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public class MovieVideosAsyncTask extends AsyncTask<String, Integer, List<VideoMovie>> {

    private TheMovieDb_Service mService;
    private String mApiKey;
    private MovieVideosCallback mMovieCallback;

    public MovieVideosAsyncTask(TheMovieDb_Service service, String apiKey, MovieVideosCallback movieCallback){
        this.mService = service;
        this.mApiKey = apiKey;
        this.mMovieCallback = movieCallback;
    }

    protected List<VideoMovie> doInBackground(String... args) {
        String idMovie = args[0];

        MovieVideos movieVideos = mService.videosMovie(idMovie, mApiKey);

        return movieVideos.getVideos();
    }

    @Override
    protected void onPostExecute(List<VideoMovie> videos) {
        try {
            if (videos != null)
                mMovieCallback.onMovieVideosDataReceived(videos);
            else
                mMovieCallback.onError(new Exception("Error getting videos"));
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}