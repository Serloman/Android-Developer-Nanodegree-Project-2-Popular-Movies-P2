package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public class VideoMovieApi implements VideoMovie{

    private String id;
    private String iso_639_1;
    private String key;
    private String name;
    private String site;
    private int size;
    private String type;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIso_639_1() {
        return iso_639_1;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSite() {
        return site;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public String getType() {
        return type;
    }
}
