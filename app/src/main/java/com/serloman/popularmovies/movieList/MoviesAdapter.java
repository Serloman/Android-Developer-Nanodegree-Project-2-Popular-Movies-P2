package com.serloman.popularmovies.movieList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.models.ImageMovie;
import com.serloman.themoviedb_api.models.Movie;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder>{

    private Context mContext;
    private List<Movie> mMovieDataSet;
    private ImageMovie.Sizes mSize;
    private int mDefaultColor;

    private MovieSelectedListener mMovieListener;

    public MoviesAdapter(Context context, List<Movie> dataSet, MovieSelectedListener listener){
        this(context, dataSet, ImageMovie.Sizes.w342, listener);
    }

    public MoviesAdapter(Context context, List<Movie> dataSet, ImageMovie.Sizes size, MovieSelectedListener listener){
        this.mContext = context;
        this.mMovieDataSet = dataSet;
        this.mSize = size;
        this.mMovieListener = listener;

        this.mDefaultColor = Color.parseColor("#ff59b4ff"); //context.getResources().getColor(R.color.title_color);
    }

    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int i) {
        View movieSingleView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_single_view, parent, false);

        return new MovieHolder(movieSingleView, new MovieHolder.MovieClickedListener() {
            @Override
            public void onClick(int position) {
                mMovieListener.onMovieSelected(mMovieDataSet.get(position));
            }
        });
    }

    @Override
    public void onBindViewHolder(MovieHolder movieHolder, int i) {
        Movie movie = mMovieDataSet.get(i);

        final TextView titleView = movieHolder.getTitleView();
        final ImageView posterView = movieHolder.getPosterView();
        final View titleContainer = movieHolder.getTitleContainer();

        titleView.setText(movie.getTitle());

        Picasso.with(mContext).load(movie.getPosterUrl(mSize)).into(posterView, new Callback() {
            @Override
            public void onSuccess() {
//                if (posterView.getDrawable() != null)
                    updateTitleColor(titleContainer, titleView, posterView.getDrawable());
            }

            @Override
            public void onError() {

            }
        });
    }

    private void updateTitleColor(View container, TextView text, Drawable drawable){
        Bitmap image = ((BitmapDrawable) drawable).getBitmap();
        updateTitleColor(container, text, image);
    }

    private void updateTitleColor(final View container, final TextView titleText, Bitmap image){
        Palette.from(image).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                int backgroundColor = palette.getDarkVibrantColor(mDefaultColor);
                container.setBackgroundColor(backgroundColor);

                int titleColor = Color.WHITE; //palette.getLightMutedColor(Color.WHITE);
                titleText.setTextColor(titleColor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMovieDataSet.size();
    }

    public void addMoreMovies(List<Movie> movies){
        for(Movie movie : movies)
            this.mMovieDataSet.add(movie);

        this.notifyDataSetChanged();
    }

    public static class MovieHolder extends RecyclerView.ViewHolder{

        interface MovieClickedListener{
            void onClick(int position);
        }

        private View mRootView;
        private TextView mTitleView;
        private ImageView mPosterView;
        private View mTitleContainer;
        private MovieClickedListener mListener;

        public MovieHolder(View movieSingleView, MovieClickedListener listener) {
            super(movieSingleView);

            this.mListener = listener;
            this.mRootView = movieSingleView;

            mTitleView = (TextView) movieSingleView.findViewById(R.id.movieSingleViewTitle);
            mPosterView = (ImageView) movieSingleView.findViewById(R.id.movieSingleViewPoster);
            mTitleContainer = movieSingleView.findViewById(R.id.movieSingleViewTitleContainer);

            initClickListener();
        }

        private void initClickListener(){
            mRootView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onClick(getAdapterPosition());
                }
            });
        }

        public TextView getTitleView(){
            return mTitleView;
        }

        public ImageView getPosterView(){
            return mPosterView;
        }

        public View getTitleContainer(){
            return mTitleContainer;
        }
    }

    public interface MovieSelectedListener{
        void onMovieSelected(Movie movie);
    }
}
