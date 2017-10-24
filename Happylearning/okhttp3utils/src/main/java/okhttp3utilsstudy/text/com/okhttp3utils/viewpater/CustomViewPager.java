package okhttp3utilsstudy.text.com.okhttp3utils.viewpater;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;


import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3utilsstudy.text.com.okhttp3utils.R;

/**
 * 1. 类的用途
 * 2. @author forever
 * 3. @date 2017/9/1 09:18
 */

public class CustomViewPager extends RelativeLayout {


    private List<String> imgList;
    private ViewPager vp;
    private LinearLayout ll_out;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //初始化控件
    private void init() {
        View view = inflate(getContext(), R.layout.custom_viewpager, null);
        //添加布局
        addView(view);
        vp = (ViewPager) view.findViewById(R.id.vp);
        ll_out = (LinearLayout) view.findViewById(R.id.ll_out);

    }

    //初始化小圆点
    private void initPoint() {
        for (int i = 0; i < imgList.size(); i++) {
            View view = new View(getContext());
            view.setBackgroundResource(R.drawable.point_bg);
            //动态生成小圆点
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(5, 5);
            params.rightMargin = 20;
            ll_out.addView(view, params);
        }
        //给第一个选中
        View view = ll_out.getChildAt(0);
        view.setEnabled(false);
    }

    //获取viewpager数据源
    private void initVp() {
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        vp.setAdapter(adapter);

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int count = ll_out.getChildCount();
                for (int i = 0; i < count; i++) {
                    View view = ll_out.getChildAt(i);
                    //设置小圆点属性
                    view.setEnabled(i == (position %= imgList.size()) ? false : true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void setData(List<String> imgList) {
        this.imgList = imgList;
        init();
        initVp();
        initPoint();
    }

    public void startSwith(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int currentItem = vp.getCurrentItem();
                currentItem++;
                vp.setCurrentItem(currentItem);
                handler.postDelayed(this,5000);
            }
        },5000);
    }
    public void stopSwith(){
        handler.removeCallbacksAndMessages(null);
    }

    private class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int i = position % imgList.size();
            ImageView imageView = new ImageView(getContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.with(getContext()).load(imgList.get(i)).into(imageView);
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //    super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

}
