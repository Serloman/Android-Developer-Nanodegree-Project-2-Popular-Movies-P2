package com.serloman.themoviedb_api.models;

import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public interface FullMovie extends Movie {
    List<Genre> getListGenres();
    long getBudget();
    String getHomePage();
    String getImdbId();
    double getPopularity();
    String getReleaseDate();
    long getRevenue();
    String getTagline();
    List<ProductionCompany> getListProductionCompanies();
}
