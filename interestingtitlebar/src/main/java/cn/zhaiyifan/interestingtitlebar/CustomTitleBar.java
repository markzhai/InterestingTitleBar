package cn.zhaiyifan.interestingtitlebar;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.LinkedList;

/**
 * Immersive title bar with scroll animation
 * <p/>
 * Created by markzhai on 2015/1/12.
 */
public class CustomTitleBar extends ImmersiveTitleBar implements ScrollAnimInterface {
    private static final String TAG = "CustomTitleBar";

    // ============================================================================
    // Scroll animation
    // ============================================================================

    // store original color
    private static final int ORIGIN_COLOR = -12345;

    private boolean mTransparentEnabled = false;
    // start position of fading animation
    private int mStartFadePosition = 80;
    // end position of fading animation
    private int mEndFadePosition = 380;
    // Max alpha, by default 247(0.97*255)
    private int mMaxAlpha = 247;
    // fade area
    private int mFadeDuration = mEndFadePosition - mStartFadePosition;
    // text shadow when completely transparent
    private float mShadowRadius = 0;
    private int mBarTitleTextShadowColor;

    // View needs to do gradual change during scroll animation
    private LinkedList<View> mFadeViewList = null;
    private TextView mTitleTextView = null;
    private ColorStateList mOriginBarTitleColor = null;

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    /**
     * Set transparent title bar anim.
     *
     * @param enabled whether to open anim
     */
    public void setTransparentEnabled(boolean enabled) {
        setTransparentEnabled(enabled, mStartFadePosition, mEndFadePosition, mMaxAlpha);
    }

    /**
     * Set transparent title bar anim.
     *
     * @param enabled whether to open anim
     * @param start   start position of fading animation
     * @param end     end position of fading animation
     */
    public void setTransparentEnabled(boolean enabled, int start, int end) {
        setTransparentEnabled(enabled, start, end, mMaxAlpha);
    }

    /**
     * Set transparent title bar anim.
     *
     * @param enabled  whether to open anim
     * @param start    start position of fading animation
     * @param end      end position of fading animation
     * @param maxAlpha max alpha(0-255)
     */
    public void setTransparentEnabled(boolean enabled, int start, int end, int maxAlpha) {
        mTransparentEnabled = enabled;
        if (mTransparentEnabled) {
            mStartFadePosition = start;
            mEndFadePosition = end;
            mMaxAlpha = maxAlpha;
            mFadeDuration = mEndFadePosition - mStartFadePosition;
        }
    }

    /**
     * Add view that needs gradual change
     */
    public void addViewToFadeList(View view) {
        if (mFadeViewList == null) {
            mFadeViewList = new LinkedList<View>();
        }
        if (view != null) {
            mFadeViewList.add(view);
        }
    }

    /**
     * remove view that has been added by addViewToFadeList
     */
    public void removeViewFromFadeList(View view) {
        if (mFadeViewList != null && view != null) {
            mFadeViewList.remove(view);
        }
    }

    public void setTitle(String title) {
        if (!TextUtils.isEmpty(title) && mTitleTextView != null) {
            mTitleTextView.setText(title);
        }
    }

    public void setTitleTextView(TextView textView) {
        mTitleTextView = textView;
        if (mTitleTextView != null) {
            mOriginBarTitleColor = mTitleTextView.getTextColors();
        }
    }

    public void setTextShadowColor(int color) {
        mBarTitleTextShadowColor = color;
    }

    private void setTitleBarTranslate(int alpha) {
        if (getBackground() == null) {
            return;
        }
        getBackground().mutate().setAlpha(alpha);
        if (mFadeViewList == null || mTitleTextView == null) {
            return;
        }
        if (alpha > 0) {
            setTitleBarShadowLayer(0);
        }
        if (alpha >= mMaxAlpha) {
            setTitleBarColor(ORIGIN_COLOR);
        } else {
            setTitleBarColor(interpolateColor(Color.WHITE, mOriginBarTitleColor.getDefaultColor(), alpha, mMaxAlpha));
            if (alpha == 0) {
                setTitleBarShadowLayer(1f);
            }
        }
    }

    private void setTitleBarColor(int color) {
        for (View view : mFadeViewList) {
            setViewColor(view, color);
        }
    }

    private void setViewColor(View view, int color) {
        if (view == null || view.getVisibility() != VISIBLE) {
            return;
        }
        if (view instanceof TextView) {
            setViewColor((TextView) view, color);
        }
        if (view instanceof ImageView) {
            setViewColor((ImageView) view, color);
        }
    }

    private void setViewColor(TextView view, int color) {
        if (color == ORIGIN_COLOR) {
            view.setTextColor(mOriginBarTitleColor);
            if (view.getBackground() != null) {
                view.getBackground().clearColorFilter();
            }
        } else {
            view.setTextColor(color);
            if (view.getBackground() != null) {
                view.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    private void setViewColor(ImageView view, int color) {
        if (color == ORIGIN_COLOR) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void setTitleBarShadowLayer(float radius) {
        if (mShadowRadius != radius) {
            mShadowRadius = radius;
            for (View view : mFadeViewList) {
                if (view instanceof TextView) {
                    ((TextView) view).setShadowLayer(mShadowRadius, 0, 1, mBarTitleTextShadowColor);
                }
            }
        }
    }

    /**
     * onScroll callback interface for ListView
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int endFadeItem) {
        if (mTransparentEnabled && firstVisibleItem <= endFadeItem) {
            View v = view.getChildAt(0);
            // Top of visible window, originally 0, turn to negative when scroll to lower position
            int top = (v == null) ? 0 : v.getTop();
            // distance from top of current window to ending position of fade
            int delta = top + mEndFadePosition;
            setTitleBarTranslate(interpolate(delta));
        }
    }

    /**
     * onScroll callback interface for ScrollView
     */
    @Override
    public void onScroll(int y) {
        if (mTransparentEnabled) {
            setTitleBarTranslate(interpolate(mEndFadePosition - y));
        }
    }

    /**
     * @param delta distance to ending position
     * @return alpha value at current position
     */
    private int interpolate(int delta) {
        if (delta > mFadeDuration) {
            return 0;
        } else if (delta <= 0) {
            return mMaxAlpha;
        } else {
            float temp = ((float) delta) / mFadeDuration;
            return (int) ((1 - temp) * mMaxAlpha);
        }
    }

    public static int interpolateColor(int colorFrom, int colorTo, int posFrom, int posTo) {
        float delta = posTo - posFrom;
        int red = (int) ((Color.red(colorFrom) - Color.red(colorTo)) * delta / posTo + Color.red(colorTo));
        int green = (int) ((Color.green(colorFrom) - Color.green(colorTo)) * delta / posTo + Color.green(colorTo));
        int blue = (int) ((Color.blue(colorFrom) - Color.blue(colorTo)) * delta / posTo) + Color.blue(colorTo);
        return Color.argb(255, red, green, blue);
    }
}