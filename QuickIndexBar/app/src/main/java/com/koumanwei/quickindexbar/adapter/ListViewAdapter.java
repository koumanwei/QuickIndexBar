package com.koumanwei.quickindexbar.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.koumanwei.quickindexbar.R;
import com.koumanwei.quickindexbar.bean.City;

import java.util.List;

/**
 * Created by koumanwei on 2016-06-15.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private List<City> cityList;

    public ListViewAdapter(Context context, List<City> cityList) {

        this.context = context;
        this.cityList = cityList;
    }

    @Override
    public int getCount() {
        return cityList.size();
    }

    @Override
    public Object getItem(int position) {
        return cityList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = View.inflate(context, R.layout.list_view_item, null);
        }
        ViewHolder mViewHolder = ViewHolder.getHolder(view);
        City city = cityList.get(position);
        String currentLetter = city.getPinyin().charAt(0) + "";
        //根据上一个首字母，决定当前字母是否显示
        String str = null;
        if (position == 0) {
            str = currentLetter;
        } else {
            //上一个人的拼音的首字母
            String preLetter = cityList.get(position - 1).getPinyin().charAt(0) + "";
            if (!TextUtils.equals(preLetter, currentLetter)) {
                str = currentLetter;
            }
        }
        //根据str是否为空，决定是否显示索引栏
        mViewHolder.mIndex.setVisibility(str == null ? View.GONE : View.VISIBLE);
        mViewHolder.mIndex.setText(currentLetter);
        mViewHolder.mName.setText(city.getCityName());
        return view;
    }

    static class ViewHolder {
        TextView mIndex;
        TextView mName;

        public static ViewHolder getHolder(View view) {
            Object tag = view.getTag();
            if (tag != null) {
                return (ViewHolder) tag;
            } else {
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.mIndex = (TextView) view.findViewById(R.id.tv_index);
                viewHolder.mName = (TextView) view.findViewById(R.id.tv_name);
                view.setTag(viewHolder);
                return viewHolder;
            }
        }
    }
}
