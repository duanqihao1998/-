package com.bwie.zss.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zss.R;
import com.bwie.zss.activity.LoginActivity;
import com.bwie.zss.activity.MySelfActivity;
import com.bwie.zss.utils.SharedUtil;
import com.squareup.picasso.Picasso;

import java.io.File;

/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  14：23
 */

public class UserFragment extends Fragment implements View.OnClickListener {

    private View view;
    private TextView unLogin;
    private TextView userzl;
    private ImageView loginImage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.user_fragment, container, false);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null) {
            group.removeView(view);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initId();
    }

    private void initId() {
        unLogin = view.findViewById(R.id.unLogin);
        userzl = view.findViewById(R.id.tvNickName);
        loginImage = view.findViewById(R.id.loginImage);
        unLogin.setOnClickListener(this);
        userzl.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.unLogin:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("确定要删除吗？");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "您已退出登录", Toast.LENGTH_SHORT).show();
                        SharedUtil instances = SharedUtil.getInstances();
                        instances.clearAllData(getActivity());
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getActivity(), "您已取消退出", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create();
                builder.show();
                break;
            case R.id.tvNickName:
                Intent intent = new Intent(getActivity(), MySelfActivity.class);
                startActivityForResult(intent, 1);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 0) {
            //接受回传的值
            String nickname = data.getStringExtra("nickname");
            String headimage = data.getStringExtra("headimage");
            Picasso.with(getActivity()).load(headimage).into(loginImage);
            userzl.setText(nickname);
            //设值展示
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        SharedUtil instances = SharedUtil.getInstances();
        boolean config = (boolean) instances.getValueByKey(getActivity(), "config", false);
        if (config) {
            String nickname = (String) instances.getValueByKey(getActivity(), "nickname", "");
/**
 * 获取SD卡中的文件
 **/
//文件名
            String fileName = "/head.png";
//路径
            File path = Environment.getExternalStorageDirectory();
//获取SD图片
            File mFile = new File(path.getPath() + fileName);
//若该文件存在
            if (mFile.exists()) {
//将文件直接转换为Bitmap 这样可以直接给ImageView
                Bitmap bitmap = BitmapFactory.decodeFile(mFile.getPath());
                loginImage.setImageBitmap(bitmap);
                userzl.setText(nickname);
            }
        }
    }
}
