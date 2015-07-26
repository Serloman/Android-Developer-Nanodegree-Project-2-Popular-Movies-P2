package com.serloman.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.serloman.themoviedb_api.models.ImageMovie;

/**
 * Created by Serloman on 22/07/2015.
 */
public class ParcelableImageMovie implements Parcelable, ImageMovie {


    private String mPathImage;
    private double mAspectRatio;
    private int mHeight;
    private double mVoteAverage;
    private int mVoteCount;
    private int mWidth;
    private String mIso_639_1;

    public ParcelableImageMovie(ImageMovie image){
        this.mPathImage = image.getRelativePath();
        this.mAspectRatio = image.getAspectRatio();
        this.mHeight = image.getHeight();
        this.mVoteAverage = image.getVoteAverage();
        this.mVoteCount = image.getVoteCount();
        this.mWidth = image.getWidth();
        this.mIso_639_1 = image.getIso_639_1();
    }

    public ParcelableImageMovie(Parcel in){
        this.readFromParcel(in);
    }

    @Override
    public String getRelativePath() {
        return mPathImage;
    }

    @Override
    public String getUrl(Sizes size) {
        return ImageMovie.API_ENDPOINT + "/" + size.toString() + "/" + mPathImage;
    }

    @Override
    public int getHeight() {
        return mHeight;
    }

    @Override
    public int getWidth() {
        return mWidth;
    }

    @Override
    public double getAspectRatio() {
        return mAspectRatio;
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
    public String getIso_639_1() {
        return mIso_639_1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPathImage);
        dest.writeDouble(mAspectRatio);
        dest.writeInt(mHeight);
        dest.writeDouble(mVoteAverage);
        dest.writeInt(mVoteCount);
        dest.writeInt(mWidth);
        dest.writeString(mIso_639_1);
    }

    private void readFromParcel(Parcel in){
        mPathImage = in.readString();
        mAspectRatio = in.readDouble();
        mHeight = in.readInt();
        mVoteAverage = in.readDouble();
        mVoteCount = in.readInt();
        mWidth = in.readInt();
        mIso_639_1 = in.readString();
    }

    public static Parcelable.Creator<ParcelableImageMovie> CREATOR = new Parcelable.Creator<ParcelableImageMovie>(){

        @Override
        public ParcelableImageMovie createFromParcel(Parcel source) {
            return new ParcelableImageMovie(source);
        }

        @Override
        public ParcelableImageMovie[] newArray(int size) {
            return new ParcelableImageMovie[size];
        }
    };
}
