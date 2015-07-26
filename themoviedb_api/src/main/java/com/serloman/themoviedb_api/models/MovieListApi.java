package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public class MovieListApi {
    private List<DiscoverMovieApi> results;

    public List<DiscoverMovieApi> getDiscoverMovies(){
        return results;
    }

    public List<Movie> getMovies(){
        List<DiscoverMovieApi> movies = getDiscoverMovies();

        List<Movie> defaultMovies = new ArrayList<>();
        for(DiscoverMovieApi movie : movies)
            defaultMovies.add(movie);

        return defaultMovies;
    }
}
