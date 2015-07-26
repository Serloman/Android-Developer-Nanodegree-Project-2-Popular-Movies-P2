package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public class ProductionCompanyApi implements ProductionCompany {

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
