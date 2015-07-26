package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public interface CastMovie {
    int getCastId();
    String getCharacterName();
    String getCreditId();
    int getId();
    String getName();
    int getOrder();
    String getRelativePath();
    String getUrlPhoto(ImageMovie.Sizes size);

}
