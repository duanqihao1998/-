package com.bwie.shang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shang.R;
import com.bwie.shang.util.TextUtil;
import com.bwie.shang.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class RegisterActivity extends AppCompatActivity {

    private TextView reg_cancel;
    private EditText reg_username_edit;
    private EditText reg_password_edit;
    private EditText reg_email_edit;
    private TextView reg_reg;
    //    private String url="http://169.254.92.58/mobile/index.php?act=login&op=register";
    private String url = "http://169.254.229.178/mobile/index.php?act=login&op=register";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json = (String) msg.obj;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    //判断请求码是否成功
                    if (code == 200) {
                        //注册成功
                        Intent intent = new Intent(RegisterActivity.this, LoginAndRegisterActivity.class);
                        startActivity(intent);
                    }
                    if (code == 400) {
                        Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
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
        setContentView(R.layout.activity_register);
        isitView();
        reg_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginAndRegisterActivity.class);
                startActivity(intent);
            }
        });
        //注册按钮设置点击事件
        reg_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String reg_username = reg_username_edit.getText().toString();
                String reg_password = reg_password_edit.getText().toString();
                String reg_email = reg_email_edit.getText().toString();
                String client = "android";
                if (TextUtil.isEmpty(reg_username)) {
                    ToastUtil.show(RegisterActivity.this, "请输入用户名");
                    return;
                }
                if (TextUtil.isEmpty(reg_password)) {
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtil.isEmpty(reg_email)) {
                    Toast.makeText(RegisterActivity.this, "请输入邮箱", Toast.LENGTH_SHORT).show();
                    return;
                }

                HashMap<String, String> params = new HashMap<>();
                params.put("username", reg_username);
                params.put("password", reg_password);
                params.put("password_confirm", reg_password);
                params.put("email", reg_email);
                params.put("client", client);

                OkHttp3Utils.doPost(url, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.i("qingqiu", json);
                        Message message=new Message();
                        message.obj=json;
                        message.what=0;
                        handler.sendMessage(message);
                    }
                });
            }
        });
    }

    //获取控件
    private void isitView() {
        reg_cancel = (TextView) findViewById(R.id.reg_cancel);//取消按钮
        reg_username_edit = (EditText) findViewById(R.id.reg_username_edit);//用户名输入框
        reg_password_edit = (EditText) findViewById(R.id.reg_password_edit);//密码输入名
        reg_email_edit = (EditText) findViewById(R.id.reg_email_edit);//邮箱
        reg_reg = (TextView) findViewById(R.id.reg_reg);//登陆按钮
    }
}
