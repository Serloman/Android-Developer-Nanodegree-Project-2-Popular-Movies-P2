package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class ReviewMovieListApi implements ReviewMovieList{

    private int id;
    private int page;
    private List<ReviewMovieApi> results;
    private int total_pages;
    private int total_results;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getPage() {
        return page;
    }

    @Override
    public List<ReviewMovie> getReviews() {
        List<ReviewMovie> reviews = new ArrayList<>();

        for(ReviewMovie review : this.results)
            reviews.add(review);

        return reviews;
    }

    @Override
    public int getTotalPages() {
        return total_pages;
    }

    @Override
    public int getTotalResults() {
        return total_results;
    }
}
