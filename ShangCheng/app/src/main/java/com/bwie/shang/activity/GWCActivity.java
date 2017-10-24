package com.bwie.shang.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
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

import com.bwie.shang.R;
import com.bwie.shang.util.SharedUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class GWCActivity extends AppCompatActivity implements View.OnClickListener {

    private String img;
    private String title;
    private String price;
    private String goods_id;
    private SharedUtil instances;
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
                    if(code==400){
                        Intent intent=new Intent(GWCActivity.this,LoginAndRegisterActivity.class);
                        startActivity(intent);
                        Toast.makeText(GWCActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
                    }
                    if(code==200){
                        Toast.makeText(GWCActivity.this, "添加成功", Toast.LENGTH_SHORT).show();

                        Log.i("gouwuche",json);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gwc);
        final Intent intent = getIntent();
        img = intent.getStringExtra("img");
        title = intent.getStringExtra("title");
        price = intent.getStringExtra("price");
        goods_id = intent.getStringExtra("goods_id");

        //获取登陆成功后的获取key
        instances = SharedUtil.getInstances();
        key = (String) instances.getValueByKey(GWCActivity.this, "key", "");

        //获取布局控件
        ImageView imageView =  (ImageView) findViewById(R.id.imageView);//商品图片
        TextView textView = (TextView) findViewById(R.id.textView);//商品标题
        TextView textView1 = (TextView) findViewById(R.id.textview1);//价格
        TextView fanhuui = (TextView) findViewById(R.id.fanhui);//返回商品展示
        TextView addGouwu = (TextView) findViewById(R.id.AddGouwu);//立即购买
        TextView shop = (TextView) findViewById(R.id.shop);//添加购物车
        //给设置的布局添加点击事件
        fanhuui.setOnClickListener(this);
        addGouwu.setOnClickListener(this);
        shop.setOnClickListener(this);
        //给控件添加值
        Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).into(imageView);
        textView.setText(title);
        textView1.setText(price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //返回按钮
            case R.id.fanhui:
                finish();
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
                        String url="http://169.254.229.178/mobile/index.php?act=member_cart&op=cart_add";
                        HashMap<String,String> params=new HashMap<>();
                        params.put("key",key);
                        params.put("goods_id",goods_id);
                        params.put("quantity","1");
                        OkHttp3Utils.doPost(url, params, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String json = response.body().string();
                                Log.i("tianjia",json);
                                Message message=new Message();
                                message.what=0;
                                message.obj=json;
                                handler.sendMessage(message);
                            }
                        });
                    }
                });
                Picasso.with(this).load(img).placeholder(R.drawable.ic_launcher).into(shangpin_image);
                shangpin_name.setText(title);
                shangpin_price.setText(price);

                bottomDialog.setContentView(contentView);
                ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
                layoutParams.width = getResources().getDisplayMetrics().widthPixels;
                layoutParams.height=height/2;

                contentView.setLayoutParams(layoutParams);
                bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
                bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
                bottomDialog.show();

                Toast.makeText(GWCActivity.this, "添加购物车", Toast.LENGTH_SHORT).show();
                break;
            //立即购买
            case R.id.shop:
                Toast.makeText(GWCActivity.this, "立即购买", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
