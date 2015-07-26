package com.serloman.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serloman.popularmovies.data.MovieContract.MovieEntry;


/**
 * Created by Serloman on 26/07/2015.
 */
public class MovieDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "themoviedb.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " (" +
                    MovieEntry._ID + " INTEGER PRIMARY KEY," +
                    MovieEntry.COLUMN_NAME_MOVIE_ID + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_TITLE + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_OVERVIEW + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_IS_ADULT + INTEGER_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_VOTE_AVERAGE + REAL_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_VOTE_COUNT + INTEGER_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_BACKDROP + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_POSTER + TEXT_TYPE + COMMA_SEP +
                    MovieEntry.COLUMN_NAME_DATE + INTEGER_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;


    public MovieDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
