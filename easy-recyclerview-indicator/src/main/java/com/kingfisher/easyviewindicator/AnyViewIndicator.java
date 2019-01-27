package com.kingfisher.easyviewindicator;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;

import androidx.annotation.AnimatorRes;
import androidx.annotation.DrawableRes;

/**
 * Created by kingfisher on 3/5/18.
 */

public class AnyViewIndicator extends LinearLayout {

    private final static int DEFAULT_INDICATOR_WIDTH = 5;
    protected int mLastPosition = -1;
    //    private ViewPager mViewpager;
//    private RecyclerView recyclerView;
    private int mIndicatorMargin = -1;
    private int mIndicatorWidth = -1;
    private int mIndicatorHeight = -1;
    private int mAnimatorResId = R.animator.scale_with_alpha;
    private int mAnimatorReverseResId = 0;
    private int mIndicatorBackgroundResId = R.drawable.white_radius;
    private int mIndicatorUnselectedBackgroundResId = R.drawable.white_radius;
    private Animator mAnimatorOut;
    private Animator mAnimatorIn;
    private Animator mImmediateAnimatorOut;
    private Animator mImmediateAnimatorIn;
    private boolean isAnimationEnable = true;
    private int currentPosition;
    private int itemCount;

    public AnyViewIndicator(Context context) {
        super(context);
        init(context, null);
    }

