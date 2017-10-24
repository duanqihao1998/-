package com.bwie.shang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shang.R;
import com.bwie.shang.bean.Shangpin;
import com.bwie.shang.util.SharedUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class DingDanxiangqingActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Shangpin> lists;
    private String key;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json = (String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if(code==200){
                        JSONObject datas = jsonObject.getJSONObject("datas");
                        JSONObject store_cart_list = datas.getJSONObject("store_cart_list");
                        JSONObject jsonObject1 = store_cart_list.getJSONObject("1");
                        JSONArray goods_list =  jsonObject1.getJSONArray("goods_list");
                        for(int i=0;i<goods_list.length();i++){
                            JSONObject goods_list_obj=goods_list.optJSONObject(i);
                            JSONObject goods_image_url = goods_list_obj.optJSONObject("goods_image_url");//商品图片
                            JSONObject goods_name = goods_list_obj.optJSONObject("goods_name");//商品名称
                            JSONObject goods_price = goods_list_obj.optJSONObject("goods_price");//商品价格
                            JSONObject store_goods_total = goods_list_obj.optJSONObject("store_goods_total");//总价
                            Log.i("shuju",goods_image_url+""+goods_name+goods_price+store_goods_total);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private TextView querendindan;
    private ListView dingdanListview;
    private TextView tv_settlement;
    private TextView tv_show_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ding_danxiangqing);
        SharedUtil instances = SharedUtil.getInstances();
        key = (String) instances.getValueByKey(DingDanxiangqingActivity.this, "key", "");
        //接收传传过来的值
        Intent intent = getIntent();
        lists = (List<Shangpin>) intent.getSerializableExtra("lists");
        getdata();
        //获取布局控件
        isitView();
    }

    //布局控件
    private void isitView() {
        querendindan = (TextView) findViewById(R.id.querendindan);
        dingdanListview = (ListView) findViewById(R.id.dingdanListview);
        tv_settlement = (TextView) findViewById(R.id.tv_settlement);
        tv_show_price = (TextView) findViewById(R.id.tv_show_price);

        querendindan.setOnClickListener(this);
        tv_settlement.setOnClickListener(this);
    }

    private void getdata() {
        String url="http://169.254.229.178/mobile/index.php?act=member_buy&op=buy_step1";
        String cart_id=null;
        for (int i = 0; i < lists.size(); i++) {
            if(i==0){
                cart_id=lists.get(i).id +"|"+lists.get(i).count;
            }else {
                cart_id+=","+lists.get(i).id +"|"+lists.get(i).count;
            }
        }
        HashMap<String,String> params=new HashMap<>();
        params.put("key",key);
        params.put("cart_id",cart_id);
        params.put("ifcart","one");
        OkHttp3Utils.doPost(url, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                Message message=new Message();
                message.obj=json;
                message.what=0;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回订单
            case R.id.querendindan:
                finish();
                break;
            //确认订单
            case R.id.tv_settlement:
                Toast.makeText(DingDanxiangqingActivity.this, "确认提交订单", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
