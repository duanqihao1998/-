package com.bwie.zss.mode;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.bwie.zss.activity.MainActivity;
import com.bwie.zss.javabean.LoginData;
import com.bwie.zss.utils.Api;
import com.bwie.zss.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  21：10
 */

public class LoginMode implements ILoginMode {
    private Handler handler=new Handler();
    @Override
    public void getPhone(Context content,String phone) {
        Map<String,String> params=new HashMap<>();
        params.put("mobile",phone);
        OkHttp3Utils.doPost(Api.YZM, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Log.i("xxx",string);

            }
        });

    }

    @Override
    public void getLoginPhone(final Context content, String phone, String code) {
        Map<String,String> parats=new HashMap<>();
        parats.put("mobile",phone);
        parats.put("code",code);
        parats.put("clt_id","1");
        OkHttp3Utils.doPost(Api.LOGIN, parats, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String login = response.body().string();
                Log.i("log",login);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson=new Gson();
                        LoginData loginData = gson.fromJson(login, LoginData.class);
                        LoginData.DataBean data = loginData.getData();
                        int code = loginData.getCode();
                        Log.i("code",code+"");
                        if (code==0){
                            SharedUtil instances = SharedUtil.getInstances();
                            instances.saveDatad(content,"mobile",data.getMobile());
                            instances.saveDatad(content,"token",data.getToken());
                            instances.saveDatad(content,"userid",data.getUserid());
                            instances.saveDatad(content,"config",true);
                            content.startActivity(new Intent(content, MainActivity.class));
                        }
                    }
                },2000);
            }
        });
    }


}
