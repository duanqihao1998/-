package test.bwie.com.jingdong.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.api.API;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

/**
 * Created by Love_you on 2017/10/21 0021.
 */

public class SanjiFenLei extends Fragment {

    private TextView textView;

    public static String pid;
    public static SanjiFenLei getInstance(String id)
    {
        pid=id;
        return new SanjiFenLei();
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0){
                String json= (String) msg.obj;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        TextView textView=new TextView(getActivity());
//        textView.setText("niaho");
        View view = inflater.inflate(R.layout.fenlei01, null);
        textView = view.findViewById(R.id.tttt);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textView.setText(pid);
        Map<String,String> params=new HashMap<>();
        OkHttp3Utils.doPost(API.getProductCatagory, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Message message=new Message();
                message.obj=string;
                message.what=0;
                handler.sendMessage(message);
            }
        });
    }
}
