package com.bwie.zss.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.bwie.zss.R;
import com.bwie.zss.javabean.ActivityData;
import com.bwie.zss.javabean.PayData;
import com.bwie.zss.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

public class PayBillActivity extends AppCompatActivity implements View.OnClickListener{

    private ActivityData.DataBean.DateListBean date;
    private String activity_id;
    private TextView title_data;
    private TextView date_date;
    private TextView tvchildPrice;
    private Button btnMinusChild;
    private TextView tvChildCount;
    private Button btnAddChild;
    private TextView tvAdultPrice;
    private Button btnMinusAdult;
    private TextView tvAdultCount;
    private Button btnAddAdult;
    private EditText editContacterName;
    private EditText editContacterPhone;
    private EditText editNote;
    private TextView tvMoney;
    private Button btnPay;
    private ActivityData.DataBean datadata;
    private int childCount;
    private int adultCount;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String obj = (String) msg.obj;
                    Gson gson=new Gson();
                    PayData payData = gson.fromJson(obj, PayData.class);
                    PayData.DataBean data = payData.getData();
                    Intent intent=new Intent(PayBillActivity.this,PaySelectActivity.class);
                    intent.putExtra("paydata",data);
                    startActivity(intent);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_pay_bill);
        date = (ActivityData.DataBean.DateListBean) getIntent().getExtras().get("data");
        activity_id = (String) getIntent().getExtras().get("activity_id");
        datadata = (ActivityData.DataBean) getIntent().getExtras().get("data_data");
        Log.i("daa",activity_id+""+date.getDate());
        initId();
    }

    private void initId() {
        //日期标题
        title_data = (TextView) findViewById(R.id.title_data);
        title_data.setText(datadata.getTitle());
        //日期时间
        date_date = (TextView) findViewById(R.id.date_date);
        if ("2".equals(date.getDays())) {
            date_date.setText(date.getDate());
        } else {
            date_date.setText(date.getDate() + " " + date.getStart_time() + "-" + date.getEnd_time());
        }
        //孩子价钱
        tvchildPrice = (TextView) findViewById(R.id.childPrice);
        tvchildPrice.setText(Float.parseFloat(datadata.getChild_price()) + "");
        //孩子减少
        btnMinusChild = (Button) findViewById(R.id.btnMinusChild);
        //孩子数量
        tvChildCount = (TextView) findViewById(R.id.tvChildCount);
        //孩子增加
        btnAddChild = (Button) findViewById(R.id.btnAddChild);
        //成人价钱
        tvAdultPrice = (TextView) findViewById(R.id.tvAdultPrice);
        tvAdultPrice.setText(Float.parseFloat(datadata.getAdult_price()) + "");
        //成人减少
        btnMinusAdult = (Button) findViewById(R.id.btnMinusAdult);
        //成人数量
        tvAdultCount = (TextView) findViewById(R.id.tvAdultCount);
        //成人增加
        btnAddAdult = (Button) findViewById(R.id.btnAddAdult);
        //姓名
        editContacterName = (EditText) findViewById(R.id.editContacterName);
        //手机号
        editContacterPhone = (EditText) findViewById(R.id.editContacterPhone);
        //备注
        editNote = (EditText) findViewById(R.id.editNote);
        //支付的钱
        tvMoney = (TextView) findViewById(R.id.tvMoney);
        //提交订单
        btnPay = (Button) findViewById(R.id.btnPay);
        btnMinusAdult.setOnClickListener(this);
        btnMinusChild.setOnClickListener(this);
        btnAddAdult.setOnClickListener(this);
        btnAddChild.setOnClickListener(this);
        btnPay.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        childCount = Integer.parseInt(tvChildCount.getText().toString());
        adultCount = Integer.parseInt(tvAdultCount.getText().toString());
        switch (view.getId()){
            case R.id.btnPay:
                submit();

                break;

            case R.id.btnMinusChild://减少儿童
                if (childCount > 0) {
                    tvChildCount.setText((childCount - 1) + "");
                    calculate();
                }
                break;

            case R.id.btnAddChild://增加儿童
                tvChildCount.setText((childCount + 1) + "");
                calculate();
                break;

            case R.id.btnMinusAdult://减少成人
                if (adultCount > 0) {
                    tvAdultCount.setText((adultCount - 1) + "");
                }
                calculate();
                break;

            case R.id.btnAddAdult://增加成人
                tvAdultCount.setText((adultCount + 1) + "");
                calculate();
                break;

        }
    }

    private void submit() {
        Map<String,String> hearders=new HashMap<>();
        SharedUtil instances = SharedUtil.getInstances();
        String token = (String) instances.getValueByKey(PayBillActivity.this, "token", "");
        String mobile = (String) instances.getValueByKey(PayBillActivity.this, "mobile", "");
        String userid = (String) instances.getValueByKey(PayBillActivity.this, "userid", "");
        hearders.put("userid",userid);
        hearders.put("token",token);
        hearders.put("mobile",mobile);
        hearders.put("cltid","1");
        String name = editContacterName.getText().toString().trim();
        String phoneNumber = editContacterPhone.getText().toString().trim();
        String note = editNote.getText().toString().trim();
        String pay_url="http://lexue365.51dangao.cn/api/order/add_order";
        Map<String,String> params=new HashMap<>();
        params.put("activity_id", activity_id);
        params.put("time_id", date.getTime_id());
        if (childCount!= 0) {
            params.put("child_num", childCount+"");
            Log.i("chi",childCount+"");
        }
        if (adultCount != 0) {
            params.put("adult_num", adultCount+"");
        }
        params.put("contact", name);
        params.put("mobile", phoneNumber);
        params.put("remark", note);
        params.put("coupon_id", 0+"");
        OkHttp3Utils.doPostHeader(pay_url,hearders, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String pay_data = response.body().string();
                Log.i("pai",pay_data);
                Message message=new Message();
                message.what=0;
                message.obj=pay_data;
                handler.sendMessage(message);
            }
        });
    }
    private void calculate() {
        int childCount = Integer.parseInt(tvChildCount.getText().toString());
        int adultCount = Integer.parseInt(tvAdultCount.getText().toString());
        int childPrice = (int) (Float.parseFloat(tvchildPrice.getText().toString().trim()) * 100);
        int adultPrice = (int) (Float.parseFloat(tvAdultPrice.getText().toString().trim())* 100);

        int totalPrice = childPrice * childCount + adultPrice * adultCount;

        if (totalPrice > 0) {
            tvMoney.setText(totalPrice / 1.0f / 100 + "");
        } else {
            tvMoney.setText("0.01");
        }
    }

}
