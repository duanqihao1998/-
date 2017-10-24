package com.bwie.zss.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bwie.zss.R;
import com.bwie.zss.fragment.HomeFragment;
import com.bwie.zss.fragment.RecommendFragment;
import com.bwie.zss.fragment.UserFragment;
import com.bwie.zss.utils.SharedUtil;
import com.hjm.bottomtabbar.BottomTabBar;

public class MainActivity extends AppCompatActivity {

    private BottomTabBar bottomTabar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        initId();
        initData();
    }

    private void initData() {
        bottomTabar.init(getSupportFragmentManager())
                .setImgSize(70, 70)
                .setFontSize(14)
                .setTabPadding(4, 6, 10)
                .setChangeColor(Color.parseColor("#46B99A"),Color.parseColor("#979797"))
                .addTabItem("首页",R.mipmap.index, HomeFragment.class)
                .addTabItem("推荐",R.mipmap.tj, RecommendFragment.class)
                .addTabItem("我的",R.mipmap.me, UserFragment.class)
                //添加选项卡切换监听
                .setOnTabChangeListener(new BottomTabBar.OnTabChangeListener() {
                    @Override public void onTabChange(int position, String name) {
                        //这里不用说，你们也都看的懂了 //暂时就返回了这俩参数，如果还有什么用的比较多的参数，欢迎留言告诉我，我继续添加上
                        Log.i("TGA", "位置：" + position + " 选项卡的文字内容：" + name);
                        SharedUtil instances = SharedUtil.getInstances();
                        boolean config = (boolean) instances.getValueByKey(MainActivity.this, "config", false);
                        switch (position){
                           case 2:
                               if (!config){
                                   startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), 0);
                               }
                               String mobile = (String) instances.getValueByKey(MainActivity.this, "mobile", "");

                               Log.i("xxx",mobile);


                               break;
                       }

                    } });
    }


    private void initId() {
        bottomTabar = (BottomTabBar) findViewById(R.id.bottomtabar);
    }
}
