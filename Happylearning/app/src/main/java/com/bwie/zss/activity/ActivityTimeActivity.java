package com.bwie.zss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import com.bwie.zss.R;
import com.bwie.zss.javabean.ActivityData;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

public class ActivityTimeActivity extends AppCompatActivity {

    private String activity_id;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String obj = (String) msg.obj;
                    Log.i("obj",obj);
                    Gson gson=new Gson();
                    ActivityData activityData = gson.fromJson(obj, ActivityData.class);
                    date_list = activityData.getData().getDate_list();
                    data_data = activityData.getData();
                    adapterData(date_list);


                    break;
            }
        }
    };
    private List<ActivityData.DataBean.DateListBean> date_list;
    private MyAdapter myAdapter;
    private ActivityData.DataBean data_data;

    private void adapterData(List<ActivityData.DataBean.DateListBean> date_list) {

        if (myAdapter==null){
            myAdapter = new MyAdapter();
            gridview.setAdapter(myAdapter);

        }else {
            myAdapter.notifyDataSetChanged();
        }
    }
    private GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay);
        //获取数据
        initId();
        activity_id = getIntent().getStringExtra("activity_id");
        initData();
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent=new Intent(ActivityTimeActivity.this,PayBillActivity.class);
                intent.putExtra("activity_id",activity_id);
                intent.putExtra("data_data",data_data);
                intent.putExtra("data",date_list.get(i));
                startActivity(intent);
            }
        });
    }

    private void initId() {
        gridview = (GridView) findViewById(R.id.gridview);
    }

    private void initData() {
        String url="http://lexue365.51dangao.cn/api/activity/choose_date";
        OkHttp3Utils.doGet(url+"?activity_id="+activity_id, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Log.i("dd",json);
                Message message=new Message();
                message.what=0;
                message.obj=json;
                handler.sendMessage(message);
            }
        });
    }

    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return date_list.size();
        }

        @Override
        public Object getItem(int position) {
            return date_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getApplicationContext(), R.layout.activity_data_item, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv);
            if ("1".equals(date_list.get(position).getDays())) {
                tv.setText(date_list.get(position).getDate() + " " + date_list.get(position).getStart_time() + "-" + date_list.get(position).getEnd_time());
            } else {
                tv.setText(date_list.get(position).getDate());
            }
            return convertView;
        }
    }
}
