package com.bwie.text.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bwei.imageloaderlibrary.utils.ImageLoaderUtils;
import com.bwie.text.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private String imagePath03="http://sjbz.fd.zol-img.com.cn/t_s800x1280c/g5/M00/00/04/ChMkJ1fJWI2IAqmNAAv6o3Kp-KgAAU-KANHRqQAC_q7387.jpg";
    private String imagePath02="http://sjbz.fd.zol-img.com.cn/t_s800x1280c/g5/M00/00/04/ChMkJ1fJWI6IIEjPAAsAXdewuT8AAU-KANfBsUACwB1885.jpg";
    private String imagePath01="http://sjbz.fd.zol-img.com.cn/t_s800x1280c/g5/M00/00/04/ChMkJ1fJWI2IAVOHAAuL8vZ_8FkAAU-KAM7upoAC4wK339.jpg";
    private ViewPager viewPager;
    private List<View> list;
    private Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(i>=0){
                i--;
                handler.postDelayed(runnable,1000);
            }else {
                handler.removeCallbacks(runnable);
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };

    private int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //创建集合
        list=new ArrayList<>();
        SharedPreferences guide = getSharedPreferences("guide", MODE_PRIVATE);
        boolean isfirst=guide.getBoolean("isfirst",true);
        if(isfirst){
            //初始化资源
            getListData();
            viewPager.setAdapter(new ViewpagerAdapter());
            SharedPreferences.Editor editor=guide.edit().putBoolean("isfirst",false);
            editor.commit();
        }else {
            //第一次以后的引导页
            getGuideData();
        }
    }

    public void getGuideData() {
        //加载视图
        View v3=View.inflate(MainActivity.this, R.layout.guide03,null);
        //获取布局控件
        Button btn= (Button) v3.findViewById(R.id.btn);
        ImageView image03= (ImageView) v3.findViewById(R.id.image03);
        image03.setScaleType(ImageView.ScaleType.FIT_XY);
        btn.setVisibility(View.GONE);
        DisplayImageOptions options=ImageLoaderUtils.getOptions();
        ImageLoader.getInstance().displayImage(imagePath03,image03,options);
        list.clear();//清空集合
        list.add(v3);
        viewPager.setAdapter(new ViewpagerAdapter());
        handler.postDelayed(runnable,1000);
    }

    //初始化资源
    public void getListData() {
        //加载布局
        View v1=View.inflate(MainActivity.this,R.layout.guide01,null);
        View v2=View.inflate(MainActivity.this,R.layout.guide02,null);
        View v3=View.inflate(MainActivity.this,R.layout.guide03,null);
        //获取布局控件
        ImageView image01= (ImageView) v1.findViewById(R.id.image01);
        ImageView image02= (ImageView) v2.findViewById(R.id.image02);
        ImageView image03= (ImageView) v3.findViewById(R.id.image03);
        image01.setScaleType(ImageView.ScaleType.FIT_XY);
        image02.setScaleType(ImageView.ScaleType.FIT_XY);
        image03.setScaleType(ImageView.ScaleType.FIT_XY);

        Button btn= (Button) v3.findViewById(R.id.btn);
        //按钮是指点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);
                finish();
            }
        });
        DisplayImageOptions options= ImageLoaderUtils.getOptions();
        ImageLoader.getInstance().displayImage(imagePath01,image01,options);
        ImageLoader.getInstance().displayImage(imagePath02,image02,options);
        ImageLoader.getInstance().displayImage(imagePath03,image03,options);
        list.add(v1);
        list.add(v2);
        list.add(v3);
    }
    //viewpager适配器
    class ViewpagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            //加载视图
            container.addView(list.get(position));
            return list.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }
    }
}
