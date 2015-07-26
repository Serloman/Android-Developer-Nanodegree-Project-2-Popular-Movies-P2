package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class FullMovieApi extends DiscoverMovieApi implements FullMovie{

    private long budget;
    private String homepage;
    private String imdb_id;
    private double popularity;
    private String release_date;
    private long revenue;
    private String tagline;
    private List<GenreApi> genres;
    private List<ProductionCompany> productionCompanies;

    @Override
    public long getBudget() {
        return budget;
    }

    @Override
    public String getHomePage() {
        return homepage;
    }

    @Override
    public String getImdbId() {
        return imdb_id;
    }

    @Override
    public double getPopularity() {
        return popularity;
    }

    @Override
    public String getReleaseDate() {
        return release_date;
    }

    @Override
    public long getRevenue() {
        return revenue;
    }

    @Override
    public String getTagline() {
        return tagline;
    }

    @Override
    public List<Genre> getListGenres() {
        List<Genre> movieGenres = new ArrayList<>();

        if(genres!=null)
            for(GenreApi genre : genres)
                movieGenres.add(genre);

        return movieGenres;
    }

    @Override
    public List<ProductionCompany> getListProductionCompanies() {
        List<ProductionCompany> productionCompanies = new ArrayList<>();

        if(this.productionCompanies!=null)
            for(ProductionCompany company : this.productionCompanies)
                productionCompanies.add(company);

        return productionCompanies;
    }
}
