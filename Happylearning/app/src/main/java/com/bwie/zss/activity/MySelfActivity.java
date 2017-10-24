package com.bwie.zss.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zss.R;
import com.bwie.zss.javabean.PicData;
import com.bwie.zss.javabean.UpdateUser;
import com.bwie.zss.utils.SharedUtil;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3utilsstudy.text.com.okhttp3utils.BuildConfig;
import okhttp3utilsstudy.text.com.okhttp3utils.okhttputils.OkHttp3Utils;

public class MySelfActivity extends AppCompatActivity  implements View.OnClickListener {

    private TextView baocun;
    private ImageView updowmpic;
    private EditText clearname;
    private EditText sex;
    private EditText birthday;
    private EditText city;
    private EditText chilcount;
    private EditText nicheng;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    String obj = (String) msg.obj;
                    Gson gson=new Gson();
                    PicData picData = gson.fromJson(obj, PicData.class);
                    pic = picData.getData().getPic();
                    loadImage(pic);
                    break;
                case 1:
                    String obj1 = (String) msg.obj;
                    Gson gson1=new Gson();
                    UpdateUser updateUser = gson1.fromJson(obj1, UpdateUser.class);
                    nickname1 = updateUser.getData().getNickname();
                    headimgurl = updateUser.getData().getHeadimgurl();
                   SharedUtil instances = SharedUtil.getInstances();
                    instances.saveDatad(MySelfActivity.this,"nickname",nickname1);
                    break;
            }
        }
    };

    private void loadImage(final String pic) {
        //由于网络请求属于耗时操作 需要创建线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//地址
                    URL url = new URL(pic);
//请求方式
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(3000);
                    if (connection.getResponseCode() == 200) {
//流
                        InputStream inputStream = connection.getInputStream();
//文件名
                        String fileName = "/head.png";
//sd路径
                        File path = Environment.getExternalStorageDirectory();
//文件路径
                        File file = new File(path.getPath() + fileName);
//通过输出流 将流写进sd卡
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        int num = 0;
                        byte[] bytes = new byte[20480];
                        //读取流
                        while ((num = inputStream.read(bytes)) > 0) {
                            //写入
                            fileOutputStream.write(bytes, 0, num);
                        }
                        fileOutputStream.flush();
                        fileOutputStream.close();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String pic;
    private String nickname1;
    private String headimgurl;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_my_self);
        initId();
    }
    private void initId() {
        back = (ImageView) findViewById(R.id.rlBack);
        baocun = (TextView) findViewById(R.id.baocun);
        updowmpic = (ImageView) findViewById(R.id.updownpic);
        clearname = (EditText) findViewById(R.id.clearname);
        sex = (EditText) findViewById(R.id.sex);
        birthday = (EditText) findViewById(R.id.birthday);
        city = (EditText) findViewById(R.id.city);
        chilcount = (EditText) findViewById(R.id.chilcount);
        nicheng = (EditText) findViewById(R.id.nicheng);
        baocun.setOnClickListener(this);
        updowmpic.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.baocun:
                Map<String,String> hearders=new HashMap<>();
                SharedUtil instances = SharedUtil.getInstances();
                String token = (String) instances.getValueByKey(MySelfActivity.this, "token", "");
                String mobile = (String) instances.getValueByKey(MySelfActivity.this, "mobile", "");
                String userid = (String) instances.getValueByKey(MySelfActivity.this, "userid", "");
                hearders.put("userid",userid);
                hearders.put("token",token);
                hearders.put("mobile",mobile);
                hearders.put("cltid","1");
                String nickname = nicheng.getText().toString().trim();
                String sexa = sex.getText().toString().trim();
                String birthdaya = birthday.getText().toString().trim();
                String childcounta = chilcount.getText().toString().trim();
                String clearnamea = clearname.getText().toString().trim();
                String bcurl="http://lexue365.51dangao.cn/api/user/edit";
                if (TextUtils.isEmpty(nickname)){
                    Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(sexa)){
                    Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
                }else if (TextUtils.isEmpty(birthdaya)){
                    Toast.makeText(this, "生日不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(clearnamea)){
                    Toast.makeText(this, "真实姓名不能为空", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(childcounta)){
                    Toast.makeText(this, "孩子个数不能为空", Toast.LENGTH_SHORT).show();
                }else {
                    Map<String, String> params = new HashMap<>();
                    params.put("headimgurl", pic);
                    params.put("nickname", nickname);
                    params.put("sex", sexa);
                    params.put("birthday", birthdaya);
                    params.put("city_id", "2");
                    params.put("district_id", "220");
                    params.put("children_count", childcounta);
                    params.put("realname", clearnamea);
                    params.put("children", "0");
                    OkHttp3Utils.doPostHeader(bcurl, hearders, params, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String bca = response.body().string();
                            Log.i("bxa", bca);
                            Message message = new Message();
                            message.what = 1;
                            message.obj = bca;
                            handler.sendMessage(message);

                        }
                    });
                }
                break;
            case R.id.updownpic:
                openPic();
                break;
            case R.id.rlBack:
                //得到一个空的Intent
                Intent it=new Intent();
                //传值
                it.putExtra("nickname",nickname1);
                it.putExtra("headimage",headimgurl);

                //resultCode 结果码???
                setResult(0, it);
                //销毁Activity
                finish();
                break;
        }

    }
    private void openPic() {
        //隐示意图激活
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, 200);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            getPicData(data);
        }
    }
    //获取图片
    public void getPicData(Intent data) {
        String url="http://lexue365.51dangao.cn/api/file/upload_img";
        try {
        if (data != null) {
            Uri uri=data.getData();
            Log.i("uri",uri+"");
            this.grantUriPermission(BuildConfig.APPLICATION_ID,uri,Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            updowmpic.setImageBitmap(bitmap);
            File file = getFile(bitmap);
            Log.i("tmd",file+"");
            OkHttp3Utils.loadFile(url, file, "temp.png", new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String pic = response.body().string();
                    Log.i("zza",pic);
                    Message message=new Message();
                    message.what=0;
                    message.obj=pic;
                    handler.sendMessage(message);

                }
            });

        }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File getFile(Bitmap bitmap) {
        File file = new File(Environment.getExternalStorageDirectory(),"zz.png");
        try {
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}

