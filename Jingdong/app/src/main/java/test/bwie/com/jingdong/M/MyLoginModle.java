package test.bwie.com.jingdong.M;

import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.V.MyLoginView;
import test.bwie.com.jingdong.api.API;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

/**
 * Created by Love_you on 2017/10/18 0018.
 */

public class MyLoginModle implements LoginModle {
    private Handler handler=new Handler();
    @Override
    public void JiaoYan(String phone, String pass, final MyLoginView myLoginView) {
        Map<String,String> params=new HashMap<>();
        params.put("mobile",phone);
        params.put("password",pass);
        OkHttp3Utils.doPost(API.login, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i("Login",string);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(string);
                            //登陆的状态
                            int code = jsonObject.optInt("code");
                            String msg = jsonObject.optString("msg");

                            if(code==0){

                                //登陆后用户的信息
                                JSONObject data = jsonObject.getJSONObject("data");
                                String username = data.optString("username");
                                String uid = data.optString("uid");
                                Log.i("username",username);
                                myLoginView.LoginOK(username,msg,uid);
                            }else {
                                myLoginView.LoginNO(msg);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
