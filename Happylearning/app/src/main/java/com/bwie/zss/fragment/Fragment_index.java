package com.bwie.zss.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bawei.swiperefreshlayoutlibrary.SwipyRefreshLayout;
import com.bwie.zss.R;
import com.bwie.zss.adapter.MRecyclerView;
import com.bwie.zss.javabean.Index_content_Data;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

/**
 * 1.类的用途
 * 2.@author 赵姗杉
 * 3.@date 2017/10/2 12:12
 */

public class Fragment_index extends Fragment {

    private View view;
    private ViewGroup group;
    private String url;
    private Index_content_Data indexBean;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
               String json= (String) msg.obj;
                Gson gson=new Gson();
                indexBean = gson.fromJson(json, Index_content_Data.class);
                adapter(indexBean.getData().getList());
            }
        }
    };
    private SwipyRefreshLayout sw;
    private RecyclerView recyclerView;
    private MRecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view ==null){
            view = View.inflate(getActivity(), R.layout.fragment_index,null);
        }
        group = (ViewGroup) view.getParent();
        if(group !=null){
            group.removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getid();
        url = (String) getArguments().get("url");
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                Log.e("TAG",json.toString());
                Message message=new Message();
                message.what=0;
                message.obj=json;
                handler.sendMessage(message);
            }
        });
    }

    public static Fragment_index newInstince(String url){
        Log.e("url",url);
        Fragment_index fragment_index=new Fragment_index();
        Bundle bundle=new Bundle();
        bundle.putString("url",url);
        fragment_index.setArguments(bundle);
        return fragment_index;
    }

    public void getid() {
        sw = (SwipyRefreshLayout) view.findViewById(R.id.index_swipyRefreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.index_recyclerView);
    }

    public void adapter(List<Index_content_Data.DataBean.ListBean> list){
        if(mRecyclerView==null){
            mRecyclerView = new MRecyclerView(getActivity(),list);
            LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(mRecyclerView);
        }else{
            mRecyclerView.notifyDataSetChanged();
        }
    }

}
