package com.bwie.zss.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.zss.R;
import com.bwie.zss.activity.LoginActivity;
import com.bwie.zss.javabean.IndexBean;
import com.bwie.zss.javabean.TabBean;
import com.bwie.zss.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  14：23
 */

public class HomeFragment extends Fragment implements View.OnClickListener{

    private View view;
    private ViewGroup group;
    private ImageView left;
    private ImageView right;
    private Boolean info;
    private TabLayout tablayout;
    private Gson gson=new Gson();
    private List<TabBean> tabBeen=new ArrayList<>();
    private ViewPager pager;
    private Handler handler=new Handler(){

        private MAdapter mAdapter;
        private List<IndexBean.DataBean.CateBean> cate;
        private IndexBean indexBean;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String json= (String) msg.obj;
                    indexBean = gson.fromJson(json, IndexBean.class);

                    cate = indexBean.getData().getCate();
                    /**
                     * 循环将title与url放入tabBeen集合
                     */
                    for(int i=0;i<cate.size();i++){
                        tabBeen.add(new TabBean(cate.get(i).getSort_name(),"http://lexue365.51dangao.cn/api/activity/lists?recommend=0&offset=0&limit=10&city_id=2&sort_id="+i));

                    }
                    for (int i=0;i<tabBeen.size();i++){
                        tablayout.addTab(tablayout.newTab().setText(tabBeen.get(i).getTitle()));
                    }
                    if(mAdapter==null){
                        mAdapter = new MAdapter(getChildFragmentManager());
                        pager.setAdapter(mAdapter);
                        tablayout.setupWithViewPager(pager);
                        tablayout.setTabsFromPagerAdapter(mAdapter);
                    }else{
                        mAdapter.notifyDataSetChanged();
                    }

                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (view==null) {
           view = inflater.inflate(R.layout.home_fragment, container, false);
       }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group!=null){
            group.removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getid();
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        getdata();

    }
    //获取id
    public void getid() {
        left = (ImageView) view.findViewById(R.id.leftImage);
        right = (ImageView) view.findViewById(R.id.rightImage);
        pager = (ViewPager) view.findViewById(R.id.a_viewpager);
        tablayout = (TabLayout) view.findViewById(R.id.a_tab);
        //滑动模式
        tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }
    public class MAdapter extends FragmentPagerAdapter {

        public MAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabBeen.get(position).getTitle();
        }

        @Override
        public Fragment getItem(int position) {
            //url传入
            return Fragment_index.newInstince(tabBeen.get(position).getUrl());
        }

        @Override
        public int getCount() {
            return tabBeen.size();
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.leftImage:
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rightImage:
                info = (Boolean) SharedUtil.getInstances().getValueByKey(getActivity(), "info", false);
                if(info){
                    Intent it=new Intent(getActivity(), UserFragment.class);
                    startActivity(it);
                    //   登录成功跳转我的界面
                }
                else{
                    Intent it=new Intent(getActivity(), LoginActivity.class);
                    startActivity(it);
                }

                break;
        }
    }

    public void getdata() {
        String url="http://lexue365.51dangao.cn/api/index";
       OkHttp3Utils.doGet(url, new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
           }
           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String json= response.body().string();
               Message message=new Message();
               message.what=0;
               message.obj=json;
               handler.sendMessage(message);
           }
       });
    }

    /**
     * 页面销毁前置空handler
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
