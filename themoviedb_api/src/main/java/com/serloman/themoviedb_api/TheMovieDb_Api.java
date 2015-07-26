package com.serloman.themoviedb_api;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.serloman.themoviedb_api.calls.ConnectionCallback;
import com.serloman.themoviedb_api.calls.DiscoverMoviesAsyncTask;
import com.serloman.themoviedb_api.calls.GetMovieAsyncTask;
import com.serloman.themoviedb_api.calls.MovieCallback;
import com.serloman.themoviedb_api.calls.MovieCreditsAsyncTask;
import com.serloman.themoviedb_api.calls.MovieCreditsCallback;
import com.serloman.themoviedb_api.calls.MovieListCallback;
import com.serloman.themoviedb_api.calls.MovieImagesAsyncTask;
import com.serloman.themoviedb_api.calls.MovieImagesCallback;
import com.serloman.themoviedb_api.calls.MovieVideosAsyncTask;
import com.serloman.themoviedb_api.calls.MovieVideosCallback;
import com.serloman.themoviedb_api.calls.PopularMoviesAsyncTask;
import com.serloman.themoviedb_api.calls.TopRatedMoviesAsyncTask;
import com.serloman.themoviedb_api.models.CreditsMovieApi;
import com.serloman.themoviedb_api.models.FullMovie;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.MovieListApi;
import com.serloman.themoviedb_api.models.MovieImages;
import com.serloman.themoviedb_api.models.MovieVideos;
import com.serloman.themoviedb_api.models.VideoMovie;

import java.io.IOException;
import java.util.List;

import retrofit.RestAdapter;

/**
 * Created by Serloman on 19/07/2015.
 */
public class TheMovieDb_Api {

    public enum Short_By {
        POPULARITY_ASC("popularity.asc"),
        POPULARITY_DESC("popularity.desc"),
        VOTE_AVERAGE_ASC("vote_average.asc"),
        VOTE_AVERAGE_DESC("vote_average.desc");

        private final String mName;

        Short_By(String name) {
            this.mName = name;
        }


        @Override
        public String toString() {
            return mName;
        }
    }

    private static final String API_ENDPOINT = "http://api.themoviedb.org/3";

    private String mApiKey;
    private RestAdapter mRestAdapter;
    private TheMovieDb_Service mService;
    private Context mContext;

    public TheMovieDb_Api(Context context, String apiKey){
        mRestAdapter = new RestAdapter.Builder().setEndpoint(API_ENDPOINT).build();
        mService = mRestAdapter.create(TheMovieDb_Service.class);
        mApiKey = apiKey;
        this.mContext = context;
    }

    public FullMovie getMovieData(int idMovie) throws IOException {
        return getMovieData(String.valueOf(idMovie));
    }

    public FullMovie getMovieData(String idMovie) throws IOException {
        checkNetwork();

        return mService.movieData(idMovie, mApiKey);
    }

    public void getMovieDataAsync(int idMovie, MovieCallback movieCallback){
        getMovieDataAsync(String.valueOf(idMovie), movieCallback);
    }

    public void getMovieDataAsync(String idMovie, MovieCallback movieCallback){
        if(!isNetworkAvailable(movieCallback))
            return;

        GetMovieAsyncTask task = new GetMovieAsyncTask(mService, mApiKey, movieCallback);
        task.execute(idMovie);
    }

    public List<Movie> discoverMovies(Short_By short_by) throws IOException {
        return discoverMovies(1, short_by);
    }

    public List<Movie> discoverMovies(int page, Short_By short_by) throws IOException {
        checkNetwork();

        MovieListApi moviesApi = mService.discoverMovies(String.valueOf(page), short_by.toString(), mApiKey);
        return moviesApi.getMovies();
    }

    public void discoverMoviesAsync(Short_By short_by, MovieListCallback moviesCallback){
        discoverMoviesAsync(1, short_by, moviesCallback);
    }

    public void discoverMoviesAsync(int page, Short_By short_by, MovieListCallback moviesCallback){
        if(!isNetworkAvailable(moviesCallback))
            return;

        DiscoverMoviesAsyncTask task = new DiscoverMoviesAsyncTask(mService, mApiKey, moviesCallback);
        task.execute(String.valueOf(page), short_by.toString());
    }

    public MovieImages getMovieImages(int idMovie) throws IOException {
        return getMovieImages(String.valueOf(idMovie));
    }

    public MovieImages getMovieImages(String idMovie) throws IOException {
        checkNetwork();

        return mService.mediaMovie(idMovie, mApiKey);
    }

