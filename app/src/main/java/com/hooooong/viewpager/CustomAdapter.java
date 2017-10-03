package com.hooooong.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Android Hong on 2017-10-03.
 */

class CustomAdapter extends PagerAdapter{
    private Context context;
    private List<String> data;

    public CustomAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Log.e("CustomAdapter", "instantiateItem 호출");
        View view = LayoutInflater.from(context).inflate(R.layout.item_viewpager, null);
        TextView textView = view.findViewById(R.id.textView);
        textView.setText(data.get(position));

        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        Log.e("CustomAdapter", "destroyItem 호출");
        container.removeView((View)object);
    }

    public void setData(List<String> data){
        this.data = data;
    }
}
