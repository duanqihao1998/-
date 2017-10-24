package com.bwie.shang.fragment;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shang.R;
import com.bwie.shang.activity.SousuoActivity;
import com.bwie.shang.bean.JsonBean;
import com.bwie.shang.bean.JsonBean2;
import com.bwie.shang.zidingyikongjian.CustomButton1;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.youth.banner.Banner;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import imageloaderapp.bwie.com.mylibrary.ImageLoaderUtils;

/**
 * Created by Love_you on 2017/8/31 0031.
 */
public class TaoFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private List<RadioButton> radioButtonList = new ArrayList<>();
    private RadioButton saoyisao;
    private RadioButton sousuo;
    private GridView gridView;
    private Banner banner1;
    private List<String> imageLists = new ArrayList<>();//添加图片集合
    private GridView getGridView;
    private List<JsonBean.DataBean.ItemListBean> itemList;
    private int[] imageArray=new int[]{R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e,R.mipmap.f,R.mipmap.g,R.mipmap.h,R.mipmap.i,R.mipmap.j};
    private String[] textArray=new String[]{"天猫","聚划算","天猫国际","外卖","天猫超市","充值中心","飞猪旅行","领金币","拍卖","分类"};
    private String path = "http://qiang.mogujie.com/jsonp/actGroupItem/1?callback=jQuery21104587953138117029_1504264031748&groupKey=11q&_=1504264031749";
    private String path1="http://m.yunifang.com/yunifang/mobile/home";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                addzhendonghua();
                String string = (String) msg.obj;
                Gson gson = new Gson();
                String haha = "jQuery21104587953138117029_1504264031748(";
                String sb = string.substring(haha.length(), string.length() - 1);
                Log.i("xxx", sb.toString());
                JsonBean jsonBean = gson.fromJson(sb, JsonBean.class);
                JsonBean.DataBean data = jsonBean.data;
                itemList = data.itemList;
                ViewGroup.LayoutParams l = getGridView.getLayoutParams();
                l.height=460*itemList.size()/2;
                getGridView.setLayoutParams(l);
                myListview = new MyListview();
                getGridView.setAdapter(myListview);
                getData(path1,1);
            }
            if(msg.what==1){
                String string = (String) msg.obj;
                Log.i("xxx", string.toString());
                Gson gson = new Gson();
                JsonBean2 jsonBean2 = gson.fromJson(string, JsonBean2.class);
                JsonBean2.DataBean2 data = jsonBean2.data;
                ad1 = data.ad1;
                imageLists.clear();
                for (int i = 0; i < ad1.size(); i++) {
                    imageLists.add(ad1.get(i).image);
                }
            }
        }
    };
    private List<JsonBean2.DataBean2.AD1> ad1;//轮播图的集合
    private EditText editText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private MyListview myListview;
    private ImageView iv;
    private AnimationDrawable animationDrawable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye, null);
        //获取布局控件
        saoyisao = (RadioButton) view.findViewById(R.id.saoyisao);
        sousuo = (RadioButton) view.findViewById(R.id.sousuo);
        gridView = (GridView) view.findViewById(R.id.groupView);
        banner1 = (Banner) view.findViewById(R.id.banner1);
        editText = (EditText) view.findViewById(R.id.editText);
        iv = (ImageView) view.findViewById(R.id.jiazai);

        getGridView = (GridView) view.findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srf);
