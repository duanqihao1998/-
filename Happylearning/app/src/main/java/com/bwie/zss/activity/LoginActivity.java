package com.bwie.zss.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bwie.zss.R;
import com.bwie.zss.persenter.ILoginPersenter;
import com.bwie.zss.persenter.LoginPersenter;
import com.bwie.zss.view.ILoginView;

public class LoginActivity extends AppCompatActivity implements ILoginView{

    private EditText username;
    private Button yzm;
    private Button login;
    private EditText useryzm;
    private String userphone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.username);
        useryzm = (EditText) findViewById(R.id.getyanzhengma);
        yzm = (Button) findViewById(R.id.yanzhengma);
        login = (Button) findViewById(R.id.login);
       final ILoginPersenter loginPersenter=new LoginPersenter(LoginActivity.this);
        yzm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userphone = username.getText().toString();
                loginPersenter.servencr(LoginActivity.this, userphone);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useryzmm = useryzm.getText().toString();
                loginPersenter.loginserver(LoginActivity.this,userphone,useryzmm);

            }
        });
    }

    @Override
    public void setPhone(String phone) {

    }
    @Override
    public void setPhone(String phone, String code) {
    }
}
