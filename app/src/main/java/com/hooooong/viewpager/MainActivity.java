package com.hooooong.viewpager;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setViewPager();
    }

    private void initView(){
        viewPager = (ViewPager)findViewById(R.id.viewPager);
    }

    private void setViewPager(){
        CustomAdapter customAdapter = new CustomAdapter(this);
        customAdapter.setData(setData());
        viewPager.setAdapter(customAdapter);
        customAdapter.notifyDataSetChanged();
    }

    private List<String> setData(){
        List<String> data = new ArrayList<>();

        for(int i = 0 ; i<100; i++){
            data.add("페이지 : " + i);
        }

        return  data;
    }

}
