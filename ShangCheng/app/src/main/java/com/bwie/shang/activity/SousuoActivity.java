package com.bwie.shang.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bwie.shang.R;

public class SousuoActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageButton imageButton;
    private EditText soueditText;
    private Button sousuoshang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sousuo);
        isitView();
    }

    //获取控件
    private void isitView() {
        imageButton = (ImageButton) findViewById(R.id.fanhui);
        soueditText = (EditText) findViewById(R.id.soueditText);
        sousuoshang = (Button) findViewById(R.id.sousahngpin);
        imageButton.setOnClickListener(this);
        sousuoshang.setOnClickListener(this);
    }

    //请求数据
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fanhui:
                Intent intent=new Intent(SousuoActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.sousahngpin:
                String string = soueditText.getText().toString();
//                String url ="http://list.mogujie.com/search?_version=61&ratio=3%3A4&cKey=46&minPrice=&_mgjuuid=dbcc6b5c-fcf7-49f4-b807-3e85fbcccc5b&ppath=&page=1&maxPrice=&sort=pop&userId=&cpc_offset=&priceList=&_=1504520539364&callback=jsonp1&q="+string;
                String url="http://169.254.229.178/mobile/index.php?act=goods&op=goods_list&page=100";
                Log.i("ssss",url);
                Intent intent1=new Intent(SousuoActivity.this,ShangPinShowActivity.class);
                intent1.putExtra("url",url);
                intent1.putExtra("string",string);
                startActivity(intent1);
                break;
        }
    }
}
