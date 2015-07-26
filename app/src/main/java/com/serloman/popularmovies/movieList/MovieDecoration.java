package com.serloman.popularmovies.movieList;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Serloman on 20/07/2015.
 */
public class MovieDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    private int mNumColumns;

    public MovieDecoration(int numColumns, int space) {
        this.mSpace = space;
        this.mNumColumns = numColumns;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        outRect.bottom = mSpace;
        outRect.right = mSpace;

        int position = parent.getChildPosition(view);

        if (position % mNumColumns == 0)
            outRect.left = mSpace;

        // Add top margin only for the mSpace item/s to avoid double space between items
        if (position < mNumColumns)
            outRect.top = mSpace;
    }
}