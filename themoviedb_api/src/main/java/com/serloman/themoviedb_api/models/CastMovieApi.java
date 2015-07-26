package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public class CastMovieApi implements CastMovie{
    int cast_id;
    String character;
    String credit_id;
    int id;
    String name;
    int order;
    String profile_path;

    @Override
    public int getCastId() {
        return cast_id;
    }

    @Override
    public String getCharacterName() {
        return character;
    }

    @Override
    public String getCreditId() {
        return credit_id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getOrder() {
        return order;
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
