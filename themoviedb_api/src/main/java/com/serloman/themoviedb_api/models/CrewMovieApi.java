package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public class CrewMovieApi implements CrewMovie {

    String credit_id;
    String department;
    int id;
    String job;
    String name;
    String profile_path;

    @Override
    public String getCreditId() {
        return credit_id;
    }

    @Override
    public String getDepartment() {
        return department;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getJob() {
        return job;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getRelativePath() {
        return profile_path;
    }

    @Override
    public String getUrlPhoto(ImageMovie.Sizes size) {
        return ImageMovie.API_ENDPOINT + "/" + size.toString() + "/" + profile_path;
    }
}
