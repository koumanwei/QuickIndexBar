package com.koumanwei.quickindexbar.bean;

import com.koumanwei.quickindexbar.util.PinyinUtils;

/**
 * Created by koumanwei on 2016-06-15.
 */

public class City implements Comparable<City> {
    private String cityName;
    private String pinyin;

    public City(String cityName) {
        this.cityName = cityName;
        this.pinyin = PinyinUtils.getPinyin(cityName);
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public int compareTo(City another) {
        return this.pinyin.compareTo(another.getPinyin());
    }
}
