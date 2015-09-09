package cn.zhaiyifan.interestingtitlebar.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import cn.zhaiyifan.interestingtitlebar.CustomTitleBar;

/**
 * Created by markzhai on 2015/1/14.
 */
public class DemoActivity2 extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo2);
        final CustomTitleBar bar = (CustomTitleBar) findViewById(R.id.title_bar);
        bar.setTitleTextView((TextView)bar.findViewById(R.id.bar_title));
        bar.setTransparentEnabled(true, 50, 500);
        bar.setTitle("DemoActivity2");
        bar.addViewToFadeList(findViewById(R.id.bar_left_button));
        bar.addViewToFadeList(findViewById(R.id.bar_right_button));
        bar.addViewToFadeList(findViewById(R.id.bar_title));
        ListView listView = (ListView)findViewById(R.id.list_view);
        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return 20;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (position == 0) {
                    ImageView imageView = new ImageView(DemoActivity2.this);
                    imageView.setImageResource(R.drawable.pic3);
                    imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    return imageView;
                }
                TextView textView = new TextView(DemoActivity2.this);
                textView.setMinimumHeight(100);
                textView.setTextSize(18);
                textView.setText("row: " + position);
                return textView;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                bar.onScroll(view, firstVisibleItem, 0);
            }
        });
    }
}