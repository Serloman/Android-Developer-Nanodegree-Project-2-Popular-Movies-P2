package com.serloman.popularmovies.gallery;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;

import com.serloman.popularmovies.R;
import com.serloman.popularmovies.models.ParcelableImageMovie;
import com.serloman.themoviedb_api.models.ImageMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 22/07/2015.
 */
public class GalleryActivity extends AppCompatActivity implements OnPageChangeListener{

    public final static String ARG_POSITION = "ARG_POSITION";
    public final static String ARG_IMAGES_MOVIE = "ARG_IMAGES_MOVIE";

    private int mPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.gallery_activity);

        mPosition = getFirstPosition();
        if(savedInstanceState!=null)
            mPosition = savedInstanceState.getInt(ARG_POSITION, getFirstPosition());

        initGallery();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(ARG_POSITION, mPosition);
    }

    private void initGallery(){
        GalleryFragment gallery = GalleryFragment.newInstance(mPosition, getImages());

        this.getSupportFragmentManager().beginTransaction().replace(R.id.container, gallery).commit();
    }

    private List<ParcelableImageMovie> getImages(){
        return this.getIntent().getParcelableArrayListExtra(ARG_IMAGES_MOVIE);
    }

    private int getFirstPosition(){
        return this.getIntent().getExtras().getInt(ARG_POSITION, 0);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
