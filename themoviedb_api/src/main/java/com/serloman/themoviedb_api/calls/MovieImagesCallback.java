package com.serloman.themoviedb_api.calls;

import com.serloman.themoviedb_api.models.MovieImages;

/**
 * Created by Serloman on 22/07/2015.
 */
public interface MovieImagesCallback extends ConnectionCallback {
    void onMovieMediaDataReceived(MovieImages movieMedia);
}
