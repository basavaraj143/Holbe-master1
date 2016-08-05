package com.appsriv.holbe.Adatpters;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by amit on 3/15/16.
 */
public class CustomViewPager extends ViewPager
{

    private float initialXValue;
    private SwipeDirection direction;
    float mStartDragX;
    OnSwipeOutListener mListener;

    public void setOnSwipeOutListener(OnSwipeOutListener listener) {
        mListener = listener;
    }


    public interface OnSwipeOutListener
    {
        public void onSwipeOutAtStart();
        public void onSwipeOutAtEnd();
    }


    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.direction = SwipeDirection.all;
    }

    public enum SwipeDirection
    {
        all, left, right, none;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (this.IsSwipeAllowed(event))
        {
            float x = event.getX();
            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    mStartDragX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mStartDragX < x && getCurrentItem() == 0)
                    {
                        mListener.onSwipeOutAtStart();
                    } else if (mStartDragX > x && getCurrentItem() == getAdapter().getCount() - 1)
                    {
                        mListener.onSwipeOutAtEnd();
                    }
                    break;
            }
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.IsSwipeAllowed(event)) {
            float x = event.getX();
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mStartDragX = x;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mStartDragX < x && getCurrentItem() == 0) {
                        mListener.onSwipeOutAtStart();
                    } else if (mStartDragX > x && getCurrentItem() == getAdapter().getCount() - 1) {
                        mListener.onSwipeOutAtEnd();
                    }
                    break;
            }
            //return super.onInterceptTouchEvent(ev);
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    private boolean IsSwipeAllowed(MotionEvent event)
    {
        if (this.direction == SwipeDirection.all) return true;

        if (direction == SwipeDirection.none)//disable any swipe
            return false;

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            initialXValue = event.getX();
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE)
        {
            try {
                float diffX = event.getX() - initialXValue;
                if (diffX > 0 && direction == SwipeDirection.right)
                {
                    // swipe from left to right detected
                    return false;
                } else if (diffX < 0 && direction == SwipeDirection.left) {
                    // swipe from right to left detected
                    return false;
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return true;
    }

    public void setAllowedSwipeDirection(SwipeDirection direction) {
        this.direction = direction;
    }
}
