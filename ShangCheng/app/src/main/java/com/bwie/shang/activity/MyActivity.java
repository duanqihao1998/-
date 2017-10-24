package com.bwie.shang.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;

import com.bwie.shang.R;
import com.bwie.shang.fragment.GouFragment;
import com.bwie.shang.fragment.TaoFragment;
import com.bwie.shang.fragment.WeiFragment;
import com.bwie.shang.fragment.WodeFragment;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager viewjiemian;
    private RadioButton shou;
    private RadioButton wei;
    private RadioButton shopche;
    private RadioButton wode;
    private List<RadioButton> diImageList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //初始化控件
        isitView();
        //对viewpager设置数据适配器
        viewjiemian.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Fragment fragment=null;
                switch (position){
                        case 0:
                            fragment =new TaoFragment();
                            break;
                        case 1:
                            fragment =new WeiFragment();
                            break;
                        case 2:
                            fragment =new GouFragment();
                            break;
                        case 3:
                            fragment =new WodeFragment();
                            break;
                    }
                return fragment;
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
    }

    //初始化控件
    private void isitView() {
        viewjiemian = (ViewPager) findViewById(R.id.viewjiemian);
        shou = (RadioButton) findViewById(R.id.shouye);
        wei = (RadioButton) findViewById(R.id.weitao);
        shopche = (RadioButton) findViewById(R.id.gouwuche);
        wode = (RadioButton) findViewById(R.id.wode);

        //将控件的图片添加到集合当中
        diImageList.add(shou);
        diImageList.add(wei);
        diImageList.add(shopche);
        diImageList.add(wode);
        //设置点击事件
        for (int i = 0; i < diImageList.size(); i++) {
            diImageList.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shouye:
                viewjiemian.setCurrentItem(0);
                break;
            case R.id.weitao:
                viewjiemian.setCurrentItem(1);
                break;
            case R.id.gouwuche:
                viewjiemian.setCurrentItem(2);
                break;
            case R.id.wode:
                viewjiemian.setCurrentItem(3);
                break;
        }
    }
}
