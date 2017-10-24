package test.bwie.com.jingdong.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.V.MyView;
import test.bwie.com.jingdong.fragment.Fenlei_Fragment;
import test.bwie.com.jingdong.fragment.Index_Fragment;
import test.bwie.com.jingdong.fragment.Mine_Fragment;
import test.bwie.com.jingdong.fragment.Shopping_Fragment;

public class MyActivity extends AppCompatActivity implements View.OnClickListener,MyView {

    private FrameLayout fragment;
    private RadioButton index;
    private RadioButton shopping;
    private RadioButton user;
    private RadioButton fenlei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置无标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my);
        //初始化控件
        isitView();
        //在一开始直接创建一个fragment
        getFragment(new Index_Fragment(), "");
    }

    //初始化控件
    private void isitView() {
        fragment = (FrameLayout) findViewById(R.id.fragment);
        index = (RadioButton) findViewById(R.id.index);
        fenlei = (RadioButton) findViewById(R.id.fenlei);
        shopping = (RadioButton) findViewById(R.id.shopping);
        user = (RadioButton) findViewById(R.id.user);
        index.setOnClickListener(this);
        fenlei.setOnClickListener(this);
        shopping.setOnClickListener(this);
        user.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.index:
                getFragment(new Index_Fragment(), "");
                break;
            case R.id.fenlei:
                getFragment(new Fenlei_Fragment(), "");
                break;
            case R.id.shopping:
                getFragment(new Shopping_Fragment(), "");
                break;
            case R.id.user:
                getFragment(new Mine_Fragment(), "");
                break;
        }
    }

    //替换布局
    private void getFragment(Fragment fragment, String tag) {
        //获得管理器
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        //替换
        beginTransaction.replace(R.id.fragment, fragment, tag);
        //提价
        beginTransaction.commit();
    }

    @Override
    public void getDataJSON(String json) {

    }
}
