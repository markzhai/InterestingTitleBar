package cn.zhaiyifan.interestingtitlebar.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import android.widget.TextView;

import cn.zhaiyifan.interestingtitlebar.CustomTitleBar;
import cn.zhaiyifan.interestingtitlebar.utils.ViewUtils;

public class DemoActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.init(this);
        setContentView(R.layout.demo1);
        final CustomTitleBar bar = (CustomTitleBar) findViewById(R.id.title_bar);
        bar.setTitleTextView((TextView)bar.findViewById(R.id.bar_title));
        bar.setTransparentEnabled(true, 100, 600);
        bar.setTitle("DemoActivity");
        bar.setTextShadowColor(getResources().getColor(R.color.bar_title_text_shadow));
        bar.addViewToFadeList(findViewById(R.id.bar_left_button));
        bar.addViewToFadeList(findViewById(R.id.bar_right_button));
        bar.addViewToFadeList(findViewById(R.id.bar_title));

        final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                bar.onScroll(scrollView.getScrollY());
            }
        });
        scrollView.findViewById(R.id.c1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, DemoActivity2.class));
            }
        });
        scrollView.findViewById(R.id.c2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DemoActivity.this, DemoActivity3.class));
            }
        });
    }
}