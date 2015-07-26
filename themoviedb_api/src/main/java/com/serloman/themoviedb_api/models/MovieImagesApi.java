package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 19/07/2015.
 */
public class MovieImagesApi implements MovieImages {
    int id;
    private List<ImageMovieApi> backdrops;
    private List<ImageMovieApi> posters;

    @Override
    public List<ImageMovie> getBackdrops(){
        List<ImageMovie> backdrops = new ArrayList<>();

        for(ImageMovieApi image : this.backdrops)
            backdrops.add(image);

        return backdrops;
    }

    @Override
    public List<ImageMovie> getPosters(){
        List<ImageMovie> posters = new ArrayList<>();

        for(ImageMovie poster : this.posters)
            posters.add(poster);

        return posters;
    }
}
