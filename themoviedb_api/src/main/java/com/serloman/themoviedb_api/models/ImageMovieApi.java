package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 19/07/2015.
 */
public class ImageMovieApi implements ImageMovie {
    private double aspect_ratio;
    private String file_path;
    private int height;
    private double vote_average;
    private int vote_count;
    private int width;
    private String iso_639_1;

    @Override
    public String getRelativePath() {
        return file_path;
    }

    @Override
    public String getUrl(Sizes size) {
        return API_ENDPOINT + "/" + size.toString() + "/" + file_path;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public double getAspectRatio() {
        return aspect_ratio;
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
    public String getIso_639_1() {
        return iso_639_1;
    }
}