    public AnyViewIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public AnyViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public AnyViewIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        handleTypedArray(context, attrs);
        checkIndicatorConfig(context);
    }

    private void handleTypedArray(Context context, AttributeSet attrs) {
        if (attrs == null) {
            return;
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AnyViewIndicator);
        mIndicatorWidth =
                typedArray.getDimensionPixelSize(R.styleable.AnyViewIndicator_avi_width, -1);
        mIndicatorHeight =
                typedArray.getDimensionPixelSize(R.styleable.AnyViewIndicator_avi_height, -1);
        mIndicatorMargin =
                typedArray.getDimensionPixelSize(R.styleable.AnyViewIndicator_avi_margin, -1);

        mAnimatorResId = typedArray.getResourceId(R.styleable.AnyViewIndicator_avi_animator,
                R.animator.scale_with_alpha);
        mAnimatorReverseResId =
                typedArray.getResourceId(R.styleable.AnyViewIndicator_avi_animator_reverse, 0);
        mIndicatorBackgroundResId =
                typedArray.getResourceId(R.styleable.AnyViewIndicator_avi_drawable,
                        R.drawable.white_radius);
        mIndicatorUnselectedBackgroundResId =
                typedArray.getResourceId(R.styleable.AnyViewIndicator_avi_drawable_unselected,
                        mIndicatorBackgroundResId);
        isAnimationEnable = typedArray.getBoolean(R.styleable.AnyViewIndicator_avi_animation_enable, true);


        int orientation = typedArray.getInt(R.styleable.AnyViewIndicator_avi_orientation, -1);
        setOrientation(orientation == VERTICAL ? VERTICAL : HORIZONTAL);

        int gravity = typedArray.getInt(R.styleable.AnyViewIndicator_avi_gravity, -1);
        setGravity(gravity >= 0 ? gravity : Gravity.CENTER);

        typedArray.recycle();
    }

    /**
     * Create and configure Indicator in Java code.
     */
    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin) {
        configureIndicator(indicatorWidth, indicatorHeight, indicatorMargin,
                R.animator.scale_with_alpha, 0, R.drawable.white_radius, R.drawable.white_radius);
    }

    public void configureIndicator(int indicatorWidth, int indicatorHeight, int indicatorMargin,
                                   @AnimatorRes int animatorId, @AnimatorRes int animatorReverseId,
                                   @DrawableRes int indicatorBackgroundId,
                                   @DrawableRes int indicatorUnselectedBackgroundId) {

        mIndicatorWidth = indicatorWidth;
        mIndicatorHeight = indicatorHeight;
        mIndicatorMargin = indicatorMargin;

        mAnimatorResId = animatorId;
        mAnimatorReverseResId = animatorReverseId;
        mIndicatorBackgroundResId = indicatorBackgroundId;
        mIndicatorUnselectedBackgroundResId = indicatorUnselectedBackgroundId;

        checkIndicatorConfig(getContext());
    }

    private void checkIndicatorConfig(Context context) {
        mIndicatorWidth = (mIndicatorWidth < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorWidth;
        mIndicatorHeight =
                (mIndicatorHeight < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorHeight;
        mIndicatorMargin =
                (mIndicatorMargin < 0) ? dip2px(DEFAULT_INDICATOR_WIDTH) : mIndicatorMargin;

        mAnimatorResId = (mAnimatorResId == 0) ? R.animator.scale_with_alpha : mAnimatorResId;

        mAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut = createAnimatorOut(context);
        mImmediateAnimatorOut.setDuration(0);

        mAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn = createAnimatorIn(context);
        mImmediateAnimatorIn.setDuration(0);

        mIndicatorBackgroundResId = (mIndicatorBackgroundResId == 0) ? R.drawable.white_radius
                : mIndicatorBackgroundResId;
        mIndicatorUnselectedBackgroundResId =
                (mIndicatorUnselectedBackgroundResId == 0) ? mIndicatorBackgroundResId
                        : mIndicatorUnselectedBackgroundResId;
    }

    private Animator createAnimatorOut(Context context) {
        return AnimatorInflater.loadAnimator(context, mAnimatorResId);
    }
//    public void setRecyclerView(final RecyclerView recyclerView) {
//        this.recyclerView = recyclerView;
//        if (recyclerView != null) {
//            recyclerView.post(new Runnable() {
//                @Override
//                public void run() {
//                    mLastPosition = recyclerView.getAdapter().getItemCount() > 0 ? 0 : -1;
//                    createIndicators();
//                    recyclerView.removeOnScrollListener(onScrollListener);
//                    recyclerView.addOnScrollListener(onScrollListener);
//                    onScrollListener.onScrollStateChanged(recyclerView, RecyclerView.SCROLL_STATE_IDLE);
//                }
//            });
//        }
//    }

//    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
//        @Override
//        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//            super.onScrollStateChanged(recyclerView, newState);
//
//            switch (newState) {
//                case RecyclerView.SCROLL_STATE_IDLE:
//                    if (getItemCount() <= 0) {
//                        return;
//                    }
//                    onCurrentLocationChange();
//                    break;
//            }
//        }
//
//        @Override
//        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//            super.onScrolled(recyclerView, dx, dy);
//        }
//    };

    private Animator createAnimatorIn(Context context) {
        Animator animatorIn;
        if (mAnimatorReverseResId == 0) {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorResId);
            animatorIn.setInterpolator(new ReverseInterpolator());
        } else {
            animatorIn = AnimatorInflater.loadAnimator(context, mAnimatorReverseResId);
        }
        return animatorIn;
    }

    /**
     * Force the circle indicator upgrade
     */
    protected void updateCircleIndicator() {
        int newCount = getItemCount();
        int currentCount = getChildCount();

        if (newCount == currentCount) {  // No change
            return;
        } else if (mLastPosition < newCount) {
            mLastPosition = getCurrentPosition();
        } else {
            mLastPosition = -1;
        }

        // show the first
        if (mLastPosition == -1 && newCount > 0) {
            mLastPosition = 0;
        }

        createIndicators();
    }

    protected void onCurrentLocationChange() {
        if (isAnimationEnable) {
            if (mAnimatorIn.isRunning()) {
                mAnimatorIn.end();
                mAnimatorIn.cancel();
            }

            if (mAnimatorOut.isRunning()) {
                mAnimatorOut.end();
                mAnimatorOut.cancel();
            }
        }

        View currentIndicator;
        if (mLastPosition >= 0 && (currentIndicator = getChildAt(mLastPosition)) != null) {
            currentIndicator.setBackgroundResource(mIndicatorUnselectedBackgroundResId);

            if (isAnimationEnable) {
                mAnimatorIn.setTarget(currentIndicator);
                mAnimatorIn.start();
            }
        }
        int position = getCurrentPosition();
        View selectedIndicator = getChildAt(position);
        if (selectedIndicator != null) {
            selectedIndicator.setBackgroundResource(mIndicatorBackgroundResId);
            if (isAnimationEnable) {
                mAnimatorOut.setTarget(selectedIndicator);
                mAnimatorOut.start();
            }
        }
        mLastPosition = position;
    }

    protected int getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
        updateCircleIndicator();
    }

    protected int getCurrentPosition() {
//        return ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
        onCurrentLocationChange();
    }

    private void createIndicators() {
        removeAllViews();
//        int count = recyclerView.getAdapter().getItemCount();
        int count = getItemCount();
        if (count <= 0) {
            return;
        }
        int currentItem = getCurrentPosition();
        int orientation = getOrientation();

        if (currentItem < 0) currentItem = 0;
        for (int i = 0; i < count; i++) {
            if (currentItem == i) {
                addIndicator(orientation, mIndicatorBackgroundResId, mImmediateAnimatorOut);
            } else {
                addIndicator(orientation, mIndicatorUnselectedBackgroundResId,
                        mImmediateAnimatorIn);
            }
        }
    }

    private void addIndicator(int orientation, @DrawableRes int backgroundDrawableId,
                              Animator animator) {
        if (isAnimationEnable) {
            if (animator.isRunning()) {
                animator.end();
                animator.cancel();
            }
        }

        View Indicator = new View(getContext());
        Indicator.setBackgroundResource(backgroundDrawableId);
        addView(Indicator, mIndicatorWidth, mIndicatorHeight);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) Indicator.getLayoutParams();

        if (orientation == HORIZONTAL) {
            lp.leftMargin = mIndicatorMargin;
            lp.rightMargin = mIndicatorMargin;
        } else {
            lp.topMargin = mIndicatorMargin;
            lp.bottomMargin = mIndicatorMargin;
        }

        Indicator.setLayoutParams(lp);


        if (isAnimationEnable) {
            animator.setTarget(Indicator);
            animator.start();
        }
    }

    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private class ReverseInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float value) {
            return Math.abs(1.0f - value);
        }

    }
}