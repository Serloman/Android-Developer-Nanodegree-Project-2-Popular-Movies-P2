package com.serloman.popularmovies.cast;

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
import com.serloman.themoviedb_api.calls.MovieCreditsCallback;
import com.serloman.themoviedb_api.models.CreditsMovie;
import com.serloman.themoviedb_api.models.Movie;

/**
 * Created by Serloman on 28/07/2015.
 */
public class CastFragment extends Fragment implements MovieCreditsCallback{
    public static final String ARG_MOVIE = "ARG_MOVIE";

    public static CastFragment newInstance(Movie movie){
        CastFragment fragment = new CastFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIE, (Parcelable) movie);
        fragment.setArguments(args);

        return fragment;
    }

    private RecyclerView mRecyclerView;
    private CastAdapter mAdapter;

    public CastFragment() { }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cast_fragment, container, false);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        initRecyclerView();

        DefaultTheMovieDbApi api = new DefaultTheMovieDbApi(getActivity());
        api.getMovieCreditsAsync(getMovie().getId(), this);
    }

    private void initRecyclerView(){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mAdapter = new CastAdapter(getActivity());

        mRecyclerView = (RecyclerView) getView().findViewById(R.id.castRecyclerView);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private Movie getMovie(){
        return getArguments().getParcelable(ARG_MOVIE);
    }

    @Override
    public void onMovieCreditsDataReceived(CreditsMovie credits) {
        mAdapter.addMoreCastMembers(credits.getCast());
        hideLoading();
    }

    @Override
    public void onError(Exception ex) {
        hideLoading();
    }

    private void hideLoading(){
        getView().findViewById(R.id.castProgressBar).setVisibility(View.GONE);
    }
}
