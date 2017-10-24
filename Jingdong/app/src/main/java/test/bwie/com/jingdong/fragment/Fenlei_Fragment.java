package test.bwie.com.jingdong.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.Bean.San_FenLei;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.adapter.MyAdapter;
import test.bwie.com.jingdong.api.API;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

/**
 * Created by Love_you on 2017/9/28 0028.
 */
public class Fenlei_Fragment extends Fragment {
    private List<San_FenLei> listOne=new ArrayList<>();
    private Handler handler=new Handler();
    private View view;
    private ListView listView;
    private FrameLayout frameLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fenlei_view,null);
        listView = view.findViewById(R.id.listview);
        frameLayout = view.findViewById(R.id.fragmlayout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        CreatFragment(1+"");
    }


    //数据
    private void getData() {
        OkHttp3Utils.doGet(API.getCatagory, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                Log.i("fenglei",string);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject jsonObject=new JSONObject(string);
                            JSONArray data = jsonObject.getJSONArray("data");
                            for (int i = 0; i < data.length(); i++) {
                                String name = data.getJSONObject(i).getString("name");
                                String cid = data.getJSONObject(i).getString("cid");
                                San_FenLei san_fenLei=new San_FenLei(name,cid);
                                listOne.add(san_fenLei);
                            }
                            MyAdapter adapter=new MyAdapter(getActivity(),listOne);
                            listView.setAdapter(adapter);
                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                    Log.i("aaa","点击了"+i);
                                    String pid = listOne.get(i).pid;
                                    Log.i("pid",pid);
                                    CreatFragment(pid);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private void CreatFragment(String pid) {
        //获得管理器
        FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        //开启事务
        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        //替换
        beginTransaction.replace(R.id.fragmlayout,SanjiFenLei.getInstance(pid));
        //提价
        beginTransaction.commit();

    }
}
