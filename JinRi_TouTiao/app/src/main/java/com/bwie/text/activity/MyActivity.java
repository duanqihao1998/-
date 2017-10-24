package com.bwie.text.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bwei.imageloaderlibrary.utils.ImageLoaderUtils;
import com.bwie.text.R;
import com.bwie.text.bean.SlidingBean;
import com.bwie.text.bean.VisitUrl;
import com.bwie.text.fragment.Fragment_vp;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MyActivity extends AppCompatActivity {
    private String path1 = "http://ic.snssdk.com/2/article/v25/stream/?count=20&min_behot_time=1455521444&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455521401&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path2 = "http://ic.snssdk.com/2/article/v25/stream/?category=essay_joke&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455523440&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path3 = "http://ic.snssdk.com/2/article/v25/stream/?category=video&count=20&min_behot_time=1455521349&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path4 = "http://ic.snssdk.com/2/article/v25/stream/?category=news_society&count=20&min_behot_time=1455521720&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522107&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path5 = "http://ic.snssdk.com/2/article/v25/stream/?category=news_entertainment&count=20&min_behot_time=1455522338&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path6 = "http://ic.snssdk.com/2/article/v25/stream/?category=news_tech&count=20&min_behot_time=1455522427&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";
    private String path7 = "http://ic.snssdk.com/2/article/v25/stream/?category=news_car&count=20&bd_city=%E5%8C%97%E4%BA%AC%E5%B8%82&bd_latitude=40.049317&bd_longitude=116.296499&bd_loc_time=1455522784&loc_mode=5&lac=4527&cid=28883&iid=3642583580&device_id=11131669133&ac=wifi&channel=baidu&aid=13&app_name=news_article&version_code=460&device_platform=android&device_type=SCH-I919U&os_api=19&os_version=4.4.2&uuid=285592931621751&openudid=AC9E172CE2490000";

    private ImageView sliding_jump;
    private TabLayout tab;
    private ViewPager mvp;
    private List<VisitUrl> list;
    private TabViewAdapter tadapter = null;
    private SlidingMenu menu;
    private List<SlidingBean> slist = new ArrayList<>();//设置侧滑窗的布局
    private ListView listView;
    private ImageView myheader;
    private ImageView qq_login;
    private TextView name;
    private LinearLayout linearLayout;
    private static final String TAG = "MyActivity";
    private static final String mAppid = "1105602574";
    private Tencent mTencent;
    private static boolean isServerSideLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //获取资源id
        sliding_jump = (ImageView) findViewById(R.id.sliding_jump);
        tab = (TabLayout) findViewById(R.id.tab);
        mvp = (ViewPager) findViewById(R.id.mvp);
        //初始化数据
        isitData();
        for (int i = 0; i < list.size(); i++) {
            tab.addTab(tab.newTab().setText(list.get(i).getTitle()));
        }
        if (tadapter == null) {
            tadapter = new TabViewAdapter(getSupportFragmentManager());
            mvp.setAdapter(tadapter);
            tab.setTabsFromPagerAdapter(tadapter);
        } else {
            tadapter.notifyDataSetChanged();
        }
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);
        tab.setupWithViewPager(mvp);
        //设置侧滑菜单
        setSlidingMenu();
        //创建实例(QQ的第三方登录)
        //实例化
        mTencent = Tencent.createInstance(mAppid, this);
    }

    //初始化数据
    private void isitData() {
        list = new ArrayList<>();
        list.add(new VisitUrl("推荐", path1));
        list.add(new VisitUrl("段子", path2));
        list.add(new VisitUrl("视频", path3));
        list.add(new VisitUrl("社会", path4));
        list.add(new VisitUrl("娱乐", path5));
        list.add(new VisitUrl("科技", path6));
        list.add(new VisitUrl("汽车", path7));
    }
    //接口回调
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100) {
        }
        if (requestCode == Constants.REQUEST_LOGIN ||
                requestCode == Constants.REQUEST_APPBAR) {
            Tencent.onActivityResultData(requestCode,resultCode,data,loginListener);
        }
    }

    //tablayout的适配器
    class TabViewAdapter extends FragmentPagerAdapter {
        public TabViewAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list != null ? list.get(position).getTitle() : null;
        }

        @Override
        public Fragment getItem(int position) {
            return Fragment_vp.newInstance(list.get(position).getPath());
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    //设置侧滑菜单
    private void setSlidingMenu() {
        menu = new SlidingMenu(this);
        //设置从哪滑出
        menu.setMode(SlidingMenu.LEFT);
        //设置侧滑的宽度
        menu.setBehindOffset(getWindowManager().getDefaultDisplay().getWidth() * 1 / 5);
        //设置淡出
        menu.setFadeEnabled(true);
        //淡出的比例
        menu.setFadeDegree(0.4f);
        //设置滑出拖拽效果
        menu.setBehindScrollScale(0);
        //设置依附在activty
        menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置slideingmenu布局
        menu.setMenu(R.layout.slidingmenu);
        //给布局设置文件信息
        setSlidingMessage();
        //获取布局中的控件
        listView = (ListView) menu.findViewById(R.id.list_slide);
        myheader = (ImageView) menu.findViewById(R.id.myheader);
        name = (TextView) menu.findViewById(R.id.name);
        qq_login = (ImageView) menu.findViewById(R.id.qq_login);
        linearLayout = (LinearLayout) menu.findViewById(R.id.shezhi);
        //设置监听给设置设置点击事件
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //给QQ第三方登陆设置点击事件
        qq_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //all表示获取所有权限
                login_qq();
            }
        });

        //给布局中的listView设置适配器
        listView.setAdapter(new SlistViewAdapter());
    }

    //图片设置点击事件展示出侧滑窗
    public void setSlidingJump(View view) {
        menu.toggle();
    }

    //设置侧滑菜单的布局Listview
    private void setSlidingMessage() {
        slist.clear();//清除
        SlidingBean sb1 = new SlidingBean(R.drawable.dynamicicon_leftdrawer, "好友动态");
        SlidingBean sb2 = new SlidingBean(R.drawable.topicicon_leftdrawer, "我的话题");
        SlidingBean sb3 = new SlidingBean(R.drawable.favoriteicon_leftdrawer, "收藏");
        SlidingBean sb4 = new SlidingBean(R.drawable.activityicon_leftdrawer, "活动");
        SlidingBean sb5 = new SlidingBean(R.drawable.sellicon_leftdrawer, "商城");
        SlidingBean sb6 = new SlidingBean(R.drawable.feedbackicon_leftdrawer, "反馈");
        SlidingBean sb7 = new SlidingBean(R.drawable.pgcicon_leftdrawer, "我要爆料");
        slist.add(sb1);
        slist.add(sb2);
        slist.add(sb3);
        slist.add(sb4);
        slist.add(sb5);
        slist.add(sb6);
        slist.add(sb7);
    }

    //给侧滑窗设置Listview适配器
    class SlistViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return slist.size();
        }

        @Override
        public Object getItem(int position) {
            return slist.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Hold hold;
            if (convertView == null) {
                hold = new Hold();
                convertView = View.inflate(MyActivity.this, R.layout.sliding_listview, null);
                hold.textView = (TextView) convertView.findViewById(R.id.sliding_text);
                hold.imageView = (ImageView) convertView.findViewById(R.id.sliding_Image);
                convertView.setTag(hold);
            } else {
                hold = (Hold) convertView.getTag();
            }
            hold.textView.setText(slist.get(position).getSlidingText());
            hold.imageView.setImageResource(slist.get(position).getSlidingIcom());
            return convertView;
        }

        class Hold {
            TextView textView;
            ImageView imageView;
        }
    }
    //qq登录的相关
    //初始化qq主操作对象    腾讯提供测试id 222222
    //登录qq
    public void login_qq() {
        if (!mTencent.isSessionValid()) {
            mTencent.login(this, "all", loginListener);
            isServerSideLogin = false;
            Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
        } else {
            if (isServerSideLogin) { // Server-Side 模式的登陆, 先退出，再进行SSO登陆
                mTencent.logout(this);
                mTencent.login(this, "all", loginListener);
                isServerSideLogin = false;
                Log.d("SDKQQAgentPref", "FirstLaunch_SDK:" + SystemClock.elapsedRealtime());
                return;
            }
            mTencent.logout(this);
            updateUserInfo();
        }
    }

    IUiListener loginListener = new BaseUiListener() {
        @Override
        protected void doComplete(JSONObject values) {
            Log.d("SDKQQAgentPref", "AuthorSwitch_SDK:" + SystemClock.elapsedRealtime());
            //初始化OpenidAndToken
            initOpenidAndToken(values);
            updateUserInfo();
        }
    };
    public  void initOpenidAndToken(JSONObject jsonObject) {
        try {
            String token = jsonObject.getString(Constants.PARAM_ACCESS_TOKEN);
            String expires = jsonObject.getString(Constants.PARAM_EXPIRES_IN);
            String openId = jsonObject.getString(Constants.PARAM_OPEN_ID);
            if (!TextUtils.isEmpty(token) && !TextUtils.isEmpty(expires)
                    && !TextUtils.isEmpty(openId)) {
                mTencent.setAccessToken(token, expires);
                mTencent.setOpenId(openId);
            }
        } catch(Exception e) {
        }
    }
    private class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            if (null == response) {
                return;
            }
            JSONObject jsonResponse = (JSONObject) response;
            if (null != jsonResponse && jsonResponse.length() == 0) {
                return;
            }
            doComplete((JSONObject)response);
        }

        protected void doComplete(JSONObject values) {
        }

        @Override
        public void onError(UiError e) {
        }

        @Override
        public void onCancel() {

        }
    }
    //更新用户信息
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        name.setText(response.getString("nickname"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                DisplayImageOptions circleOptions = ImageLoaderUtils.getOptions();
                ImageLoader.getInstance().displayImage(msg.obj.toString(),myheader,circleOptions);
                ImageLoader.getInstance().displayImage(msg.obj.toString(),sliding_jump,circleOptions);
            }
        }
    };

    //获取用户信息
    private void updateUserInfo() {
        if (mTencent != null && mTencent.isSessionValid()) {
            IUiListener listener = new IUiListener() {

                @Override
                public void onError(UiError e) {

                }

                @Override
                public void onComplete(final Object response) {
                    Log.i("sss",response.toString());
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    new Thread(){

                        @Override
                        public void run() {
                            JSONObject json = (JSONObject)response;
                            if(json.has("figureurl")){

                                Message msg = new Message();
                                try {
                                    msg.obj = json.getString("figureurl_qq_2");
                                    msg.what = 1;
                                    mHandler.sendMessage(msg);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }.start();
                }
                @Override
                public void onCancel() {
                }
            };
            UserInfo mInfo = new UserInfo(this, mTencent.getQQToken());
            mInfo.getUserInfo(listener);
        }
    }
    //回调方法 才能得到用户信息
}
