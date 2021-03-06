package com.serloman.popularmovies.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.serloman.popularmovies.data.MovieContract.MovieEntry;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.serloman.themoviedb_api.models.Movie;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class FavouriteMovies {

    Context mContext;

    public FavouriteMovies(Context context){
        this.mContext = context;

    }

    public List<Movie> getFavouriteMovies(){
        Uri uri = MovieProvider.CONTENT_URI;
        String sortOrder = MovieEntry.COLUMN_NAME_DATE + " DESC";

        ContentResolver contentResolver = mContext.getContentResolver();
        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);
        cursor.moveToFirst();

        List<Movie> favourites = new ArrayList<>();

        if(cursor.getCount()>0){
            do{
                favourites.add(new DatabaseMovie(cursor));
            }while (cursor.moveToNext());
        }

        return favourites;
    }

    public Movie getMovie(int idMovie){
        ContentResolver contentResolver = mContext.getContentResolver();

        String sortOrder = MovieEntry.COLUMN_NAME_DATE + " DESC";
        Uri uri = MovieProvider.getMovieUri(idMovie);

        Cursor cursor = contentResolver.query(uri, null, null, null, sortOrder);
        cursor.moveToFirst();

        if(cursor.getCount()<1)
            return null;

        return new DatabaseMovie(cursor);
    }

    public boolean isFavourited(int idMovie){
        return getMovie(idMovie)!=null;
    }

    public Uri saveFavourite(Movie movie){
        ContentValues values = new ContentValues();
        values.put(MovieEntry.COLUMN_NAME_MOVIE_ID, String.valueOf(movie.getId()));
        values.put(MovieEntry.COLUMN_NAME_TITLE, movie.getTitle());
        values.put(MovieEntry.COLUMN_NAME_OVERVIEW, movie.getOverview());
        values.put(MovieEntry.COLUMN_NAME_VOTE_AVERAGE, movie.getVoteAverage());
        values.put(MovieEntry.COLUMN_NAME_VOTE_COUNT, movie.getVoteCount());
        values.put(MovieEntry.COLUMN_NAME_BACKDROP, movie.getBackdropRelativePath());
        values.put(MovieEntry.COLUMN_NAME_POSTER, movie.getPosterRelativePath());
        values.put(MovieEntry.COLUMN_NAME_DATE, new Date().getTime());

        if(movie.isAdult())
            values.put(MovieEntry.COLUMN_NAME_IS_ADULT, 1);
        else
            values.put(MovieEntry.COLUMN_NAME_IS_ADULT, 0);

        Uri uri = MovieProvider.CONTENT_URI;

        ContentResolver contentResolver = mContext.getContentResolver();

        return contentResolver.insert(uri, values);
    }

    public void deleteFavourite(Movie movie){
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = MovieProvider.getMovieUri(movie.getId());
        contentResolver.delete(uri, null, null);
    }

    private static class DatabaseMovie implements Movie{

        int mId;
        String mTitle;
        String mOverview;
        double mVoteAverage;
        int mVoteCount;
        String mBackdrop;
        String mPoster;
        boolean mAdult;

        public DatabaseMovie(Cursor cursor){
            mId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_MOVIE_ID)));
            mTitle = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_TITLE));
            mOverview = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_OVERVIEW));
            mVoteAverage = cursor.getDouble(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_AVERAGE));
            mVoteCount = cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_VOTE_COUNT));
            mBackdrop = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_BACKDROP));
            mPoster = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_POSTER));
            mAdult = false;
            if(cursor.getInt(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_NAME_IS_ADULT))==1)
                mAdult = true;
        }

        @Override
        public int getId() {
            return mId;
        }

        @Override
        public String getTitle() {
            return mTitle;
        }

        @Override
        public boolean isAdult() {
            return mAdult;
        }

        @Override
        public String getOverview() {
            return mOverview;
        }

        @Override
        public double getVoteAverage() {
            return mVoteAverage;
        }

        @Override
        public int getVoteCount() {
            return mVoteCount;
        }

        @Override
        public String getBackdropRelativePath() {
            return mBackdrop;
        }

        @Override
        public String getPosterRelativePath() {
            return mPoster;
        }

        @Override
        public String getBackdropUrl(ImageMovie.Sizes size) {
            return getImageUrl(size, mBackdrop);
        }

        @Override
        public String getPosterUrl(ImageMovie.Sizes size) {
            return getImageUrl(size, mPoster);
        }

        private String getImageUrl(ImageMovie.Sizes size, String pathImage){
            return API_IMAGE_ENDPOINT + "/" + size.toString() + "/" + pathImage;
        }
    }
}
