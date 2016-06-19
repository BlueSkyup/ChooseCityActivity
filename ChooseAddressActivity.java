package com.yangsheng.ydzd_lb.androidpnpro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用时在initData方法中 初始化数据  也就是要显示的热门城市和 所有城市
 * 然后在 前一个Activity中 获取得到的数据即可   onActivityResult中
 */
public class ChooseAddressActivity extends AppCompatActivity {

    private static final int CHOOSE_CITY_RESULT = 78;
    private static final String CHOOSE_CITY_FLAG = "choose_city";
    private EditText edit_province;
    private ListView lv_pull_city;
    private MyScrollViewListView lv_all_city;
    private MyScrollView scroll_all;
    private MyGridView gv_hop_city;
    private String[] hot_cities;//
    private List<String> pull_lists;  //自动提示的省份列表
    private String[] all_cities;
    private BaseAdapter lv_pull_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_drawer_city_choose);
        initData();

        initView();

    }

    /**
     * 在里面设置一些初始化显示的数据
     */
    private void initData() {
        hot_cities = getResources().getStringArray(R.array.array_city);
        pull_lists = new ArrayList<>();
        all_cities = getResources().getStringArray(R.array.province_item);
    }

    /**
     * 初始化View布局
     */
    private void initView() {
        edit_province = (EditText) findViewById(R.id.edit_province);
        lv_pull_city = (ListView) findViewById(R.id.lv_pull_city);
        lv_pull_city.setVisibility(View.GONE);
        scroll_all = (MyScrollView) findViewById(R.id.scroll_all);
        scroll_all.setVisibility(View.VISIBLE);
        gv_hop_city = (MyGridView) findViewById(R.id.gv_hop_city);
        lv_all_city = (MyScrollViewListView) findViewById(R.id.lv_all_city);

        gv_hop_city.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return hot_cities.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View view1 = null;
                ViewHolderGV holderGV = null;
                if (convertView != null) {
                    view1 = convertView;
                    holderGV = (ViewHolderGV) view1.getTag();
                } else {
                    view1 = View.inflate(ChooseAddressActivity.this, R.layout.gv_city_item, null);
                    holderGV = new ViewHolderGV();
                    holderGV.txt_hot_city = (TextView) view1.findViewById(R.id.txt_hot_city);
                }
                holderGV.txt_hot_city.setText(hot_cities[position]);
                holderGV.txt_hot_city.setOnClickListener(new MyGvListtener(hot_cities[position]));
                view1.setTag(holderGV);
                return view1;
            }

            class ViewHolderGV {
                TextView txt_hot_city;
            }
        });

        lv_all_city.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return all_cities.length;
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = null;
                ViewholderLvAll viewholderLvAll = null;
                if (convertView != null) {
                    view1 = convertView;
                    viewholderLvAll = (ViewholderLvAll) view1.getTag();
                } else {
                    view1 = View.inflate(ChooseAddressActivity.this, R.layout.lv_city_item, null);
                    viewholderLvAll = new ViewholderLvAll();
                    viewholderLvAll.txt_hot_city = (TextView) view1.findViewById(R.id.txt_hot_city);
                }
                viewholderLvAll.txt_hot_city.setText(all_cities[position]);
                viewholderLvAll.txt_hot_city.setOnClickListener(new MyGvListtener(all_cities[position]));
                view1.setTag(viewholderLvAll);
                return view1;
            }

            class ViewholderLvAll {
                TextView txt_hot_city;
            }
        });

        lv_pull_city.setAdapter(lv_pull_adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return pull_lists.size();
            }

            @Override
            public Object getItem(int position) {
                return position;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view1 = null;
                ViewholderLvPull viewholderLvPull = null;
                if (convertView != null) {
                    view1 = convertView;
                    viewholderLvPull = (ViewholderLvPull) view1.getTag();
                } else {
                    view1 = View.inflate(ChooseAddressActivity.this, R.layout.lv_city_item, null);
                    viewholderLvPull = new ViewholderLvPull();
                    viewholderLvPull.txt_hot_city = (TextView) view1.findViewById(R.id.txt_hot_city);
                }
                viewholderLvPull.txt_hot_city.setText(pull_lists.get(position));
                viewholderLvPull.txt_hot_city.setOnClickListener(new MyGvListtener(pull_lists.get(position), 1));
                view1.setTag(viewholderLvPull);
                return view1;
            }

            class ViewholderLvPull {
                TextView txt_hot_city;
            }
        });
        edit_province.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                lv_pull_city.setVisibility(View.VISIBLE);

                changePullList(s, pull_lists);
                lv_pull_adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if ("".equals(s.toString())) {
                    lv_pull_city.setVisibility(View.GONE);

                }
            }
        });
    }


    private void changePullList(CharSequence string, List<String> pull_lists) {
        pull_lists.clear();
        for (String city : all_cities) {
            System.out.println("----" + city + "  " + string);
            if (city.contains(string)) {
                System.out.println("----contains  " + city + "  " + string);
                pull_lists.add(city);
            }
        }
    }

    private class MyGvListtener implements View.OnClickListener {

        private String city;
        private int flag;

        public MyGvListtener(String city) {
            this.city = city;
            this.flag = -1;
        }

        public MyGvListtener(String city, int flag) {
            this.city = city;
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            Intent reslut = new Intent();
            reslut.putExtra(CHOOSE_CITY_FLAG, city);
            setResult(CHOOSE_CITY_RESULT);
        }
    }
}
