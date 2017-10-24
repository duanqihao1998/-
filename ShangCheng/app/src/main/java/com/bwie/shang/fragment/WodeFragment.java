package com.bwie.shang.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shang.R;
import com.bwie.shang.activity.LoginAndRegisterActivity;
import com.bwie.shang.util.SharedUtil;
import com.bwie.shang.zidingyikongjian.CustomButton;
import com.bwie.xlistviewutils.XListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Love_you on 2017/8/31 0031.
 */
public class WodeFragment extends Fragment {

    private List<String> list = new ArrayList<>();
    private XListView xlv;
    private Handler handler = new Handler();
    private View view;
    private ListviewAdapter adapter;
    private TextView usernametext;
    private boolean config;
    private SharedUtil instances;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView = new TextView(getActivity());
//        textView.setText("我的主界面");
        view = inflater.inflate(R.layout.wodelistview, null);
        xlv = (XListView) view.findViewById(R.id.xlv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        instances = SharedUtil.getInstances();
        config = (boolean) instances.getValueByKey(getActivity(), "config", false);


        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopRefresh();
                        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }

            //上拉加载
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        xlv.stopLoadMore();
                        Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
            }
        });
        adapter = new ListviewAdapter();
        xlv.setAdapter(adapter);
    }

    class ListviewAdapter extends BaseAdapter implements View.OnClickListener {
        @Override
        public int getCount() {
            return 1;
        }

        @Override
        public Object getItem(int position) {
            return View.inflate(getActivity(), R.layout.wodetaobao, null);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hold hold = null;
            if (convertView == null) {
                //加载布局
                convertView = View.inflate(getActivity(), R.layout.wodetaobao, null);
                CustomButton Custom1 = (CustomButton) convertView.findViewById(R.id.Custom1);
                CustomButton Custom2 = (CustomButton) convertView.findViewById(R.id.Custom2);
                CustomButton Custom3 = (CustomButton) convertView.findViewById(R.id.Custom3);
                Custom1.setTextCount(0 + "");
                Custom1.setTextName("收藏夹");
                Custom2.setTextCount(0 + "");
                Custom2.setTextName("关注店铺");
                Custom3.setTextCount(0 + "");
                Custom3.setTextName("足迹");

                //获取页面的控件
                ImageView imageView = (ImageView) convertView.findViewById(R.id.userImage);
                usernametext = (TextView) convertView.findViewById(R.id.username);
                if(config){
                    String username1 = (String) instances.getValueByKey(getActivity(), "username", "");
                    usernametext.setText(username1);
                }
                imageView.setOnClickListener(this);
                usernametext.setOnClickListener(this);
            }
            return convertView;
        }

        @Override
        public void onClick(View v) {
            Intent intent=new Intent(getActivity(), LoginAndRegisterActivity.class);
            startActivity(intent);
        }
        class Hold {

        }
    }
}
