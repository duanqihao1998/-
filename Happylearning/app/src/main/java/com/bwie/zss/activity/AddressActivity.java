package com.bwie.zss.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.bwie.zss.R;

public class AddressActivity extends AppCompatActivity {


    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_address);


        mapView = (MapView) findViewById(R.id.mapview);
        //找到地图控件
        mapView.onCreate(savedInstanceState);
        // 此方法必须重写
        AMap aMap = mapView.getMap();
        //得到一个map对象
//
//        //116.268700  40.0124
        LatLng latLng = new LatLng(Double.parseDouble(getIntent().getStringExtra("weidu")),Double.parseDouble(getIntent().getStringExtra("jindu")));
        Marker marker = aMap.addMarker(new MarkerOptions().position(latLng).title("北京").snippet("在这里训练哦"));


    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
