package com.bwie.text.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwei.imageloaderlibrary.utils.ImageLoaderUtils;
import com.bwie.text.R;
import com.bwie.text.activity.ShowActivity;
import com.bwie.text.bean.UrlContent;
import com.bwie.xlistviewutils.XListView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Love_you on 2017/8/16 0016.
 */
public class Fragment_vp extends Fragment {

    //设置Listview要加载的布局类型
    private int TYPE01 = 0;
    private int TYPE02 = 1;
    private int TYPE03 = 2;
    private int TYPE04 = 3;
    private View v;
    private String json;
    private XListView xlv;
    private List<UrlContent> list = new ArrayList<>();
    private Handler handler = new Handler();
    private SharedPreferences getTime;
    private SharedPreferences.Editor edit;
    private ListViewAdapter ladapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (v == null) {
            v = inflater.inflate(R.layout.fragment_vp, container, false);
            xlv = (XListView) v.findViewById(R.id.xlv);
        }
        ViewGroup group = (ViewGroup) v.getParent();
        if (group != null) {
            group.removeView(v);
        }
        getTime = getActivity().getSharedPreferences("getTime", Context.MODE_PRIVATE);
        edit = getTime.edit();
        if (getArguments() != null) {
            json = (String) getArguments().get("flag");
            //解析字符串
            syJson(json);
        }
        return v;
    }
    //刷新
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        xlv.setPullRefreshEnable(true);
        xlv.setPullLoadEnable(true);
        syJson(json);

        //设置刷新 加载监听
        xlv.setXListViewListener(new XListView.IXListViewListener() {
            //下拉刷新
            @Override
            public void onRefresh() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        syJson(json);
                        xlv.setRefreshTime(getTime.getString("mytime", ""));
                        xlv.stopRefresh();
                        //获取本地的时间
                        Date d = new Date();
                        //格式化时间
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        String date = sdf.format(d);
                        edit.clear();
                        edit.putString("mytime", date);
                        edit.commit();
                    }
                }, 2000);
            }

            //上拉加载
            @Override
            public void onLoadMore() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        syJson(json);
                        xlv.stopLoadMore();
                    }
                }, 2000);
            }
        });
        //条目设置监听
        xlv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(), ShowActivity.class);
                intent.putExtra("url",list.get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    //解析Json串
    private void syJson(String json) {
        //异步请求
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(getActivity(), json, new TextHttpResponseHandler() {

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject obj = new JSONObject(responseString);
                    JSONArray data = obj.optJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject d = data.optJSONObject(i);
                        UrlContent u = new UrlContent();
                        u.setTitle(d.optString("title"));
                        u.setUrl(d.optString("url"));
                        JSONArray imglist = d.optJSONArray("image_list");
                        List<String> imagelist = new ArrayList<String>();
                        if (imglist!=null) {
                            for (int j = 0; j < imglist.length(); j++) {
                                imagelist.add(imglist.optJSONObject(j).optString("url"));
                            }
                        }
                        u.setDisplay_url(d.optString("display_url"));
                        u.setImagelist(imagelist);
                        u.setHas_image(d.optBoolean("has_image"));
                        u.setHas_video(d.optBoolean("has_video"));
                        u.setSource(d.optString("source"));
                        JSONArray videou = d.optJSONArray("large_image_list");
                        if (videou != null && videou.length() > 0) {
                            u.setVideourl(videou.optJSONObject(0).optString("url"));
                        }
                        JSONArray wordArray = d.optJSONArray("filter_words");
                        u.setFilter_words(wordArray.length());
                        list.add(u);
                    }

                    if(ladapter ==null){
                        ladapter = new ListViewAdapter();
                        xlv.setAdapter(ladapter);
                    }else {
                        //刷新适配器
                        ladapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //listview的适配器适配数据
    class ListViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getViewTypeCount() {
            return 4;
        }

        @Override
        public int getItemViewType(int position) {
            //判断没有图片也没有视频只展示文本
            if (list.get(position).isHas_image() == false && list.get(position).isHas_video() == false) {
                return TYPE01;
            }
            //集合只有视频
            else if (list.get(position).isHas_video()) {
                return TYPE04;
            }
            //集合只有图片并且有三张
            else if (list.get(position).isHas_image() && list.get(position).getImagelist().size() == 3) {
                return TYPE03;
            }
            //集合有图片并且只有只有一张
            else if (list.get(position).isHas_image() && list.get(position).getImagelist().size() == 1) {
                return TYPE02;
            }
            //默认返回第一种类型
                else {
                    return TYPE01;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hold holder;
            int type = getItemViewType(position);
            if (convertView == null) {
                holder = new Hold();
                switch (type) {
                    case 0:
                        convertView=View.inflate(getActivity(),R.layout.item01,null);
                        holder.text_item=(TextView) convertView.findViewById(R.id.text_item);
                        holder.count_item=(TextView) convertView.findViewById(R.id.count_item);
                        break;
                    case 1:
                        convertView=View.inflate(getActivity(),R.layout.item02,null);
                        holder.text_item=(TextView) convertView.findViewById(R.id.text_item);
                        holder.count_item=(TextView) convertView.findViewById(R.id.count_item);
                        holder.image_item01=(ImageView) convertView.findViewById(R.id.image_item01);
                        break;
                    case 2:
                        convertView=View.inflate(getActivity(),R.layout.item03,null);
                        holder.text_item=(TextView) convertView.findViewById(R.id.text_item);
                        holder.count_item=(TextView) convertView.findViewById(R.id.count_item);
                        holder.image_item01=(ImageView) convertView.findViewById(R.id.image_item01);
                        holder.image_item02=(ImageView) convertView.findViewById(R.id.image_item02);
                        holder.image_item03=(ImageView) convertView.findViewById(R.id.image_item03);
                        break;
                    case 3:
                        convertView=View.inflate(getActivity(),R.layout.item04,null);
                        holder.vv=(WebView) convertView.findViewById(R.id.vv);
                        holder.text_item=(TextView) convertView.findViewById(R.id.text_item);
                        break;
                }
                convertView.setTag(holder);
            } else {
                holder = (Hold) convertView.getTag();
            }
            DisplayImageOptions options = ImageLoaderUtils.getOptions();
            switch (type) {
                case 0:
                    holder.text_item.setText(list.get(position).getTitle());
                    holder.count_item.setText(list.get(position).getSource()+"   评论"+list.get(position).getFilter_words());
                    break;
                case 1:
                    holder.text_item.setText(list.get(position).getTitle());
                    holder.count_item.setText(list.get(position).getSource()+"   评论"+list.get(position).getFilter_words());
                    ImageLoader.getInstance().displayImage(list.get(position).getImagelist().get(0),holder.image_item01,options);
                    break;
                case 2:
                    holder.text_item.setText(list.get(position).getTitle());
                    holder.count_item.setText(list.get(position).getSource()+"   评论"+list.get(position).getFilter_words());

                    ImageLoader.getInstance().displayImage(list.get(position).getImagelist().get(0),holder.image_item01,options);
                    ImageLoader.getInstance().displayImage(list.get(position).getImagelist().get(1),holder.image_item02,options);
                    ImageLoader.getInstance().displayImage(list.get(position).getImagelist().get(2),holder.image_item03,options);
                    break;
                case 3:
                    holder.text_item.setText(list.get(position).getTitle());
                    WebSettings settings=holder.vv.getSettings();
                    settings.setJavaScriptCanOpenWindowsAutomatically(true);
                    settings.setJavaScriptEnabled(true);
                    holder.vv.setWebViewClient(new WebViewClient(){});
                    holder.vv.loadUrl(list.get(position).getDisplay_url());
                    break;
            }
            return convertView;
        }

        class Hold {
            TextView text_item;
            TextView count_item;
            ImageView image_item01;
            ImageView image_item02;
            ImageView image_item03;
            WebView vv;
        }
    }
    //创建Fragment
    public static Fragment_vp newInstance(String flag) {
        Fragment_vp fragment_vp = new Fragment_vp();
        Bundle bundle = new Bundle();
        bundle.putString("flag", flag);
        fragment_vp.setArguments(bundle);
        return fragment_vp;
    }
}
