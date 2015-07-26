package com.serloman.themoviedb_api.models;

import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public interface ReviewMovieList {
    int getId();
    int getPage();
    List<ReviewMovie> getReviews();
    int getTotalPages();
    int getTotalResults();
}
