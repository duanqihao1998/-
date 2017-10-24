package test.bwie.com.jingdong.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.adapter.Sou_Shop_Adapter;
import test.bwie.com.jingdong.api.API;
import test.bwie.com.jingdong.util.SharedUtil;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class Shop_Xiangqing_Activity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json= (String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    JSONObject data = jsonObject.optJSONObject("data");
                    images = data.optString("images");
                    String[] split = images.split("\\|");
                    if(split.length>1){
                        Picasso.with(Shop_Xiangqing_Activity.this).load(split[1]).placeholder(R.mipmap.ic_launcher).into(imageView);
                    }else {
                        Picasso.with(Shop_Xiangqing_Activity.this).load(images).placeholder(R.mipmap.ic_launcher).into(imageView);
                    }
                    title = data.optString("title");
                    price = data.optString("￥ "+"price");
                    textView.setText(title);
                    textView1.setText(price);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private String pid;//商品id
    private boolean config;//登陆状态
    private String images;//图片
    private TextView fanhui;
    private ImageView imageView;
    private TextView textView;
    private TextView textView1;
    private TextView addGouwu;
    private TextView shopping;
    private String uid;//用户id
    private String title;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__xiangqing_);
        SharedUtil instances = SharedUtil.getInstances();
        config = (boolean) instances.getValueByKey(Shop_Xiangqing_Activity.this, "config", false);
        if(config){
            uid = (String) instances.getValueByKey(this, "uid", "");
        }
        Intent intent = getIntent();
        pid = intent.getStringExtra("pid");
        getData();
        isitView();
    }

    private void isitView() {
        //返回
        fanhui = (TextView) findViewById(R.id.fanhui);
        //商品详情图片
        imageView = (ImageView) findViewById(R.id.imageView);
        //商品的名称
        textView = (TextView) findViewById(R.id.textView);
        //商品的价格
        textView1 = (TextView) findViewById(R.id.textview1);
        //添加购物车
        addGouwu = (TextView) findViewById(R.id.AddGouwu);
        //购买
        shopping = (TextView) findViewById(R.id.shopping);
        fanhui.setOnClickListener(this);
        addGouwu.setOnClickListener(this);
        shopping.setOnClickListener(this);
    }

    /**
     * 商品详情
     */
    private void getData() {
        Map<String,String> params=new HashMap<>();
        params.put("pid",pid);
        OkHttp3Utils.doPost(API.getProductDetail,params ,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i("xiangqing",string);
                Message message=new Message();
                message.obj=string;
                message.what=0;
                handler.sendMessage(message);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //返回
            case R.id.fanhui:
                finish();
                break;
            //购买
            case R.id.shopping:
                break;
            //添加购物车
            case R.id.AddGouwu:
                //获取屏幕的高
                WindowManager wm = this.getWindowManager();
                int height = wm.getDefaultDisplay().getHeight();

                Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);
                View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_content_normal, null);

                //获取布局的id
                ImageView shangpin_image =  (ImageView) contentView.findViewById(R.id.shangpin_image);
                TextView shangpin_name = (TextView) contentView.findViewById(R.id.shangpin_name);
                TextView shangpin_price = (TextView) contentView.findViewById(R.id.shangpin_price);
                Button button = (Button) contentView.findViewById(R.id.button);

                //给确定按钮添加点击事件将商品添加到购物车当中
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //商品添加按钮
                        String url=API.addCart;
                        HashMap<String,String> params=new HashMap<>();
                        params.put("uid",uid);
                        params.put("pid",pid);
                        OkHttp3Utils.doPost(url, params, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                final String json = response.body().string();
                                Log.i("tianjia",json);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            JSONObject jsonObject=new JSONObject(json);
                                            String msg = jsonObject.optString("msg");
                                            Toast.makeText(Shop_Xiangqing_Activity.this, msg, Toast.LENGTH_SHORT).show();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                    }
                });
                String[] split = images.split("\\|");
                if(split.length>1){
                    Picasso.with(Shop_Xiangqing_Activity.this).load(split[1]).placeholder(R.mipmap.ic_launcher).into(shangpin_image);
                }else {
                    Picasso.with(Shop_Xiangqing_Activity.this).load(images).placeholder(R.mipmap.ic_launcher).into(shangpin_image);
                }
                shangpin_name.setText(title);
                shangpin_price.setText("￥ "+price);

                bottomDialog.setContentView(contentView);
                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                layoutParams.height=height/2;

                contentView.setLayoutParams(layoutParams);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();
                break;
        }
    }
}
