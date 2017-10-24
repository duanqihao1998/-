package test.bwie.com.jingdong.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.adapter.Sou_Shop_Adapter;
import test.bwie.com.jingdong.api.API;
import text.bwie.com.okhttpclient.util.OkHttp3Utils;

public class SouSuoActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText sousuo_shop;
    private Button sousahngpin;
    private ImageView fanhui;
    private String edit_shop;
    private Handler handler=new Handler();
    private RecyclerView recycleview;
    private CheckBox tiaojie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_suo);
        isitView();
        /**
         * 通过这三种方法进行三种不同的样式的显示
         */
        //设置LinearLayoutManager布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //设置GridLayoutManager布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        //设置StaggeredGridLayoutManager布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        //把布局管理器给RecyclerView
            recycleview.setLayoutManager(staggeredGridLayoutManager);
    }

    private void isitView() {
        sousuo_shop = (EditText) findViewById(R.id.soueditText);
        sousahngpin = (Button) findViewById(R.id.sousahngpin);
        fanhui = (ImageView) findViewById(R.id.fanhui);
        recycleview = (RecyclerView) findViewById(R.id.recycleview);
        sousahngpin.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }
    private void getData(){
        edit_shop = sousuo_shop.getText().toString();
        if(edit_shop.length()==0){
            Toast.makeText(this, "傻孩子，没输入商品", Toast.LENGTH_SHORT).show();
        }else {
            String getpath=API.search+edit_shop;
            OkHttp3Utils.doGet(getpath, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String stringJson = response.body().string();
                    Log.i("sousuo",stringJson);
                    handler.post(new Runnable() {

                        private JSONArray data;

                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject=new JSONObject(stringJson);
                                data = jsonObject.getJSONArray("data");
                                Sou_Shop_Adapter sou_shop_adapter=new Sou_Shop_Adapter(SouSuoActivity.this, data);
                                recycleview.setAdapter(sou_shop_adapter);
                                sou_shop_adapter.setOnItemClickListener(new Sou_Shop_Adapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View view, int position) {
                                        Toast.makeText(SouSuoActivity.this, "点击了第"+position, Toast.LENGTH_SHORT).show();
                                        try {
                                            String pid = data.getJSONObject(position).getString("pid");
                                            Intent intent=new Intent(SouSuoActivity.this,Shop_Xiangqing_Activity.class);
                                            intent.putExtra("pid",pid);
                                            startActivity(intent);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                });
//                            for (int i = 0; i < data.length(); i++) {
//                                data.getJSONObject(i).getString("images");
//                                data.getJSONObject(i).getString("title");
//                            }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sousahngpin:
                getData();
                break;
            case R.id.fanhui:
                finish();
                break;
        }
    }
}
