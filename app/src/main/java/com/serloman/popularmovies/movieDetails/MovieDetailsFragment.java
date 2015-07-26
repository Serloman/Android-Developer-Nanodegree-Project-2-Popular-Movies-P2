package com.serloman.popularmovies.movieDetails;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.serloman.popularmovies.DefaultTheMovieDbApi;
import com.serloman.popularmovies.R;
import com.serloman.popularmovies.gallery.GalleryActivity;
import com.serloman.popularmovies.models.ParcelableDiscoverMovie;
import com.serloman.popularmovies.models.ParcelableImageMovie;
import com.serloman.themoviedb_api.calls.MovieCallback;
import com.serloman.themoviedb_api.calls.MovieImagesCallback;
import com.serloman.themoviedb_api.models.FullMovie;
import com.serloman.themoviedb_api.models.Genre;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.MovieImages;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MovieDetailsFragment extends Fragment implements MovieCallback, LoaderManager.LoaderCallbacks<FullMovie>, MovieImagesCallback {

    public final static String ARG_MOVIE_DATA = "ARG_MOVIE_DATA";

    public static MovieDetailsFragment newInstance(Movie movie){
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_DATA, new ParcelableDiscoverMovie(movie));
        fragment.setArguments(args);

        return fragment;
    }

    private FullMovie mMovie;
    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    public MovieDetailsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().initLoader(0, null, this);

        initBasicData();
    }

    private void initBasicData(){
        Movie movie = getBasicMovieData();

        initToolbar(movie);
        initBackDrop(movie);
        initMovieData(movie);
        initGallery(movie);
    }

    private Movie getBasicMovieData(){
        return this.getArguments().getParcelable(ARG_MOVIE_DATA);
    }

    @Override
    public void onMovieDataReceived(FullMovie movie) {
        if(movie!=null)
            updateUI(movie);
        else
            notifyUser();
    }

    private void updateUI(FullMovie movie){
        ((TextView) getView().findViewById(R.id.movieDetailsTagline)).setText(movie.getTagline());
        ((TextView) getView().findViewById(R.id.movieDetailsReleaseDate)).setText(movie.getReleaseDate());
        TextView homepage = ((TextView) getView().findViewById(R.id.movieDetailsHomePage));
        homepage.setText(getHomepageLink(movie.getHomePage()));
        homepage.setMovementMethod(LinkMovementMethod.getInstance());

        ((TextView) getView().findViewById(R.id.scoreVoteAverageTextView)).setText(String.valueOf(movie.getVoteAverage()));
        ((TextView) getView().findViewById(R.id.scoreVoteCountTextView)).setText(String.valueOf(movie.getVoteCount()));

        ((TextView) getView().findViewById(R.id.movieDetailsGenres)).setText(getGenres(movie));
    }

    private void notifyUser(){
        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.notify_user_network_error), Toast.LENGTH_SHORT).show();
    }

    private Spanned getHomepageLink(String homepage){
        return Html.fromHtml("<a href='" + homepage + "'>" + homepage + "</a>");
    }

    private String getGenres(FullMovie movie){
        String genres = "";

        List<Genre> genresList = movie.getListGenres();

        if(genresList.size()>0){
            genres+=genresList.get(0).getName();

            for(int i=1;i<genresList.size();i++)
                genres+=", " + genresList.get(i).getName();
        }

        return genres;
    }

    private void initBackDrop(Movie movie){
        ImageView backDrop = (ImageView) getView().findViewById(R.id.movieDetailsBackdrop);
        Picasso.with(getActivity().getApplicationContext()).load(movie.getBackdropUrl(ImageMovie.Sizes.w500)).into(backDrop);
    }

    private void initToolbar(Movie movie){
        AppCompatActivity activity = (AppCompatActivity) this.getActivity();
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.movieDetailsToolbar);

        activity.setSupportActionBar(toolbar);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) getView().findViewById(R.id.movieDetailsCollapsingToolbar);
        collapsingToolbarLayout.setTitle(movie.getTitle());

        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initMovieData(Movie movie){
        ImageView poster = (ImageView) getView().findViewById(R.id.movieDetailsPoster);
        Picasso.with(getActivity().getApplicationContext()).load(movie.getPosterUrl(ImageMovie.Sizes.w185)).into(poster);

        ((TextView) getView().findViewById(R.id.movieDetailsTitle)).setText(movie.getTitle());
        ((TextView) getView().findViewById(R.id.movieDetailsOverview)).setText(movie.getOverview());
    }

    private void initGallery(Movie movie){
        mViewPager = (ViewPager) getView().findViewById(R.id.movieDetailsGallery);
        mViewPager.setPageMargin(20);
        mViewPager.setClipToPadding(false);

        int paddingSiblings = getPaddingSiblings();
        mViewPager.setPadding(paddingSiblings, 0, paddingSiblings, 0);

        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getActivity());
        api.getMovieImagesAsync(movie.getId(), this);
    }

    private int getPaddingSiblings(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return 480;
        return 120;
    }

    @Override
    public void onMovieMediaDataReceived(MovieImages movieMedia) {
        List<ImageMovie> gallery = movieMedia.getBackdrops();
        mAdapter = new GalleryPagerAdapter(getActivity(), gallery);

        mViewPager.setAdapter(mAdapter);

        if(gallery.size()>1)
            mViewPager.setCurrentItem(1);

        initPosterGallery(movieMedia.getPosters());
    }

    private void initPosterGallery(final List<ImageMovie> posters){
        ImageView poster = (ImageView) getView().findViewById(R.id.movieDetailsPoster);
        poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPostersGallery(posters);
            }
        });
    }

    private void openPostersGallery(List<ImageMovie> posters){
        List<ParcelableImageMovie> parcelablePosters = converToParcelable(posters);

        Intent postersGallery = new Intent(getActivity(), GalleryActivity.class);
        postersGallery.putParcelableArrayListExtra(GalleryActivity.ARG_IMAGES_MOVIE, (ArrayList<? extends Parcelable>) parcelablePosters);
        startActivity(postersGallery);
    }

    private List<ParcelableImageMovie> converToParcelable(List<ImageMovie> images){
        List<ParcelableImageMovie> parcelableImages = new ArrayList<>();
        for(ImageMovie image : images)
            parcelableImages.add(new ParcelableImageMovie(image));

        return parcelableImages;
    }

    @Override
    public void onError(Exception ex) {
        // Nothing to do here...
    }

    @Override
    public Loader<FullMovie> onCreateLoader(int id, Bundle args) {
        return new TakeListLoader(getActivity(), getBasicMovieData().getId());
    }

    @Override
    public void onLoadFinished(Loader<FullMovie> loader, FullMovie data) {
        this.mMovie = data;

        onMovieDataReceived(data);
    }

    @Override
    public void onLoaderReset(Loader<FullMovie> loader) {

    }

    private static class TakeListLoader extends AsyncTaskLoader<FullMovie> {

        private int mIdMovie;

        public TakeListLoader(Context context, int idMovie) {
            super(context);

            this.mIdMovie = idMovie;

            onContentChanged();
        }

        @Override
        public FullMovie loadInBackground() {
            DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getContext());
            try {
                return api.getMovieData(mIdMovie);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onStartLoading() {
            if (takeContentChanged())
                forceLoad();
        }

        @Override
        protected void onStopLoading() {
            cancelLoad();
        }
    }

    private static class GalleryPagerAdapter extends PagerAdapter{

        Context mContext;
        List<ImageMovie> mImages;

        public GalleryPagerAdapter(Context context, List<ImageMovie> images){
            super();

            this.mContext = context;
            this.mImages = images;
        }

        @Override
        public int getCount() {
            return mImages.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.gallery_item_view, container, false);

            ImageMovie image = mImages.get(position);

            ImageView galleryImage = (ImageView) rootView.findViewById(R.id.galleryImageMovie);
            Picasso.with(mContext).load(image.getUrl(ImageMovie.Sizes.w500)).into(galleryImage);

            setOnItemClickListener(rootView, position);

            TextView rate = (TextView) rootView.findViewById(R.id.galleryImageVoteRate);
            rate.setText(getRate(image.getVoteAverage()));

            container.addView(rootView);

            return rootView;
        }

        private String getRate(double average){
            return String.valueOf(Math.round(average*100)/100);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        private void setOnItemClickListener(View layout, final int position){
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openGallery(position);
                }
            });
        }

        private void openGallery(int position){
            Intent galleryIntent = new Intent(mContext, GalleryActivity.class);
            galleryIntent.putExtra(GalleryActivity.ARG_POSITION, position);
            galleryIntent.putParcelableArrayListExtra(GalleryActivity.ARG_IMAGES_MOVIE, (ArrayList<? extends Parcelable>) getParcelableImages());

            mContext.startActivity(galleryIntent);
        }

        private List<ParcelableImageMovie> getParcelableImages(){
            List<ParcelableImageMovie> images = new ArrayList<>();

            for(ImageMovie image : this.mImages)
                images.add(new ParcelableImageMovie(image));

            return images;
        }


    }
}
