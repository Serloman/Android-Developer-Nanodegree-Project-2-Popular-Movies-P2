package com.serloman.themoviedb_api;

import com.serloman.themoviedb_api.models.CreditsMovieApi;
import com.serloman.themoviedb_api.models.FullMovieApi;
import com.serloman.themoviedb_api.models.MovieImagesApi;
import com.serloman.themoviedb_api.models.MovieListApi;
import com.serloman.themoviedb_api.models.MovieVideosApi;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Serloman on 19/07/2015.
 */
public interface TheMovieDb_Service {
    @GET("/movie/{movie}")
    FullMovieApi movieData(@Path("movie") String movie, @Query("api_key") String api_key);

    @GET("/discover/movie")
    MovieListApi discoverMovies(@Query("page") String page, @Query("sort_by") String sort_by, @Query("api_key") String api_key);

    @GET("/movie/{movie}/images")
    MovieImagesApi mediaMovie(@Path("movie") String movie, @Query("api_key") String api_key);

    @GET("/movie/{movie}/videos")
    MovieVideosApi videosMovie(@Path("movie") String movie, @Query("api_key") String api_key);

    @GET("/movie/popular")
    MovieListApi popularMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("/movie/top_rated")
    MovieListApi topRatedMovies(@Query("page") String page, @Query("api_key") String api_key);

    @GET("/movie/{movie}/credits")
    CreditsMovieApi creditsMovie(@Path("movie") String movie, @Query("api_key") String api_key);
}
