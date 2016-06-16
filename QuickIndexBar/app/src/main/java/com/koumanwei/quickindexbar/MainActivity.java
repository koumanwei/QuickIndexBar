package com.koumanwei.quickindexbar;

import android.os.Handler;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.koumanwei.quickindexbar.adapter.ListViewAdapter;
import com.koumanwei.quickindexbar.bean.City;
import com.koumanwei.quickindexbar.util.CityListUtils;
import com.koumanwei.quickindexbar.util.ToastUtils;
import com.koumanwei.quickindexbar.view.QuickIndexBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<City> cityList;
    private TextView tvCenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);
        tvCenter = (TextView) findViewById(R.id.tv_center);
        QuickIndexBar bar = (QuickIndexBar) findViewById(R.id.bar);
        bar.setListener(new QuickIndexBar.OnLetterUpdateListener() {
            @Override
            public void onLetterUpdate(String letter) {
                showLetter(letter);
                //根据字母定位ListView，找到集合中第一个以letter为拼音首字母的对象，得到索引
                for (int i = 0; i < cityList.size(); i++) {
                    City city = cityList.get(i);
                    String l = city.getPinyin().charAt(0) + "";
                    if (TextUtils.equals(letter, l)) {
                        //匹配成功
                        listView.setSelection(i);
                        break;
                    }
                }
            }
        });
        //存储人名和拼音的集合
        cityList = new ArrayList<>();
        //填充并且排序
        fillAndSortData(cityList);
        listView.setAdapter(new ListViewAdapter(MainActivity.this, cityList));
    }

    private Handler mHandle = new Handler();

    /**
     * 显示字母
     *
     * @param letter
     */
    private void showLetter(String letter) {
        tvCenter.setVisibility(View.VISIBLE);
        tvCenter.setText(letter);
        mHandle.removeCallbacksAndMessages(null);
        mHandle.postDelayed(new Runnable() {
            @Override
            public void run() {
                tvCenter.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void fillAndSortData(List<City> cityList) {
        //填充数据
        for (int i = 0; i < CityListUtils.CITY_NAMES.length; i++) {
            String name = CityListUtils.CITY_NAMES[i];
            cityList.add(new City(name));
        }
        //进行排序
        Collections.sort(cityList);
    }
}
