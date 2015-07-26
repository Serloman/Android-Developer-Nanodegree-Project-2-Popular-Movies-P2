package com.serloman.themoviedb_api.calls;

import com.serloman.themoviedb_api.models.ReviewMovie;

import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public interface MovieReviewsCallback extends ConnectionCallback {
    void onMovieReviewsDataReceived(List<ReviewMovie> reviews);
}
