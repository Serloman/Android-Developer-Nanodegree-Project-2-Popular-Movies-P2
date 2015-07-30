package com.serloman.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.Date;

/**
 * Created by Serloman on 30/07/2015.
 * Using as example: http://www.vogella.com/tutorials/AndroidSQLite/article.html
 */
public class MovieProvider extends ContentProvider {

    public final static String AUTHORITY = "com.serloman.movies.provider";
    public final static String MOVIE_PATH = "movie";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + MOVIE_PATH);

    private static final int MOVIES = 10;
    private static final int MOVIE = 20;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static{
        sUriMatcher.addURI(AUTHORITY, MOVIE_PATH, MOVIES);
        sUriMatcher.addURI(AUTHORITY,MOVIE_PATH + "/#", MOVIE);
    }

    public static Uri getMovieUri(int idMovie){
        return Uri.parse("content://" + AUTHORITY + "/" + MOVIE_PATH + "/" + idMovie);
    }

    private MovieDbHelper mMovieDbHelper;

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());

        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        switch (sUriMatcher.match(uri)){
            case MOVIES:
                queryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
                break;
            case MOVIE:
                queryBuilder.setTables(MovieContract.MovieEntry.TABLE_NAME);
                queryBuilder.appendWhere(MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        SQLiteDatabase db = mMovieDbHelper.getReadableDatabase();

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }



    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case MOVIES:
                id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(MOVIE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);
        SQLiteDatabase db = mMovieDbHelper.getWritableDatabase();
        int rowsDeleted = 0;

        switch (uriType) {
            case MOVIES:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case MOVIE:
                String id = uri.getLastPathSegment();
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry.COLUMN_NAME_MOVIE_ID + "=" + id, null);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
