package com.serloman.themoviedb_api.models;


import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public interface Movie {
    String API_IMAGE_ENDPOINT = ImageMovie.API_ENDPOINT;

    int getId();
    String getTitle();
    boolean isAdult();
    String getOverview();
    double getVoteAverage();
    int getVoteCount();

    String getBackdropRelativePath();
    String getPosterRelativePath();
    String getBackdropUrl(ImageMovie.Sizes size);
    String getPosterUrl(ImageMovie.Sizes size);

}
