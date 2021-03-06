package com.serloman.popularmovies.movieDetails;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.serloman.popularmovies.DefaultTheMovieDbApi;
import com.serloman.popularmovies.R;
import com.serloman.popularmovies.data.FavouriteMovies;
import com.serloman.popularmovies.gallery.GalleryActivity;
import com.serloman.popularmovies.models.ParcelableDiscoverMovie;
import com.serloman.popularmovies.models.ParcelableImageMovie;
import com.serloman.popularmovies.models.ParcelableVideoMovie;
import com.serloman.themoviedb_api.calls.MovieCallback;
import com.serloman.themoviedb_api.calls.MovieImagesCallback;
import com.serloman.themoviedb_api.calls.MovieVideosCallback;
import com.serloman.themoviedb_api.models.FullMovie;
import com.serloman.themoviedb_api.models.Genre;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.MovieImages;
import com.serloman.themoviedb_api.models.VideoMovie;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MovieDetailsFragment extends Fragment implements MovieCallback, LoaderManager.LoaderCallbacks<FullMovie>, MovieImagesCallback, MovieVideosCallback {

    private final static String STATUS_TRAILER_INFO = "STATUS_TRAILER_INFO";

    public final static String ARG_MOVIE_DATA = "ARG_MOVIE_DATA";

    public static MovieDetailsFragment newInstance(Movie movie){
        MovieDetailsFragment fragment = new MovieDetailsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE_DATA, new ParcelableDiscoverMovie(movie));
        fragment.setArguments(args);

        return fragment;
    }

    private ViewPager mViewPager;
    private PagerAdapter mAdapter;

    private MovieDetailsListener mMovieListener;
    private ParcelableVideoMovie mTrailerInfo;

    public MovieDetailsFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_details_fragment, container, false);

        if(savedInstanceState!=null)
            mTrailerInfo = savedInstanceState.getParcelable(STATUS_TRAILER_INFO);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        getLoaderManager().initLoader(0, null, this);

        initBasicData();

        setHasOptionsMenu(true);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof MovieDetailsListener)
            this.mMovieListener = (MovieDetailsListener) activity;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mTrailerInfo!=null)
            outState.putParcelable(STATUS_TRAILER_INFO, mTrailerInfo);

        super.onSaveInstanceState(outState);
    }

    private void initBasicData(){
        Movie movie = getBasicMovieData();

        initToolbar(movie);
        initBackDrop(movie);
        initMovieData(movie);
        initGallery(movie);
        initScoreTouch();
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

        ((TextView) getView().findViewById(R.id.movieDetailsGenres)).setText(getGenres(movie));
    }

    private void initScoreTouch(){
        View score = getView().findViewById(R.id.movieDetailsScore);
        score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openReviews();
            }
        });
    }

    private void openReviews(){
        if(mMovieListener!=null)
            mMovieListener.onScoreClicked(getBasicMovieData());
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
        Picasso.with(getActivity().getApplicationContext()).load(movie.getBackdropUrl(ImageMovie.Sizes.w780)).into(backDrop);

        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getActivity());
        api.getMovieVideosAsync(movie.getId(), this);
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
        Picasso.with(getActivity().getApplicationContext()).load(movie.getPosterUrl(ImageMovie.Sizes.w500)).into(poster);

        ((TextView) getView().findViewById(R.id.movieDetailsTitle)).setText(movie.getTitle());
        ((TextView) getView().findViewById(R.id.movieDetailsOverview)).setText(movie.getOverview());

        ((TextView) getView().findViewById(R.id.scoreVoteAverageTextView)).setText(String.valueOf(movie.getVoteAverage()));
        ((TextView) getView().findViewById(R.id.scoreVoteCountTextView)).setText(String.valueOf(movie.getVoteCount()));
    }

    private void initGallery(Movie movie){
        mViewPager = (ViewPager) getView().findViewById(R.id.movieDetailsGallery);
        mViewPager.setPageMargin(20);
        mViewPager.setClipToPadding(false);

        int paddingSiblings = getResources().getDimensionPixelSize(R.dimen.detail_gallery_item_padding);
        mViewPager.setPadding(paddingSiblings, 0, paddingSiblings, 0);

        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getActivity());
        api.getMovieImagesAsync(movie.getId(), this);
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
        List<ParcelableImageMovie> parcelablePosters = convertToParcelable(posters);

        Intent postersGallery = new Intent(getActivity(), GalleryActivity.class);
        postersGallery.putParcelableArrayListExtra(GalleryActivity.ARG_IMAGES_MOVIE, (ArrayList<? extends Parcelable>) parcelablePosters);
        startActivity(postersGallery);
    }

    private List<ParcelableImageMovie> convertToParcelable(List<ImageMovie> images){
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
        onMovieDataReceived(data);
    }

    @Override
    public void onLoaderReset(Loader<FullMovie> loader) {

    }

    @Override
    public void onMovieVideosDataReceived(List<VideoMovie> videos) {
        mTrailerInfo = new ParcelableVideoMovie(getYoutubeTrailer(videos));

        if(mTrailerInfo !=null){
            View playIcon = getView().findViewById(R.id.backdropPlayTrailer);
            playIcon.setVisibility(View.VISIBLE);

            View backdropContainer = getView().findViewById(R.id.backdropContainer);
            backdropContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playYoutubeTrailer(mTrailerInfo);
                }
            });
        }
    }

    private VideoMovie getYoutubeTrailer(List<VideoMovie> videos){
        for(VideoMovie video : videos)
            if(video.getSite().toLowerCase().compareTo("youtube")==0)
                return video;
        return null;
    }

    private void playYoutubeTrailer(VideoMovie trailerInfo){
        String youtubeUrl = VideoMovie.BASE_URL_YOUTUBE + trailerInfo.getKey();

        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl)));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

        inflater.inflate(R.menu.menu_movie_details, menu);

        MenuItem fav = menu.findItem(R.id.action_fav);
        updateFavButton(fav);

        super.onCreateOptionsMenu(menu, inflater);
    }

    private boolean isFavMovie(){
        FavouriteMovies fav = new FavouriteMovies(getActivity());
        return fav.isFavourited(getBasicMovieData().getId());
    }

    private void updateFavButton(MenuItem mButtonFav){
        if(isFavMovie())
            mButtonFav.setIcon(R.drawable.ic_action_important);
        else
            mButtonFav.setIcon(R.drawable.ic_action_not_important);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_fav:
                favAction(item);
                return true;
            case R.id.action_share_trailer:
                shareTrailer();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void favAction(MenuItem item){
        if(isFavMovie())
            unfavMovie(item);
        else
            favMovie(item);
    }

    private void favMovie(MenuItem item){
        FavouriteMovies fav = new FavouriteMovies(getActivity());
        fav.saveFavourite(getBasicMovieData());

        item.setIcon(R.drawable.ic_action_important);
    }

    private void unfavMovie(MenuItem item){
        FavouriteMovies fav = new FavouriteMovies(getActivity());
        fav.deleteFavourite(getBasicMovieData());

        item.setIcon(R.drawable.ic_action_not_important);
    }

    private void shareTrailer(){
        if(mTrailerInfo==null){
            Toast.makeText(getActivity(), mTrailerInfo.getName(), Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = getShareIntent();
        startActivity(shareIntent);
    }

    private Intent getShareIntent(){
        String urlTrailer = VideoMovie.BASE_URL_YOUTUBE + mTrailerInfo.getKey();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, urlTrailer);
        sendIntent.setType("text/plain");

        return sendIntent;
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
            Picasso.with(mContext).load(image.getUrl(ImageMovie.Sizes.w780)).into(galleryImage);

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

    public interface MovieDetailsListener{
        void onScoreClicked(Movie movie);
    }
}
