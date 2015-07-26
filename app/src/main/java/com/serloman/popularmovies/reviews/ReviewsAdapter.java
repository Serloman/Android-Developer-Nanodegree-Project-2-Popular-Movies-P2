package com.serloman.popularmovies.reviews;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.serloman.popularmovies.R;
import com.serloman.themoviedb_api.models.ReviewMovie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Serloman on 26/07/2015.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewHolder> {

    private List<ReviewMovie> mReviews;

    public ReviewsAdapter(){
        this(new ArrayList<ReviewMovie>());
    }

    public ReviewsAdapter(List<ReviewMovie> reviews){
        super();

        this.mReviews = reviews;
    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View reviewLayout = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_single_view, parent, false);

        return new ReviewHolder(reviewLayout);
    }

    @Override
    public void onBindViewHolder(ReviewHolder reviewHolder, int i) {
        ReviewMovie review = mReviews.get(i);

        reviewHolder.mAuthor.setText(review.getAuthorName());
        reviewHolder.mContent.setText(review.getContent());

//        reviewHolder.updateData(review);
    }

    @Override
    public int getItemCount() {
        return mReviews.size();
    }

    public void addReviews(List<ReviewMovie> newReviews){
        for(ReviewMovie review : newReviews)
            mReviews.add(review);

        notifyDataSetChanged();
    }


    public static class ReviewHolder extends RecyclerView.ViewHolder {

        private View mRootView;
        private TextView mAuthor;
        private TextView mContent;

        public ReviewHolder(View itemView) {
            super(itemView);

            mRootView = itemView;
            mAuthor = (TextView) itemView.findViewById(R.id.reviewAuthor);
            mContent = (TextView) itemView.findViewById(R.id.reviewContent);
        }

        public void updateData(ReviewMovie review){
            mAuthor.setText(review.getAuthorName());
            mContent.setText(review.getContent());
        }
    }
}
