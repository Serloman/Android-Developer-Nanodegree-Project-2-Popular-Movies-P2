package com.serloman.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.serloman.themoviedb_api.models.VideoMovie;

/**
 * Created by Serloman on 30/07/2015.
 */
public class ParcelableVideoMovie implements Parcelable, VideoMovie {

    private String mId;
    private String mIso_639_1;
    private String mKey;
    private String mName;
    private String mSite;
    private int mSize;
    private String mType;

    public ParcelableVideoMovie(VideoMovie videoMovie){
        this.mId = videoMovie.getId();
        this.mIso_639_1 = videoMovie.getIso_639_1();
        this.mKey = videoMovie.getKey();
        this.mName = videoMovie.getName();
        this.mSite = videoMovie.getSite();
        this.mSize = videoMovie.getSize();
        this.mType = videoMovie.getType();
    }

    public ParcelableVideoMovie(Parcel in){
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mIso_639_1);
        dest.writeString(mKey);
        dest.writeString(mName);
        dest.writeString(mSite);
        dest.writeInt(mSize);
        dest.writeString(mType);
    }

    private void readFromParcel(Parcel in){
        mId = in.readString();
        mIso_639_1 = in.readString();
        mKey = in.readString();
        mName = in.readString();
        mSite = in.readString();
        mSize = in.readInt();
        mType = in.readString();
    }

    @Override
    public String getId() {
        return mId;
    }

    @Override
    public String getIso_639_1() {
        return mIso_639_1;
    }

    @Override
    public String getKey() {
        return mKey;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getSite() {
        return mSite;
    }

    @Override
    public int getSize() {
        return mSize;
    }

    @Override
    public String getType() {
        return mType;
    }

    public static final Parcelable.Creator<ParcelableVideoMovie> CREATOR = new Parcelable.Creator<ParcelableVideoMovie>(){

        @Override
        public ParcelableVideoMovie createFromParcel(Parcel source) {
            return new ParcelableVideoMovie(source);
        }

        @Override
        public ParcelableVideoMovie[] newArray(int size) {
            return new ParcelableVideoMovie[size];
        }
    };
}
