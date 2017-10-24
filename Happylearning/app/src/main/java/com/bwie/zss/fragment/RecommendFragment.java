package com.bwie.zss.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bwie.zss.R;
import com.bwie.zss.activity.DetailsActivity;
import com.bwie.zss.javabean.DetailsData;
/**
 * 1.类的用途
 * 2.@author棒棒糖：赵姗杉
 * 3.@date2017/9/28  14：23
 */
public class RecommendFragment extends Fragment {
    private String url="http://lexue365.51dangao.cn/Page/recommend.html";
    private View view;
    private WebView webview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if (view==null) {
           view = inflater.inflate(R.layout.recommend_fragment, container, false);
       }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group!=null){
            group.removeView(view);
        }
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initId();
        initView();
        webview.loadUrl(url);
    }
    private void initView() {
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setJavaScriptEnabled(true);//支持js
        webview.setWebViewClient(new WebViewClient(){});
        webview.setWebChromeClient(new WebChromeClient(){});
        /**
         * window.lexue365
         */
        webview.addJavascriptInterface(new Object(){
            @JavascriptInterface
            public void jumpActivity(String url,String id)
            {
                Log.i("xxx","js交互获取的数据    url:"+url+"  id:"+id);
                //跳转到详情页面
                Intent intent=new Intent(getActivity(),DetailsActivity.class);
                //封装Bean对象
                DetailsData details=new DetailsData();
                details.id=id;
                details.url=url;
                intent.putExtra("details",details);
                startActivity(intent);
            }
        },"lexue365");
    }
    private void initId() {
        webview = view.findViewById(R.id.webview);
    }
}
