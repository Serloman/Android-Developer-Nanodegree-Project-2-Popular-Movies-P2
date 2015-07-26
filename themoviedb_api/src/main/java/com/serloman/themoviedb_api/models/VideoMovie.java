package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public interface VideoMovie {
    String getId();
    String getIso_639_1();
    String getKey();
    String getName();
    String getSite();
    int getSize();
    String getType();
}
