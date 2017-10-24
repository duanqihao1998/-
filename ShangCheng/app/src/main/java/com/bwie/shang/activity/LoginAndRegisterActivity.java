package com.bwie.shang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bwie.shang.R;
import com.bwie.shang.util.SharedUtil;
import com.bwie.shang.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class LoginAndRegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edit_username;
    private EditText edit_password;
    private Button buttom_login;
    private TextView button_register;
    private String url="http://169.254.229.178/mobile/index.php?act=login";
    private String client="android";
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json = (String) msg.obj;
                try {
                    JSONObject jsonObject=new JSONObject(json);
                    int code = jsonObject.getInt("code");
                    if (code==200) {
//                        JSONObject datas = jsonObject.getJSONObject("datas");
//                        GetSharedPreferences.mEditor.putBoolean("login",true);
//                        GetSharedPreferences.mEditor.putString("id",datas.optInt());
                        JSONObject datas = jsonObject.getJSONObject("datas");
                        SharedUtil instances = SharedUtil.getInstances();
                        instances.saveDatad(LoginAndRegisterActivity.this,"key",datas.optString("key"));
                        String key = (String) instances.getValueByKey(LoginAndRegisterActivity.this, "key", "");
                        instances.saveDatad(LoginAndRegisterActivity.this,"userid",datas.optInt("userid"));
                        instances.saveDatad(LoginAndRegisterActivity.this,"username",datas.optString("username"));
                        instances.saveDatad(LoginAndRegisterActivity.this,"config",true);

                        ToastUtil.show(LoginAndRegisterActivity.this,"登陆成功");
                        Intent intent=new Intent(LoginAndRegisterActivity.this,MyActivity.class);
                        startActivity(intent);
                    }
                    if(code==400){
                        ToastUtil.show(LoginAndRegisterActivity.this,"登陆失败");
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
        setContentView(R.layout.activity_login_and_register);
        inistView();
    }

    //获取控件
    private void inistView() {
        edit_username = (EditText) findViewById(R.id.edit_username);
        edit_password = (EditText) findViewById(R.id.edit_password);
        buttom_login = (Button) findViewById(R.id.buttom_Login);
        button_register = (TextView) findViewById(R.id.button_Register);
        buttom_login.setOnClickListener(this);
        button_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //登陆按钮
            case R.id.buttom_Login:
                String username = edit_username.getText().toString().trim();
                String password = edit_password.getText().toString().trim();
                Log.i("xx",username+"   "+password);
                HashMap<String,String> params=new HashMap<>();
                params.put("username",username);
                params.put("password",password);
                params.put("client",client);
                OkHttp3Utils.doPost(url, params, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String json = response.body().string();
                        Log.i("denglu",json);
                        Message message=new Message();
                        message.obj=json;
                        message.what=0;
                        handler.sendMessage(message);
                    }
                });
                break;
            //注册按钮
            case R.id.button_Register:
                Intent intent=new Intent(LoginAndRegisterActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
