package com.serloman.themoviedb_api.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public class MovieVideosApi implements MovieVideos {

    List<VideoMovieApi> results;

    @Override
    public List<VideoMovie> getVideos() {
        List <VideoMovie> videos = new ArrayList<>();

        for(VideoMovieApi video : results)
            videos.add(video);

        return videos;
    }
}
