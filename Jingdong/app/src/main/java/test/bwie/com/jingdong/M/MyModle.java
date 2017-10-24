package test.bwie.com.jingdong.M;

import android.os.Handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.V.MyIndexView;
import test.bwie.com.jingdong.V.MyView;
import test.bwie.com.jingdong.api.API;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

/**
 * Created by Love_you on 2017/10/17 0017.
 */

public class MyModle implements MyM {
    private Handler handler=new Handler();
    @Override
    public void regin_user(String phone, String pass, final MyView myView) {
        Map<String,String> params=new HashMap<>();
        params.put("mobile",phone);
        params.put("password",pass);
        OkHttp3Utils.doPost(API.regin, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myView.getDataJSON(string);
                    }
                });
            }
        });
    }

    @Override
    public void Index(final MyIndexView myIndexView) {
        OkHttp3Utils.doGet(API.getAd, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        myIndexView.getIndexJSON(string);

                    }
                });
            }
        });
    }
}