//        TextView textView = new TextView(getActivity());
//        textView.setText("我的淘宝首页");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //解析json串获得数据

        getData(path,0);

        radioButtonList.add(saoyisao);
        radioButtonList.add(sousuo);
        for (int i = 0; i < radioButtonList.size(); i++) {
            RadioButton radioButton = radioButtonList.get(i);
            radioButton.setOnClickListener(this);
        }
        swipeRefreshLayout.setOnRefreshListener(this);

        //输入框添加点击事件
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
            }
        });

        banner1.setIndicatorGravity(Banner.CENTER);
        banner1.setBannerStyle(Banner.CIRCLE_INDICATOR_TITLE);
        banner1.isAutoPlay(true);
        banner1.setDelayTime(2000);
        banner1.setImages(imageLists);
        gridView.setAdapter(new MyGridview());
    }

    //按钮添加点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.saoyisao:
                Toast.makeText(getActivity(), "扫描二维码", Toast.LENGTH_SHORT).show();
                break;
            case R.id.sousuo:
                Intent intent=new Intent(getActivity(), SousuoActivity.class);
                startActivity(intent);
                break;
        }
    }

    //网络请求数据首页展示数据
    private void getData(final String urlpath, final int whatnum) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlpath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        //输出流
                        ByteArrayOutputStream bos=new ByteArrayOutputStream();
                        byte[] buffer=new byte[1024];
                        int len=0;
                        while ((len=inputStream.read(buffer))!=-1){
                            bos.write(buffer,0,len);
                        }
                        String string = bos.toString("UTF-8");
                        Message message=new Message();
                        message.obj=string;
                        message.what=whatnum;
                        handler.sendMessage(message);
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    //数据加载时添加帧动画
    public void addzhendonghua(){
        if(itemList!=null){
            //将动画隐藏
            iv.setVisibility(View.GONE);
        }else {
            iv.setImageResource(R.drawable.animation);
            animationDrawable = (AnimationDrawable) iv.getDrawable();
            animationDrawable.start();
        }

    }

    //刷新
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(itemList==null){
                    Toast.makeText(getActivity(), "刷新失败", Toast.LENGTH_SHORT).show();
                }else {
                    itemList.clear();
                    getData(path,0);
                    myListview.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        },2000);
    }

    //给GridView添加适配器
    class MyGridview extends BaseAdapter {

        @Override
        public int getCount() {
            return imageArray.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hold hold;
            if (convertView == null) {
                hold = new Hold();
                convertView = View.inflate(getActivity(), R.layout.girdviewlistview, null);
                hold.customButton1 = (CustomButton1) convertView.findViewById(R.id.CustomButton1);
                convertView.setTag(hold);
            } else {
                hold = (Hold) convertView.getTag();
            }
//            hold.customButton1.setImageResource(imageArray[position]);
            hold.customButton1.setImageView(imageArray[position]);
            hold.customButton1.setTextView(textArray[position]);
            return convertView;
        }
        class Hold {
//            ImageView imageView1;
            CustomButton1 customButton1;
        }
    }

    //给Listview设置适配器
    class MyListview extends BaseAdapter {
        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int position) {
            return itemList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ListHold listHold;
            if (convertView == null) {
                listHold = new ListHold();
                convertView = View.inflate(getActivity(), R.layout.shouyelistview, null);
                listHold.imageView = (ImageView) convertView.findViewById(R.id.imagehead);
                listHold.texttitle = (TextView) convertView.findViewById(R.id.texttitle);
                listHold.textprice = (TextView) convertView.findViewById(R.id.textprice);
                listHold.imagegou= (ImageView) convertView.findViewById(R.id.mai);
                //给按钮添加点击事件
                listHold.imagegou.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), "添加到购物车", Toast.LENGTH_SHORT).show();

                    }
                });
                convertView.setTag(listHold);
            } else {
                listHold = (ListHold) convertView.getTag();
            }
            DisplayImageOptions options = ImageLoaderUtils.getOptions();
            ImageLoader.getInstance().displayImage(itemList.get(position).itemImage, listHold.imageView, options);
           /* ViewGroup.LayoutParams params=listHold.imageView.getLayoutParams();
            params.height=400;
            params.width=300;
            listHold.imageView.setLayoutParams(params);*/
            String str="<font color='#ff0000'>¥</font>"+itemList.get(position).price;

            listHold.imagegou.setImageResource(R.mipmap.ic_launcher);
            listHold.texttitle.setText(itemList.get(position).title);
            listHold.textprice.setText(Html.fromHtml(str));
            return convertView;
        }

        class ListHold {
            ImageView imageView;
            TextView texttitle;
            TextView textprice;
            ImageView imagegou;
        }
    }
}
