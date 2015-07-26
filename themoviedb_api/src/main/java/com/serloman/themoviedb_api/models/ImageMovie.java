package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 19/07/2015.
 */
public interface ImageMovie {
    String API_ENDPOINT = "http://image.tmdb.org/t/p";
    enum Sizes{
        w92,
        w154,
        w185,
        w342,
        w500,
        w780,
        original
    }

    String getRelativePath();
    String getUrl(Sizes size);
    int getHeight();
    int getWidth();
    double getAspectRatio();
    double getVoteAverage();
    int getVoteCount();
    String getIso_639_1();
}
