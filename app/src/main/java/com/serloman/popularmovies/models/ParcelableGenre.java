package com.serloman.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.serloman.themoviedb_api.models.Genre;

/**
 * Created by Serloman on 20/07/2015.
 */
public class ParcelableGenre implements Parcelable, Genre {

    private int mId;
    private String mName;

    public ParcelableGenre(Genre genre){
        this(genre.getId(), genre.getName());
    }

    public ParcelableGenre(int id, String name){
        this.mId = id;
        this.mName = name;
    }

    public ParcelableGenre(Parcel in){
        readFromParcel(in);
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public int getId() {
        return mId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeString(mName);
    }

    private void readFromParcel(Parcel in){
        mId = in.readInt();
        mName = in.readString();
    }

    public static final Parcelable.Creator<ParcelableGenre> CREATOR = new Parcelable.Creator<ParcelableGenre>(){

        @Override
        public ParcelableGenre createFromParcel(Parcel source) {
            return new ParcelableGenre(source);
        }

        @Override
        public ParcelableGenre[] newArray(int size) {
            return new ParcelableGenre[size];
        }
    };
}