    public void getMovieImagesAsync(int idMovie, MovieImagesCallback movieMediaCallback){
        getMovieImagesAsync(String.valueOf(idMovie), movieMediaCallback);
    }

    public void getMovieImagesAsync(String idMovie, MovieImagesCallback movieMediaCallback){
        if(!isNetworkAvailable(movieMediaCallback))
            return;

        MovieImagesAsyncTask task = new MovieImagesAsyncTask(mService, mApiKey, movieMediaCallback);
        task.execute(idMovie);
    }

    public List<VideoMovie> getMovieVideos(int idMovie) throws IOException {
        return getMovieVideos(String.valueOf(idMovie));
    }

    public List<VideoMovie> getMovieVideos(String idMovie) throws IOException {
        checkNetwork();

        MovieVideos movieVideos = mService.videosMovie(idMovie, mApiKey);
        return movieVideos.getVideos();
    }

    public void getMovieVideosAsync(int idMovie, MovieVideosCallback movieMediaCallback){
        getMovieVideosAsync(String.valueOf(idMovie), movieMediaCallback);
    }

    public void getMovieVideosAsync(String idMovie, MovieVideosCallback movieVideosCallback){
        if(!isNetworkAvailable(movieVideosCallback))
            return;

        MovieVideosAsyncTask task = new MovieVideosAsyncTask(mService, mApiKey, movieVideosCallback);
        task.execute(idMovie);
    }

    public List<Movie> getPopularMovies() throws IOException {
        return getPopularMovies(1);
    }

    public List<Movie> getPopularMovies(int page) throws IOException {
        checkNetwork();

        MovieListApi moviesApi = mService.popularMovies(String.valueOf(page), mApiKey);
        return moviesApi.getMovies();
    }

    public void getPopularMoviesAsync(MovieListCallback moviesCallback){
        getPopularMoviesAsync(1, moviesCallback);
    }

    public void getPopularMoviesAsync(int page, MovieListCallback moviesCallback){
        if(!isNetworkAvailable(moviesCallback))
            return;

        PopularMoviesAsyncTask task = new PopularMoviesAsyncTask(mService, mApiKey, moviesCallback);
        task.execute(String.valueOf(page));
    }

    public List<Movie> getTopRatedMovies() throws IOException {
        return getTopRatedMovies(1);
    }

    public List<Movie> getTopRatedMovies(int page) throws IOException {
        checkNetwork();

        MovieListApi moviesApi = mService.topRatedMovies(String.valueOf(page), mApiKey);
        return moviesApi.getMovies();
    }

    public void getTopRatedMoviesAsync(MovieListCallback moviesCallback){
        getTopRatedMoviesAsync(1, moviesCallback);
    }

    public void getTopRatedMoviesAsync(int page, MovieListCallback moviesCallback){
        if(!isNetworkAvailable(moviesCallback))
            return;

        TopRatedMoviesAsyncTask task = new TopRatedMoviesAsyncTask(mService, mApiKey, moviesCallback);
        task.execute(String.valueOf(page));
    }

    public CreditsMovieApi getMovieCredits(int idMovie) throws IOException {
        return getMovieCredits(String.valueOf(idMovie));
    }

    public CreditsMovieApi getMovieCredits(String idMovie) throws IOException {
        checkNetwork();

        return mService.creditsMovie(idMovie, mApiKey);
    }

    public void getMovieCreditsAsync(int idMovie, MovieCreditsCallback creditsCallback){
        getMovieCreditsAsync(String.valueOf(idMovie), creditsCallback);
    }

    public void getMovieCreditsAsync(String idMovie, MovieCreditsCallback creditsCallback){
        if(!isNetworkAvailable(creditsCallback))
            return;

        MovieCreditsAsyncTask task = new MovieCreditsAsyncTask(mService, mApiKey, creditsCallback);
        task.execute(idMovie);
    }

    /**
     * Based on solutions provided in:
     * http://stackoverflow.com/questions/4086159/checking-internet-connection-on-android
     */
    private void checkNetwork() throws IOException {
        ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);;
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(!(networkInfo!=null && networkInfo.isConnected() && networkInfo.isAvailable()))
            throw new IOException("Network not available");
    }

    private boolean isNetworkAvailable(ConnectionCallback callback){
        try{
            checkNetwork();
        }catch (IOException ex){
            callback.onError(ex);
            return false;
        }

        return true;
    }


}
