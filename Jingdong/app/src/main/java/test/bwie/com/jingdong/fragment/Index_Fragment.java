package test.bwie.com.jingdong.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.squareup.picasso.Picasso;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import test.bwie.com.jingdong.Bean.Miaosha;
import test.bwie.com.jingdong.Bean.TuiJianBean;
import test.bwie.com.jingdong.P.MyPresenter;
import test.bwie.com.jingdong.R;
import test.bwie.com.jingdong.V.MyIndexView;
import test.bwie.com.jingdong.activity.SouSuoActivity;
import test.bwie.com.jingdong.adapter.MiaoshaAdapter;
import test.bwie.com.jingdong.adapter.TuiJIanAdapter;

/**
 * Created by Love_you on 2017/9/28 0028.
 */
public class Index_Fragment extends Fragment implements MyIndexView,PullLoadMoreRecyclerView.PullLoadMoreListener{

    private MyPresenter presenter;
    private List<String> BannerList=new ArrayList<>();
    private View view;
    private RollPagerView mRollViewPager;
    private RecyclerView recycle;
    private List<Miaosha> JingDongMiaoSha=new ArrayList<>();
    private List<TuiJianBean> tuiJianBeanList=new ArrayList<>();
    private PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    private EditText sousuo_shop;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        TextView textView=new TextView(getActivity());
//        textView.setText("首页");
        view = inflater.inflate(R.layout.shouye, null);
        //获取布局控;
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new MyPresenter(this);
        presenter.getIndex();
        isitView();
        sousuo_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
            }
        });


        /**
         * 通过这三种方法进行三种不同的样式的显示
         */
        //设置LinearLayoutManager布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //设置GridLayoutManager布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);

        //设置StaggeredGridLayoutManager布局管理器
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);

        //把布局管理器给RecyclerView
        recycle.setLayoutManager(staggeredGridLayoutManager);


        //设置网格列数
//        pullLoadMoreRecyclerView.setGridLayout(1);
        //设置上拉加载展示的字
        pullLoadMoreRecyclerView.setFooterViewText("loading");
        //设置瀑布流的列数
        pullLoadMoreRecyclerView.setStaggeredGridLayout(2);
        //添加点击事件
        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
    }

    private void isitView() {
        mRollViewPager = view.findViewById(R.id.ropagerView);
        recycle = view.findViewById(R.id.recycle);
        pullLoadMoreRecyclerView = view.findViewById(R.id.pullLoadMoreRecyclerView);
        sousuo_shop = view.findViewById(R.id.sousuo_shop);
    }

    @Override
    public void getIndexJSON(String indexujson) {
        Log.i("index",indexujson);
        try {
            JSONObject jsonObject=new JSONObject(indexujson);
            JSONArray data = jsonObject.optJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                String icon = data.getJSONObject(i).optString("icon");
                BannerList.add(icon);
            }
            //设置播放时间间隔
            mRollViewPager.setPlayDelay(1000);
            //设置透明度
            mRollViewPager.setAnimationDurtion(500);
            //设置适配器
            mRollViewPager.setAdapter(new TestNormalAdapter());

            //设置指示器（顺序依次）
            //自定义指示器图片
            //设置圆点指示器颜色
            //设置文字指示器
            //隐藏指示器
            //mRollViewPager.setHintView(new IconHintView(this, R.drawable.point_focus, R.drawable.point_normal));
            //mRollViewPager.setHintView(new ColorPointHintView(this, Color.YELLOW, Color.WHITE));
            //mRollViewPager.setHintView(new TextHintView(this));
            //mRollViewPager.setHintView(null);
            JSONObject miaosha = jsonObject.getJSONObject("miaosha");
            JSONArray list = miaosha.getJSONArray("list");
            for (int i = 0; i < list.length(); i++) {
                String images = list.getJSONObject(i).getString("images");
                String title = list.getJSONObject(i).getString("title");
                String[] split = images.split("\\|");
                if(split.length>1){
                    Miaosha sha=new Miaosha(split[0],title);
                    JingDongMiaoSha.add(sha);
                }else {
                    Miaosha miaosha1=new Miaosha(images,title);
                    JingDongMiaoSha.add(miaosha1);
                }
            }
            MiaoshaAdapter miaoshaAdapter=new MiaoshaAdapter(getContext(),JingDongMiaoSha);
            recycle.setAdapter(miaoshaAdapter);

            JSONObject tuijian = jsonObject.getJSONObject("tuijian");
            JSONArray list1 = tuijian.getJSONArray("list");
            TuiJIanAdapter tuiJIanAdapter=new TuiJIanAdapter(getContext(),list1);
            pullLoadMoreRecyclerView.setAdapter(tuiJIanAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void getData1() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //停止刷新
                pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            }
        }, 1000);
    }

    //刷新
    @Override
    public void onRefresh() {
        getData1();
        Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT).show();
    }

    //加载
    @Override
    public void onLoadMore() {
        getData1();
        Toast.makeText(getActivity(), "加载成功", Toast.LENGTH_SHORT).show();
    }


    /**
     * 轮播图的适配器
     */
    private class TestNormalAdapter extends StaticPagerAdapter {
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(getActivity());
            Picasso.with(getActivity()).load(BannerList.get(position)).placeholder(R.mipmap.ic_launcher).into(view);
           // Log.i("Ba",BannerList.get(position));
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            //view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return BannerList.size();
        }
    }
}
