package com.bwie.shang.activity;

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
import android.widget.LinearLayout;

import com.bwie.shang.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import imageloaderapp.bwie.com.mylibrary.ImageLoaderUtils;

public class MainActivity extends AppCompatActivity {

    private String path1="http://attimg.dospy.com/img/day_131027/20131027_3d566cc9000c9c623b83x4Dk3TUKDHCS.png";
    private String path2="http://imga.mumayi.com/android/img_mumayi/2013/05/09/31/319320/nologo/nologo_pic_319320_0613ab.png";
    private String path3="http://img1.imgtn.bdimg.com/it/u=4195606643,2846806789&fm=27&gp=0.jpg";
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private Button button;
    private List<View> viewList=new ArrayList<>();
    private List<ImageView> imageViewList=new ArrayList<>();
    private int i=1;
    private int lastposition=0;
    private Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(i>1){
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        isitView();
        SharedPreferences guide=getSharedPreferences("guide",MODE_PRIVATE);
        boolean isfirst = guide.getBoolean("isfirst", true);
        if(isfirst){
            //初始化资源
            getListdata();
            //添加小点点
            addpoint();
            viewPager.setAdapter(new MyPagerAdapter());
            SharedPreferences.Editor editor = guide.edit().putBoolean("isfirst", false);
            editor.commit();
            //给Viewpager设置监听
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    position=position%viewList.size();
                    imageViewList.get(lastposition).setImageResource(R.drawable.dot_normal);
                    imageViewList.get(position).setImageResource(R.drawable.dot_focus);
                    lastposition=position;
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
            getGuideData();
        }
    }

    //添加小点点的方法
    private void addpoint(){
        for (int i = 0; i < viewList.size(); i++) {
            ImageView imageView=new ImageView(this);
            imageView.setImageResource(R.drawable.dot_normal);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.leftMargin=30;
            imageViewList.add(imageView);
            linearLayout.addView(imageView,layoutParams);
        }
    }

    //二次进入
    private void getGuideData() {
        View v3 = View.inflate(MainActivity.this, R.layout.yin03, null);
        //获取控件
        ImageView image03 = (ImageView) v3.findViewById(R.id.image03);
        Button button = (Button) v3.findViewById(R.id.but_jinru);
        image03.setScaleType(ImageView.ScaleType.FIT_XY);
        //按钮隐藏
        button.setVisibility(View.GONE);
        DisplayImageOptions options=ImageLoaderUtils.getOptions();
        ImageLoader.getInstance().displayImage(path1,image03,options);
        viewList.clear();//清空集合
        viewList.add(v3);
        viewPager.setAdapter(new MyPagerAdapter());
        handler.postDelayed(runnable,1000);
    }

    private void getListdata() {
        //加载布局
        View v1 = View.inflate(MainActivity.this, R.layout.yin01, null);
        View v2 = View.inflate(MainActivity.this, R.layout.yin02, null);
        View v3 = View.inflate(MainActivity.this, R.layout.yin03, null);
        //获取布局控件
        ImageView image01 =   (ImageView) v1.findViewById(R.id.image01);
        ImageView image02 =   (ImageView) v2.findViewById(R.id.image02);
        ImageView image03 =   (ImageView) v3.findViewById(R.id.image03);
        image01.setScaleType(ImageView.ScaleType.FIT_XY);
        image02.setScaleType(ImageView.ScaleType.FIT_XY);
        image03.setScaleType(ImageView.ScaleType.FIT_XY);

        Button button =  (Button) v3.findViewById(R.id.but_jinru);
        //按钮设置点击事件
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MyActivity.class);
                startActivity(intent);

            }
        });
        DisplayImageOptions options = ImageLoaderUtils.getOptions();
        ImageLoader.getInstance().displayImage(path1,image01,options);
        ImageLoader.getInstance().displayImage(path2,image02,options);
        ImageLoader.getInstance().displayImage(path3,image03,options);
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);
    }

    //获取布局控件
    private void isitView() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        linearLayout = (LinearLayout) findViewById(R.id.liner);
        button = (Button) findViewById(R.id.but_jinru);
    }


    //适配器
    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return viewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(viewList.get(position));
            return viewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
