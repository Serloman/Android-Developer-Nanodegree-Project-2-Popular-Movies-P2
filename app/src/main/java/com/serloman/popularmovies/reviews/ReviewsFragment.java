package com.serloman.popularmovies.reviews;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.serloman.popularmovies.DefaultTheMovieDbApi;
import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.calls.MovieReviewsCallback;
import com.serloman.themoviedb_api.models.Movie;
import com.serloman.themoviedb_api.models.ReviewMovie;

import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class ReviewsFragment extends Fragment implements MovieReviewsCallback{

    public final static String ARG_MOVIE = "ARG_MOVIE";

    public static ReviewsFragment newInstance(Movie movie){
        ReviewsFragment fragment = new ReviewsFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, (Parcelable) movie);
        fragment.setArguments(args);

        return fragment;
    }

    private RecyclerView mRecyclerView;
    private ReviewsAdapter mAdapter;

    public ReviewsFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.reviews_fragment, container, false);

        initList(rootView);

        return rootView;
    }

    private void initList(View rootView){
        mAdapter = new ReviewsAdapter();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.reviewsRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getActivity());
        api.getReviewsMovieAsync(getMovie().getId(), this);
    }

    private Movie getMovie(){
        return this.getArguments().getParcelable(ARG_MOVIE);
    }

    @Override
    public void onMovieReviewsDataReceived(List<ReviewMovie> reviews) {
        mAdapter.addReviews(reviews);

        getView().findViewById(R.id.reviewsProgressBar).setVisibility(View.GONE);
    }

    @Override
    public void onError(Exception ex) {

    }
}
