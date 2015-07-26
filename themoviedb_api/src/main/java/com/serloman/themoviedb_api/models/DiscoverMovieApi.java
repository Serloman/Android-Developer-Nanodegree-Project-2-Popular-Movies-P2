package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public class DiscoverMovieApi implements Movie{
    private String title;
    private boolean adult;
    private String backdrop_path;
    private int id;
    private String poster_path;
    private String overview;
    private double vote_average;
    private int vote_count;

    @Override
    public String toString() {
        return getTitle();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean isAdult() {
        return adult;
    }

    @Override
    public String getOverview() {
        return overview;
    }

    @Override
    public double getVoteAverage() {
        return vote_average;
    }

    @Override
    public int getVoteCount() {
        return vote_count;
    }

    @Override
    public String getBackdropUrl(ImageMovie.Sizes size) {
        return getImageUrl(size, backdrop_path);
    }


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getBackdropRelativePath() {
        return backdrop_path;
    }

    @Override
    public String getPosterRelativePath() {
        return poster_path;
    }

    @Override
    public String getPosterUrl(ImageMovie.Sizes size) {
        return getImageUrl(size, poster_path);
    }

    private String getImageUrl(ImageMovie.Sizes size, String pathImage){
        return API_IMAGE_ENDPOINT + "/" + size.toString() + "/" + pathImage;
    }

}
