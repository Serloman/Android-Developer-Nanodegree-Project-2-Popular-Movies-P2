package com.serloman.themoviedb_api.models;

import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public interface CreditsMovie {
    List<CastMovie> getCast();
    List<CrewMovie> getCrew();
}
