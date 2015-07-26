package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 19/07/2015.
 */
public class GenreApi implements Genre{
    private int id;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getId() {
        return id;
    }
}
