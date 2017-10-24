package test.bwie.com.jingdong.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import test.bwie.com.jingdong.P.MyPresenter;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.V.MyView;

public class Zhuce_Activity extends AppCompatActivity implements MyView {

    private MyPresenter presenter;
    private Button regin_but;//注册按钮
    private TextView regin_pass;//注册密码
    private TextView regin_phone;//注册电话
    private ImageView return_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhuce_);
        isitView();
        regin_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(regin_phone.getText().length()>0&&regin_pass.getText().length()>0){
                    presenter = new MyPresenter(Zhuce_Activity.this);
                    presenter.getData(regin_phone.getText().toString(),regin_pass.getText().toString());
                }else if(regin_phone.getText().length()==0&&regin_pass.getText().length()==0){
                    Toast.makeText(Zhuce_Activity.this, "", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void isitView() {
        regin_but = (Button) findViewById(R.id.regin_but);
        regin_pass = (TextView) findViewById(R.id.regin_pass);
        regin_phone = (TextView) findViewById(R.id.regin_phone);
        return_login = (ImageView) findViewById(R.id.Return_login);
    }

    @Override
    public void getDataJSON(String json) {
        Log.i("json",json);
        try {
            JSONObject jsonObject=new JSONObject(json);
            int code = jsonObject.optInt("code");
            String msg = jsonObject.optString("msg");
            if(code==0){
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(Zhuce_Activity.this,Denglu_Activity.class);
                startActivity(intent);
            }else {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
