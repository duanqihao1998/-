package test.bwie.com.jingdong.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import test.bwie.com.jingdong.P.MyLoginPresenter;
import test.bwie.com.jingdong.P.MyPresenter;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.V.MyLoginView;
import test.bwie.com.jingdong.V.MyView;
import test.bwie.com.jingdong.util.SharedUtil;

public class Denglu_Activity extends AppCompatActivity implements View.OnClickListener,MyLoginView {

    private TextView text_regin;
    private Button login_but;//登陆
    private EditText login_phone;//登陆手机号
    private EditText login_pass;//登陆密码
    private ImageView x_image;//返回
    private String login_userphone;
    private String login_password;
    private MyLoginPresenter presenter;
    private SharedUtil instances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denglu_);
        isitView();
    }

    private void getLogin() {
        login_userphone = login_phone.getText().toString();
        login_password = login_pass.getText().toString();
    }

    private void isitView() {
        text_regin = (TextView) findViewById(R.id.text_regin);
        login_but = (Button) findViewById(R.id.Login_but);
        login_phone = (EditText) findViewById(R.id.Login_phone);
        login_pass = (EditText) findViewById(R.id.Login_pass);
        x_image = (ImageView) findViewById(R.id.X_Image);
        text_regin.setOnClickListener(this);
        login_but.setOnClickListener(this);
        x_image.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //跳转注册
            case R.id.text_regin:
                Intent intent=new Intent(Denglu_Activity.this,Zhuce_Activity.class);
                startActivity(intent);
                break;
            //登陆
            case R.id.Login_but:
                getLogin();
                if(login_userphone.length()<=0&&login_password.length()<=0){
                    Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    presenter = new MyLoginPresenter(this);
                    presenter.getData(login_userphone,login_password);
                }
                break;
            case R.id.X_Image:
                finish();
                break;
        }
    }

    //登陆成功的方法
    @Override
    public void LoginOK(String username,String msg,String uid) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        instances = SharedUtil.getInstances();
        instances.saveDatad(Denglu_Activity.this,"username",username);
        instances.saveDatad(Denglu_Activity.this,"uid",uid);
        instances.saveDatad(Denglu_Activity.this,"config",true);
        Intent intent=new Intent(Denglu_Activity.this,MyActivity.class);
        startActivity(intent);
        finish();
    }

    //登陆失败的方法
    @Override
    public void LoginNO(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
