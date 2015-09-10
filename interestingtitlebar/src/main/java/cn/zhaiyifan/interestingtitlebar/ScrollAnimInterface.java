package cn.zhaiyifan.interestingtitlebar;

import android.widget.AbsListView;

/**
 * Interface for scroll animation.
 * <p>
 * Created by markzhai on 2015/3/8.
 */
public interface ScrollAnimInterface {
    /**
     * For ListView usage
     */
    void onScroll(AbsListView view, int firstVisibleItem, int endFadeItem);

    /**
     * For ScrollView usage
     */
    void onScroll(int y);
}