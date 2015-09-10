package cn.zhaiyifan.interestingtitlebar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import cn.zhaiyifan.interestingtitlebar.CustomTitleBar;

/**
 * Demo for usage when set fitsSystemWindows="true"
 * <p/>
 * Created by markzhai on 2015/4/11.
 */
public class DemoActivity3 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo3);
        final CustomTitleBar bar = (CustomTitleBar) findViewById(R.id.title_bar);
        bar.setTitleTextView((TextView) bar.findViewById(R.id.bar_title));
        bar.setTitle("DemoActivity3");
        bar.setTitleBarFitsSystemWindows(true);
    }
}