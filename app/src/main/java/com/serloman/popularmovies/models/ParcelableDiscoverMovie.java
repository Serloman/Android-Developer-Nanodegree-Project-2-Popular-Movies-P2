package com.serloman.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.serloman.themoviedb_api.models.ImageMovie;
import com.serloman.themoviedb_api.models.Movie;

import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class ParcelableDiscoverMovie implements Parcelable, Movie {

    private int mId;
    private String mTitle;
    private int mIsAdult;
    private String mBackDropPath;
    private String mPosterPath;
    private String mOverview;
    private double mVoteAverage;
    private int mVoteCount;

    public ParcelableDiscoverMovie(Movie movie){
        this.mId = movie.getId();
        this.mTitle = movie.getTitle();
        this.mIsAdult = 0;
        if(movie.isAdult())
            this.mIsAdult = 1;
        this.mBackDropPath = movie.getBackdropRelativePath();
        this.mPosterPath = movie.getPosterRelativePath();
        this.mOverview = movie.getOverview();
        this.mVoteAverage = movie.getVoteAverage();
        this.mVoteCount = movie.getVoteCount();
    }

    public ParcelableDiscoverMovie(Parcel in){
        readFromParcel(in);
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    @Override
    public boolean isAdult() {
        return mIsAdult==1;
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
    public int getId() {
        return mId;
    }

    @Override
    public String getBackdropRelativePath() {
        return mBackDropPath;
    }

    @Override
    public String getPosterRelativePath() {
        return mPosterPath;
    }

    @Override
    public String getBackdropUrl(ImageMovie.Sizes size) {
        return getImageUrl(size, mBackDropPath);
    }

    @Override
    public String getPosterUrl(ImageMovie.Sizes size) {
        return getImageUrl(size, mPosterPath);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mTitle);
        dest.writeInt(mIsAdult);
        dest.writeString(mBackDropPath);
        dest.writeString(mPosterPath);
        dest.writeString(mOverview);
        dest.writeDouble(mVoteAverage);
        dest.writeInt(mVoteCount);
    }

    private void readFromParcel(Parcel in){
        mId = in.readInt();
        mTitle = in.readString();
        mIsAdult = in.readInt();
        mBackDropPath = in.readString();
        mPosterPath = in.readString();
        mOverview = in.readString();
        mVoteAverage = in.readDouble();
        mVoteCount = in.readInt();
    }

    private String getImageUrl(ImageMovie.Sizes size, String pathImage){
        return API_IMAGE_ENDPOINT + "/" + size.toString() + "/" + pathImage;
    }

    public static final Parcelable.Creator<ParcelableDiscoverMovie> CREATOR = new Parcelable.Creator<ParcelableDiscoverMovie>(){

        @Override
        public ParcelableDiscoverMovie createFromParcel(Parcel source) {
            return new ParcelableDiscoverMovie(source);
        }

        @Override
        public ParcelableDiscoverMovie[] newArray(int size) {
            return new ParcelableDiscoverMovie[size];
        }
    };
}
