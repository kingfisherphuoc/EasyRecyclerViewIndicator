package com.kingfisher.easyviewindicator;

import android.content.Context;
import android.util.AttributeSet;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by kingfisher on 3/5/18.
 */

public class RecyclerViewIndicator extends AnyViewIndicator {
    public RecyclerViewIndicator(Context context) {
        super(context);
    }

    public RecyclerViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RecyclerViewIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected int getItemCount() {
        if (recyclerView != null && recyclerView.getAdapter() != null) {
            return recyclerView.getAdapter().getItemCount();
        }
        return 0;
    }

    @Override
    protected int getCurrentPosition() {
        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    RecyclerView recyclerView;
    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            switch (newState) {
                case RecyclerView.SCROLL_STATE_IDLE:
                    if (getItemCount() <= 0) {
                        return;
                    }
                    onCurrentLocationChange();
                    break;
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }
    };

    /**
     * @param recyclerView
     */
    public void setRecyclerView(final RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        if (recyclerView != null) {
            recyclerView.post(new Runnable() {
                @Override
                public void run() {
                    mLastPosition = getItemCount() > 0 ? 0 : -1;
                    updateCircleIndicator();
                    recyclerView.removeOnScrollListener(onScrollListener);
                    recyclerView.addOnScrollListener(onScrollListener);
                    onScrollListener.onScrollStateChanged(recyclerView, RecyclerView.SCROLL_STATE_IDLE);
                }
            });
        }
    }

    /**
     * Force update indicators when new item is insert/remove from the Adapter
     */
    public void forceUpdateItemCount() {
        updateCircleIndicator();
    }


}
