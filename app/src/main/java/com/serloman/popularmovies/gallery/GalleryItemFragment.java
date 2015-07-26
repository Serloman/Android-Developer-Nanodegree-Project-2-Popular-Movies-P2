package com.serloman.popularmovies.gallery;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.serloman.popularmovies.R;
import com.serloman.popularmovies.models.ParcelableImageMovie;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Serloman on 22/07/2015.
 */
public class GalleryItemFragment extends Fragment implements Callback{
    public static final String ARG_IMAGE_MOVIE = "ARG_IMAGE_MOVIE";

    public static GalleryItemFragment newInstance(ImageMovie image){
        GalleryItemFragment fragment = new GalleryItemFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_IMAGE_MOVIE, new ParcelableImageMovie(image));
        fragment.setArguments(args);

        return fragment;
    }

    private boolean imageLoaded;

    public GalleryItemFragment() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        imageLoaded = false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_full_item_view, container, false);

        initImage(rootView);

        if(imageLoaded)
            rootView.findViewById(R.id.galleryFullItemProgressBar).setVisibility(View.GONE);

        return rootView;
    }

    private void initImage(View rootView){
        ImageView imageView = (ImageView) rootView.findViewById(R.id.galleryFullItemImageView);
        Picasso.with(getActivity()).load(getImageData().getUrl(ImageMovie.Sizes.original)).into(imageView, this);
    }

    private ImageMovie getImageData(){
        return this.getArguments().getParcelable(ARG_IMAGE_MOVIE);
    }

    @Override
    public void onSuccess() {
        loadFinished();
    }

    @Override
    public void onError() {
        loadFinished();
        Toast.makeText(getActivity(), getResources().getString(R.string.error_image_movie), Toast.LENGTH_SHORT).show();
    }

    private void loadFinished(){
        View rootView = getView();

        if(rootView!=null)
            rootView.findViewById(R.id.galleryFullItemProgressBar).setVisibility(View.GONE);

        imageLoaded = true;
    }
}
