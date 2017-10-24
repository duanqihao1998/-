package com.bwie.shang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.bwie.shang.R;
import com.bwie.shang.adapter.SuosuoRecycleViewAdapter;
import com.bwie.shang.bean.LaolishiBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class ShangPinShowActivity extends AppCompatActivity {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                   String json = (String) msg.obj;
//                    String s="/**/jsonp1(";
//                    String substring = json.substring(s.length(), json.length() - 2);
//                    Gson gson=new Gson();
//                    Log.i("substring",substring);
//                    JsonBeanSou jsonBeanSou = gson.fromJson(substring, JsonBeanSou.class);
//                    JsonBeanSou.JsonBeanSou1 result = jsonBeanSou.result;
//                    JsonBeanSou.JsonBeanSou1.JsonBeanSou2 wall = result.wall;
//                    docs = wall.docs;
                    Gson gson=new Gson();
                    LaolishiBean laolishiBean = gson.fromJson(json, LaolishiBean.class);
                    LaolishiBean.Laolishi datas = laolishiBean.datas;
                    goods_list = datas.goods_list;

                    SuosuoRecycleViewAdapter suosuoRecycleViewAdapter=new SuosuoRecycleViewAdapter(ShangPinShowActivity.this,goods_list);
                    suosuoRecycleViewAdapter.setItemClickListener(new SuosuoRecycleViewAdapter.MyItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            String img = goods_list.get(position).goods_image_url;
                            String title = goods_list.get(position).goods_name;
                            String price = goods_list.get(position).goods_price;
                            String goods_id = goods_list.get(position).goods_id;
                            Intent intent=new Intent(ShangPinShowActivity.this,GWCActivity.class);
                            intent.putExtra("img",img);
                            intent.putExtra("title",title);
                            intent.putExtra("price",price);
                            intent.putExtra("goods_id",goods_id);
                            startActivity(intent);
                        }
                    });
                    recyclerView.setAdapter(suosuoRecycleViewAdapter);
                    break;
            }
        }
    };
//    private List<JsonBeanSou.JsonBeanSou1.JsonBeanSou2.JSON> docs;
    private String url;
    private RecyclerView recyclerView;
    private EditText editText;
    private String string;
    private List<LaolishiBean.Laolishi.GT> goods_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shang_pin_show);
        editText = (EditText) findViewById(R.id.soueditText);
        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ShangPinShowActivity.this,SousuoActivity.class);
                startActivity(intent);
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.shangpin_RecycleView);
        //设置StaggeredGridLayoutManager布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //把布局管理器给RecyclerView
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        //接收传过来的值
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        string = intent.getStringExtra("string");
        Log.i("hhhhhh",url);
        getData(url);
    }

    private void getData(String url) {
        OkHttp3Utils.doGet(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Message msg = new Message();
                msg.what = 0;
                msg.obj = json;
                handler.sendMessage(msg);
            }
        });
    }
}
