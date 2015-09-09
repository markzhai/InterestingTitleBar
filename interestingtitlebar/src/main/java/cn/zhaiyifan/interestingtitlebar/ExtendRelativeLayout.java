package cn.zhaiyifan.interestingtitlebar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

public class ExtendRelativeLayout extends RelativeLayout {

    private boolean mLayoutConsiderPadding = true;

    public ExtendRelativeLayout(Context context) {
        super(context);
    }

    public ExtendRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLayoutConsiderPadding(boolean consider) {
        if (mLayoutConsiderPadding != consider) {
            mLayoutConsiderPadding = consider;
            requestLayout();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        // The layout has actually already been performed and the positions cached.
        // Apply the cached values to the children.
        super.onLayout(changed, l, t, r, b);

        if (mLayoutConsiderPadding) {
            int count = getChildCount();
            for (int i = 0; i < count; i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == GONE) {
                    continue;
                }

                int xOffset = 0;
                LayoutParams params = (LayoutParams) child.getLayoutParams();
                final int[] rules = params.getRules();
                if (rules[CENTER_IN_PARENT] != 0 || rules[CENTER_VERTICAL] != 0) {
                    xOffset = (getPaddingTop() - getPaddingBottom()) / 2;
                }
                if (xOffset != 0) {
                    child.offsetTopAndBottom(xOffset);
                }
            }
        }
    }
}

