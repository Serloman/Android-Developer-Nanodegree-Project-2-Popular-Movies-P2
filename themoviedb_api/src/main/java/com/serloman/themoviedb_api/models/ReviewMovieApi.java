package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 26/07/2015.
 */
public class ReviewMovieApi implements ReviewMovie {

    private String id;
    private String author;
    private String content;
    private String url;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAuthorName() {
        return author;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public String getUrl() {
        return url;
    }
}
