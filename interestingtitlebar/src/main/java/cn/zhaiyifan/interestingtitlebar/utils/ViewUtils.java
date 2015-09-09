package cn.zhaiyifan.interestingtitlebar.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by markzhai on 2015/1/14.
 */
public class ViewUtils {

    static private float density = -1;
    static private int screenWidth = -1;
    static private int screenHeight = -1;

    public static void init(Context context) {
        if (density == -1) {
            DisplayMetrics metrics = context.getResources().getDisplayMetrics();
            density = metrics.density;
            if (metrics.widthPixels < metrics.heightPixels) {
                screenWidth = metrics.widthPixels;
                screenHeight = metrics.heightPixels;
            } else {   // 部分机器使用application的context宽高反转
                screenHeight = metrics.widthPixels;
                screenWidth = metrics.heightPixels;
            }
        }
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId);
        }
        if (result <= 0) {
            result = dpToPx(25);
        }
        return result;
    }

    public static float getDensity() {
        return density;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int dpToPx(float dp) {
        return Math.round(dp * getDensity());
    }

    public static int PxToDp(float px) {
        return Math.round(px / getDensity());
    }

    /**
     * 在xml里设置android:alpha对api11以前的系统不起作用，所以在代码里获取并设置透明度
     */
    public static void setAlpha(View view, float alphaValue) {
        if (view == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            view.setAlpha(alphaValue);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(alphaValue, alphaValue);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            view.startAnimation(alpha);
        }
    }
}