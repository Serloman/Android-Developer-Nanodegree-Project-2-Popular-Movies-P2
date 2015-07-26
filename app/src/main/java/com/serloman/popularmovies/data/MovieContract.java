package com.serloman.popularmovies.data;

import android.provider.BaseColumns;

/**
 * Created by Serloman on 26/07/2015.
 */
public final class MovieContract {
    public MovieContract() {}

    public static abstract class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_NAME_MOVIE_ID = "movie_id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_OVERVIEW = "overview";
        public static final String COLUMN_NAME_IS_ADULT = "is_adult";
        public static final String COLUMN_NAME_VOTE_AVERAGE = "vote_Average";
        public static final String COLUMN_NAME_VOTE_COUNT = "vote_count";
        public static final String COLUMN_NAME_BACKDROP = "backdrop";
        public static final String COLUMN_NAME_POSTER = "poster";
        public static final String COLUMN_NAME_DATE = "date";
    }
}
