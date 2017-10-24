package com.bwie.zss.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.bwie.zss.R;
import com.bwie.zss.javabean.DetailsData;
import com.bwie.zss.utils.SharedUtil;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    private WebView detailwebview;
    private Button lijibm;
    private DetailsData detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_details);
        initId();
        //获取传递对象的Bundle
        Bundle bundle=getIntent().getExtras();
        //获取数据
        detail = (DetailsData) bundle.get("details");
        initWebView(detail);
    }

    private void initId() {

        lijibm = (Button) findViewById(R.id.lijibmm);
        lijibm.setOnClickListener(this);
    }

    private void initWebView(DetailsData detail) {
        detailwebview = (WebView) findViewById(R.id.detail_webview);
        WebSettings webSettings = detailwebview.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);//支持js

        detailwebview.setWebChromeClient(new WebChromeClient() {
            //此方法可获得网页标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.i("xxx", title);//title就是网页标题
            }

            //处理JavaScript Alert事件
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                // 用Android组件替换
                new AlertDialog.Builder(DetailsActivity.this).setMessage(message).setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        result.confirm();
                    }
                }).setCancelable(false).create().show();
                return true;
            }

            /**处理定位的相关，否则WebView不会开启定位功能，类似百度地图这样的就没法定位*/
            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);              //这个必须有
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            /**打开网页的进度,newProgress就是*/
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        detailwebview.addJavascriptInterface(new Object(){
            //获取经纬度
            @JavascriptInterface
            public void openMap(String longitude,String latitude){
                Log.e("TAG",longitude+"  "+latitude);
                Intent intent = new Intent(DetailsActivity.this,AddressActivity.class);
                intent.putExtra("weidu",latitude);
                intent.putExtra("jindu",longitude);
                startActivity(intent);
            }
            @JavascriptInterface
            public void jumpShop(String url){
                Log.e("url",url.toString());
                Intent it=new Intent(DetailsActivity.this,CompanyActivity.class);
                it.putExtra("url",url);
                startActivity(it);
            }

        },"lexue365");
        detailwebview.loadUrl(detail.url);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.lijibmm:
                SharedUtil instances = SharedUtil.getInstances();
                boolean config = (boolean) instances.getValueByKey(DetailsActivity.this, "config", false);
                if (!config){
                    Toast.makeText(this, "请登录", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent intent=new Intent(DetailsActivity.this,ActivityTimeActivity.class);
                    intent.putExtra("activity_id",detail.id);
                    startActivity(intent);
                }

                break;

        }

    }
}
