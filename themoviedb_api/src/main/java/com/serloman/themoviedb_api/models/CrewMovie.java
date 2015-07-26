package com.serloman.themoviedb_api.models;

/**
 * Created by Serloman on 22/07/2015.
 */
public interface CrewMovie {
    String getCreditId();
    String getDepartment();
    int getId();
    String getJob();
    String getName();
    String getRelativePath();
    String getUrlPhoto(ImageMovie.Sizes size);
}
