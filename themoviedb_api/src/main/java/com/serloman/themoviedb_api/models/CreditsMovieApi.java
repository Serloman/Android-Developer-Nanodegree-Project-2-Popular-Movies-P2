package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public class CreditsMovieApi implements CreditsMovie{
    int id;
    List<CastMovieApi> cast;
    List<CrewMovieApi> crew;

    @Override
    public List<CastMovie> getCast(){
        List<CastMovie> cast = new ArrayList<>();

        for(CastMovieApi person : this.cast)
            cast.add(person);

        return cast;
    }

    @Override
    public List<CrewMovie> getCrew(){
        List<CrewMovie> crew = new ArrayList<>();

        for(CrewMovieApi person : this.crew)
            crew.add(person);

        return crew;
    }
}
