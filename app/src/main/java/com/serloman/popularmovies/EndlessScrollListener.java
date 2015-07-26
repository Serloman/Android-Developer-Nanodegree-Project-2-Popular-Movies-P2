package com.serloman.popularmovies;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Serloman on 23/07/2015.
 *
 * Inspired from the work of https://github.com/codepath/android_guides/wiki/Endless-Scrolling-with-AdapterViews
 */
public class EndlessScrollListener extends RecyclerView.OnScrollListener {
    public interface OnEndlessScrollListener{
        void onLoadPage(int numPage);
    }

    private int mVisibleThreshold;
    private int mCurrentPage;
    private int mPreviousTotal;
    private boolean mLoading;

    private OnEndlessScrollListener mListener;

    public EndlessScrollListener(OnEndlessScrollListener listener)
    {
        this(listener, 5);
    }

    public EndlessScrollListener(OnEndlessScrollListener listener, int visibleThreshold)
    {
        this.mListener = listener;

        this.mVisibleThreshold = visibleThreshold;
        mCurrentPage = 1;
        mPreviousTotal = 0;
        mLoading = true;
    }

    public boolean isLoading(){
        return mLoading;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        int totalItemCount = layoutManager.getItemCount();
        int visibleItemCount = layoutManager.getChildCount();

        int firstVisibleItem = 0;
        if(layoutManager instanceof GridLayoutManager)
            firstVisibleItem = ((GridLayoutManager)layoutManager).findFirstVisibleItemPosition();
        else if(layoutManager instanceof LinearLayoutManager)
            firstVisibleItem = ((LinearLayoutManager)layoutManager).findFirstVisibleItemPosition();

        if (mLoading) {
            if (totalItemCount > mPreviousTotal) {
                mLoading = false;
                mPreviousTotal = totalItemCount;
                mCurrentPage++;
            }
        }
        if (!mLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + mVisibleThreshold)) {
            mLoading = true;
            mListener.onLoadPage(mCurrentPage);
        }
    }

    public void pageLoadFailed(){
        mCurrentPage--;
        mCurrentPage = Math.max(0, mCurrentPage);

        mPreviousTotal -= mVisibleThreshold;
        mPreviousTotal = Math.max(0, mPreviousTotal);
    }
}
