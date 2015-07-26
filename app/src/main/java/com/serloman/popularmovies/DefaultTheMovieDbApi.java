package com.serloman.popularmovies;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.serloman.themoviedb_api.TheMovieDb_Api;

/**
 * Created by Serloman on 20/07/2015.
 */
public class DefaultTheMovieDbApi extends TheMovieDb_Api{

    private final static String TAG = "TheMovieDbApiKey";

    public DefaultTheMovieDbApi(Context context) {
        super(context, getTheMovieDbApiKey(context));
    }

    private static String getTheMovieDbApiKey(Context context){
        try {
            return getApiKey(context);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }

        return null;
    }

    private static String getApiKey(Context context) throws PackageManager.NameNotFoundException {
        ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
        Bundle bundle = ai.metaData;
        return bundle.getString("the_movie_db_api_key");
    }
}
